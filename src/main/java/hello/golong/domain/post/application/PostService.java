package hello.golong.domain.post.application;

import hello.golong.domain.comment.application.CommentService;
import hello.golong.domain.img.application.ImgService;
import hello.golong.domain.post.dao.PostRepository;
import hello.golong.domain.post.domain.Post;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ImgService imgService;
    private final CommentService commentService;

    @Autowired
    public PostService(PostRepository postRepository, ImgService imgService, CommentService commentService) {
        this.postRepository = postRepository;
        this.imgService = imgService;
        this.commentService = commentService;
    }

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
                .region(postDto.getRegion()).build();

        postRepository.save(post);
        postDto.setPost_id(post.getId());

        imgService.saveImg(postDto.getImages(), postDto.getPost_id(), 0L);

        return postDto;

    }

    public List<PostDto> findAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for(Post post : posts) {
            PostDto postDto = PostDto.builder()
                    .post_id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .status(post.getStatus())
                    .uploader_id(post.getUploaderId())
                    .created_at(post.getCreatedAt())
                    .period(post.getPeriod())
                    .comments(commentService.findByPostId(post.getId()))
                    .target_amount(post.getTargetAmount())
                    .region(post.getRegion())
                    .images(imgService.findImgByPostId(post.getId(), 0L))
                    .build();


            postDtos.add(postDto);
        }

        return postDtos;

    }

    //TODO: Exception 처리
    //TODO : orElseThrow를 사용해야할 이유가 있는지 생각해보기
    //TODO : findPost 리팩토링
    public PostDto findPost(Long post_id) {

        PostDto postDto = new PostDto();
        Optional<Post> postOptional = postRepository.findById(post_id);
        postOptional.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postOptional.ifPresent(post -> {

            postDto.setPost_id(post.getId());
            postDto.setRegion(post.getRegion());
            postDto.setContent(post.getContent());
            postDto.setPeriod(post.getPeriod());
            postDto.setTitle(post.getTitle());
            postDto.setUploader_id(post.getUploaderId());
            postDto.setCreated_at(post.getCreatedAt());
            postDto.setTarget_amount(post.getTargetAmount());
            postDto.setStatus(post.getStatus());
            postDto.setImages(imgService.findImgByPostId(post_id, 0L));
            postDto.setComments(commentService.findByPostId(post_id));

        });

        return postDto;

    }

    public void deletePost(Long post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isPresent()) {
            postRepository.deleteById(post_id);
            imgService.deleteImg(post_id, 0L);
            commentService.deleteByPostId(post_id);
        }
    }
}
