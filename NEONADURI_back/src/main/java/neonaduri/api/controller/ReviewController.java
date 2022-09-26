package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.service.ReviewService;
import neonaduri.dto.request.CreateReviewReq;
import neonaduri.dto.request.ModifyReviewReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    /** A02: 장소에 따른 리뷰 작성 */
    @PostMapping
    public ResponseEntity<HttpStatus> createReview(@Valid @RequestBody CreateReviewReq createReviewReq) {
        reviewService.postReview(createReviewReq);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> modifyReview(@Valid @RequestBody ModifyReviewReq modifyReviewReq) {
        reviewService.putReview(modifyReviewReq);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/pass/{reviewId}/{password}")
    public ResponseEntity<?> confirmPassword(@PathVariable("reviewId") Long reviewId, @PathVariable("password") String password) {
        return new ResponseEntity<>(reviewService.comparePass(reviewId,password),HttpStatus.OK);
    }

}
