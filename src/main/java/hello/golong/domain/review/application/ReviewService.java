package hello.golong.domain.review.application;

import hello.golong.domain.comment.application.CommentService;
import hello.golong.domain.img.application.ImgService;
import hello.golong.domain.post.application.PostService;
import hello.golong.domain.review.dao.ReviewRepository;
import hello.golong.domain.review.domain.Review;
import hello.golong.domain.review.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImgService imgService;
    private final CommentService commentService;

    private final PostService postService;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ImgService imgService, CommentService commentService, PostService postService) {
        this.reviewRepository = reviewRepository;
        this.imgService = imgService;
        this.commentService = commentService;
        this.postService = postService;
    }

    public ReviewDto createReview(ReviewDto reviewDto) throws IOException {

        reviewDto.setCreatedAt(LocalDateTime.now());
        postService.updateStatus(reviewDto.getPostId(), 4);

        //TODO : donation entity 찾아서 인원수 및 모금액 부분 수정하기
        reviewDto.setRaisedPeople(0L);
        reviewDto.setAmount(0L);


        Review review = Review.builder()
                .postId(reviewDto.getPostId())
                .content(reviewDto.getContent())
                .createdAt(reviewDto.getCreatedAt())
                .amount(reviewDto.getAmount())
                .raisedPeople(reviewDto.getRaisedPeople())
                .build();

        reviewRepository.save(review);
        reviewDto.setId(review.getId());

        imgService.saveImg(new ArrayList<>(Arrays.asList(reviewDto.getReceipt())), reviewDto.getId(), 2L);
        imgService.saveImg(reviewDto.getImages(), reviewDto.getId(), 1L);

        return reviewDto;
    }

    public List<ReviewDto> findAllReviews() {

        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> reviewDtos = new ArrayList<>();

        for(Review review : reviews) {

            ReviewDto reviewDto = ReviewDto.builder()
                    .id(review.getId())
                    .postId(review.getPostId())
                    .content(review.getContent())
                    .amount(review.getAmount())
                    .raisedPeople(review.getRaisedPeople())
                    .createdAt(review.getCreatedAt())
                    .images(imgService.findImgByPostId(review.getId(), 1L))
                    .receipt(imgService.findImgByPostId(review.getId(), 2L).get(0))
                    .comments(commentService.findByReviewId(review.getId()))
                    .build();

            reviewDtos.add(reviewDto);
        }
        return reviewDtos;

    }

    public ReviewDto findReview(Long review_id) {

        Optional<Review> reviewOptional = reviewRepository.findById(review_id);
        return this.getReviewDto(reviewOptional);
    }

    public ReviewDto findReviewByPostId(Long post_id) {

        Optional<Review> reviewOptional = reviewRepository.findByPostId(post_id);
        return this.getReviewDto(reviewOptional);
    }

    public ReviewDto getReviewDto(Optional<Review> reviewOptional) {

        ReviewDto reviewDto = new ReviewDto();

        reviewOptional.ifPresent(review -> {
            reviewDto.setId(review.getId());
            reviewDto.setPostId(review.getPostId());
            reviewDto.setContent(review.getContent());
            reviewDto.setAmount(review.getAmount());
            reviewDto.setRaisedPeople(review.getRaisedPeople());
            reviewDto.setCreatedAt(review.getCreatedAt());
            reviewDto.setImages(imgService.findImgByPostId(review.getId(), 1L));
            reviewDto.setReceipt(imgService.findImgByPostId(review.getId(), 2L).get(0));
            reviewDto.setComments(commentService.findByReviewId(review.getId()));

        });
        return reviewDto;

    }

    public void deleteReview(Long review_id) {

        reviewRepository.deleteById(review_id);
        imgService.deleteImg(review_id, 1L);
        commentService.deleteByReviewId(review_id);

    }






}
