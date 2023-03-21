package hello.golong.domain.img.application;

import hello.golong.domain.img.dao.ImgRepository;
import hello.golong.domain.img.domain.Img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImgService {

    private final ImgS3Service imgS3Service;
    private final ImgRepository imgRepository;

    public List<String> findImgByPostId(Long post_id, Long post_type) {

        List<String> urls = new ArrayList<>();
        //TODO : Repository 레벨로 코드 분류
        List<Img> allImg = imgRepository.findAll();
        for(Img img : allImg) {
            if(img.getType().equals(post_type) && img.getPost_id().equals(post_id))
                urls.add(img.getImg_url());
        }
        return urls;
    }

    //TODO : Entity & DTO 구분하기
    public void saveImg(List<String> file_names, Long post_id, Long post_type) throws IOException {

        for(String file_name : file_names) {
            Img img = Img.builder()
                    .img_url(imgS3Service.download(file_name))
                    .type(post_type)
                    .post_id(post_id)
                    .file_name(file_name)
                    .build();
            imgRepository.save(img);
        }

    }

    public void deleteImg(Long post_id, Long post_type) {
        List<Img> allImg = imgRepository.findAll();
        for(Img img : allImg) {
            if(img.getPost_id().equals(post_id) && img.getType().equals(post_type))
            {
                imgRepository.deleteById(img.getImg_id());
                //TODO : S3에서 이미지 삭제하는 코드 구현하기
            }
        }
    }




}
