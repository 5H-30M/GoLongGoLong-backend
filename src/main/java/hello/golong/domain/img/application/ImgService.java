package hello.golong.domain.img.application;

import hello.golong.domain.img.dao.ImgRepository;
import hello.golong.domain.img.domain.Img;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImgService {

    private final ImgS3Service imgS3Service;
    private final ImgRepository imgRepository;

    public List<String> findImgByPostId(Long post_id, Long post_type) {

        Optional<List<Img>> optionalImgs = imgRepository.findByPostIdAndType(post_id, post_type);
        List<String> urls = new ArrayList<>();

        optionalImgs.ifPresent( imgs -> {

            for(Img img : imgs) {
                if(img.getIsThumbnail() == 1) {
                    urls.add(0, img.getImgUrl());
                }
                else
                    urls.add(img.getImgUrl());
            }

        });

        return urls;
    }

    //TODO : Entity & DTO 구분하기
    public void saveImg(List<String> file_names, Long post_id, Long post_type) throws IOException {

        for(String file_name : file_names) {

            int is_thumbnail = 0;//대표 이미지 x

            //대표 이미지인 경우 + 영수증인 경우 항상 is_thumbnail = 1
            if(file_names.indexOf(file_name) == 0)
                is_thumbnail = 1;
            Img img = Img.builder()
                    .imgUrl(imgS3Service.download(file_name))
                    .type(post_type)
                    .postId(post_id)
                    .fileName(file_name)
                    .isThumbnail(is_thumbnail)
                    .build();
            imgRepository.save(img);
        }

    }

    public void deleteImg(Long post_id, Long post_type) {

        Optional<List<Img>> optionalImgs = imgRepository.findByPostIdAndType(post_id, post_type);
        optionalImgs.ifPresent( imgs -> {

            for(Img img : imgs) {
                imgS3Service.deleteFromS3(img.getFileName());
                imgRepository.deleteById(img.getId());
            }

        });

    }

    @Transactional
    public void updateReceipt(String file_name, Long post_id, Long post_type) {
        Optional<List<Img>> optionalImgs = imgRepository.findByPostIdAndType(post_id, post_type);
        optionalImgs.ifPresent( imgs -> {
            Img img = imgs.get(0);
            //s3에서 기존 이미지 삭제
            imgS3Service.deleteFromS3(img.getFileName());
            try {
                img.updateImg(file_name, imgS3Service.download(file_name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public void updateImg(List<String> file_names, Long post_id, Long post_type) throws IOException {
        this.deleteImg(post_id, post_type);
        this.saveImg(file_names, post_id, post_type);
    }




}
