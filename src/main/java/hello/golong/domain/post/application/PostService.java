package hello.golong.domain.post.application;

import hello.golong.domain.post.dao.PostRepository;
import hello.golong.domain.post.domain.Post;
import hello.golong.domain.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(PostDto postDto) {

        postDto.setCreated_at(LocalDateTime.now());
        postDto.setStatus(0);

        Post post = Post.builder()
                .title(postDto.getTitle())
                .status(postDto.getStatus())
                .content(postDto.getContent())
                .uploader_id(postDto.getUploader_id())
                .created_at(postDto.getCreated_at())
                .period(postDto.getPeriod())
                .region(postDto.getRegion()).build();

        postRepository.save(post);
        postDto.setPost_id(post.getPost_id());
        return postDto;

    }

    public List<PostDto> findAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        //TODO : Builder로 수정하기
        for(Post post : posts) {
            PostDto postDto = new PostDto(
                    post.getPost_id(),
                    post.getTitle(),
                    post.getStatus(),
                    post.getContent(),
                    post.getUploader_id(),
                    post.getCreated_at(),
                    post.getPeriod(),
                    post.getRegion());
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

            postDto.setPost_id(post.getPost_id());
            postDto.setRegion(post.getRegion());
            postDto.setContent(post.getContent());
            postDto.setPeriod(post.getPeriod());
            postDto.setTitle(post.getTitle());
            postDto.setUploader_id(post.getUploader_id());
            postDto.setCreated_at(post.getCreated_at());
            postDto.setStatus(post.getStatus());

        });

        return postDto;

    }

    public void deletePost(Long post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isPresent()) {
            postRepository.deleteById(post_id);
        }
    }
}
