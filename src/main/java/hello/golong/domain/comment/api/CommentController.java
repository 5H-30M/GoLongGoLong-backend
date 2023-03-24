package hello.golong.domain.comment.api;

import hello.golong.domain.comment.application.CommentService;
import hello.golong.domain.comment.dto.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board/comment") ///board/{post-id}/comment/{comment-id}
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        commentDto = commentService.createComment(commentDto);
        return ResponseEntity.ok().body(commentDto);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);

        //TODO : 예외처리는 여기서 해주는 수 밖에 없는지??
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
