package hello.golong.domain.post.api;

import hello.golong.domain.post.application.PostService;
import hello.golong.domain.post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        postDto = postService.createPost(postDto);
        return ResponseEntity.ok().body(postDto);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = postService.findAllPosts();
        return ResponseEntity.ok().body(postDtos);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("post_id") Long post_id) {
        PostDto postDto = postService.findPost(post_id);
        return ResponseEntity.ok().body(postDto);
    }

}
