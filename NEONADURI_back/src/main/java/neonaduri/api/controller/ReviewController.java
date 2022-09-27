package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.service.ReviewService;
import neonaduri.dto.request.CreateReviewReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import neonaduri.dto.request.ModifyReviewReq;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    /** A02: 장소에 따른 리뷰 작성 */
    @PostMapping
    public ResponseEntity<HttpStatus> createReview(@Valid CreateReviewReq createReviewReq) throws IOException {
        reviewService.postReview(createReviewReq);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> modifyReview(@Valid ModifyReviewReq modifyReviewReq) throws IOException{
        reviewService.putReview(modifyReviewReq);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/pass/{reviewId}/{password}")
    public ResponseEntity<?> confirmPassword(@PathVariable("reviewId") Long reviewId, @PathVariable("password") String password) {
        return new ResponseEntity<>(reviewService.comparePass(reviewId,password),HttpStatus.OK);
    }

}
