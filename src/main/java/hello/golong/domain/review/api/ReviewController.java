package hello.golong.domain.review.api;

import hello.golong.domain.review.application.ReviewService;
import hello.golong.domain.review.domain.Review;
import hello.golong.domain.review.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) throws IOException {
        reviewDto = reviewService.createReview(reviewDto);
        return ResponseEntity.ok().body(reviewDto);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviewDtos = reviewService.findAllReviews();
        return ResponseEntity.ok().body(reviewDtos);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long review_id) {
        ReviewDto reviewDto = reviewService.findReview(review_id);
        return ResponseEntity.ok().body(reviewDto);
    }

    @GetMapping("/board/{post_id}")
    public ResponseEntity<ReviewDto> getReviewByPostId(@PathVariable Long post_id) {
        ReviewDto reviewDto = reviewService.findReviewByPostId(post_id);
        return ResponseEntity.ok().body(reviewDto);
    }

    //TODO: 모금 후기 삭제 방식 결정해서 메소드 추가하기

    @PatchMapping("/{review_id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("review_id") Long review_id, @RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(review_id, reviewDto);
        //TODO: findReview 호출 없이 동작하도록 수정하기
        return ResponseEntity.ok().body(reviewService.findReview(review_id));
    }


}
