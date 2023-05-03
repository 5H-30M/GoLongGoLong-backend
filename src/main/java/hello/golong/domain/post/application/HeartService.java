package hello.golong.domain.post.application;

import hello.golong.domain.post.dao.HeartRepository;
import hello.golong.domain.post.domain.Heart;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeartService {

    private final HeartRepository heartRepository;


    public HeartService(HeartRepository heartRepository) {
        this.heartRepository = heartRepository;
    }

    List<Long> findHeartedPost(Long member_id){
        List<Long> postIds = new ArrayList<>();
        Optional<List<Heart>> optionalHearts = heartRepository.findByMemberId(member_id);
        optionalHearts.ifPresent(hearts -> {
            for(Heart heart : hearts) {
                postIds.add(heart.getPostId());
            }
        });
        return postIds;
    }
}
