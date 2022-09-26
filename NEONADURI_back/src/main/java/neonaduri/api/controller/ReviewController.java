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

}
