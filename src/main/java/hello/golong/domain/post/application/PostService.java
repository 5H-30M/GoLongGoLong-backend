package hello.golong.domain.post.application;

import hello.golong.domain.img.application.ImgService;
import hello.golong.domain.post.dao.PostRepository;
import hello.golong.domain.post.domain.Post;
import hello.golong.domain.post.dto.PostDto;
import hello.golong.domain.review.application.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PlanService planService;
    private final ImgService imgService;

    private final ReviewService reviewService;

    @Autowired
    public PostService(PostRepository postRepository, PlanService planService, ImgService imgService, ReviewService reviewService) {
        this.postRepository = postRepository;
        this.planService = planService;
        this.imgService = imgService;
        this.reviewService = reviewService;
    }

    //TODO : 암호화폐 관련 컬럼 추가 수정하기
    public PostDto createPost(PostDto postDto) throws IOException {

        postDto.setCreated_at(LocalDateTime.now());
        postDto.setStatus(0);

        Post post = Post.builder()
                .title(postDto.getTitle())
                .status(postDto.getStatus())
                .content(postDto.getContent())
                .uploaderId(postDto.getUploader_id())
                .createdAt(postDto.getCreated_at())
                .period(postDto.getPeriod())
                .targetAmount(postDto.getTarget_amount())
                .region(postDto.getRegion())
                .amount(0L)
                .privateKey(postDto.getPrivateKey())
                .walletUrl(postDto.getWalletUrl())
                .transactionId(postDto.getTransactionId())
                .raisedPeople(0L).build();

        postRepository.save(post);
        postDto.setPost_id(post.getId());

        planService.savePlans(postDto.getPost_id(), postDto.getPlans());
        imgService.saveImg(postDto.getImages(), postDto.getPost_id(), 0L);

        return postDto;

    }

    public List<PostDto> findAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for(Post post : posts) {
            PostDto postDto = this.getPostDto(post);
            postDtos.add(postDto);
        }

        return postDtos;

    }

    //TODO: Exception 처리
    //TODO : orElseThrow를 사용해야할 이유가 있는지 생각해보기
    //TODO : findPost 리팩토링
    public PostDto findPost(Long post_id) {

        //TODO: Exception 핸들링하기
        Post post = postRepository.findById(post_id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return this.getPostDto(post);

    }
    public List<PostDto> findPostByUploaderId(Long uploaderId) {

        Optional<List<Post>> postOptional = postRepository.findByUploaderId(uploaderId);
        List<PostDto> postDtos = new ArrayList<>();
        postOptional.ifPresent(posts -> {
            for(Post post : posts) {
                postDtos.add(this.getPostDto(post));
            }
        });
        return postDtos;
    }

    //TODO : 암호화폐 관련 컬럼 추가 수정하기
    public PostDto getPostDto(Post post) {

        return PostDto.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .uploader_id(post.getUploaderId())
                .created_at(post.getCreatedAt())
                .period(post.getPeriod())
                .target_amount(post.getTargetAmount())
                .region(post.getRegion())
                .raised_people(post.getRaisedPeople())
                .amount(post.getAmount())
                .images(imgService.findImgByPostId(post.getId(), 0L))
                .plans(planService.findPlans(post.getId()))
                .privateKey(post.getPrivateKey())
                .walletUrl(post.getWalletUrl())
                .transactionId(post.getTransactionId())
                .build();

    }

    public void deletePost(Long post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isPresent()) {
            postRepository.deleteById(post_id);
            imgService.deleteImg(post_id, 0L);
            planService.deletePlans(post_id);
            if(postOptional.get().getStatus() >= 4) {
                reviewService.deleteReview(reviewService.findReviewByPostId(post_id).getId());
            }

        }
    }

    public void updateStatus(Long post_id, int status) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        postOptional.ifPresent(post -> {
            post.updateStatus(status);
        });
    }

    public void updatePost(Long post_id, PostDto postDto) {

        Optional<Post> postOptional = postRepository.findById(post_id);
        postOptional.ifPresent(post -> {
            if(postDto.getImages() != null)
            {
                try {
                    imgService.updateImg(postDto.getImages(), post_id, 0L);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.info("updateImg = {}", postDto.getImages().toString());
            }

            if(postDto.getTitle() != null) post.updateTitle(postDto.getTitle());
            if(postDto.getContent() != null) post.updateContent(postDto.getContent());
        });

    }

    public void updateDonationInformation(Long post_id, Long new_amount) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        postOptional.ifPresent(post -> {
            post.updateDonationInformation(new_amount);
        });
    }
}
