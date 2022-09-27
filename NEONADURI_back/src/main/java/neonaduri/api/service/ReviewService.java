package neonaduri.api.service;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ReviewRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.domain.Review;
import neonaduri.domain.Tag;
import neonaduri.dto.request.CreateReviewReq;
import neonaduri.dto.request.ModifyReviewReq;
import neonaduri.utils.S3Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final S3Utils s3Utils;
    private final SpotRepository spotRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    public void postReview(CreateReviewReq createReviewReq) throws IOException {
        /* Tag 먼저 생성 */
        Set<Tag> tags = Arrays.stream(createReviewReq.getTags().split(", "))
                .map(Tag::new)
                .collect(Collectors.toSet());

        s3Utils.upload(createReviewReq.getReviewImage(), "review");

        /* Review 생성하기 */
        Review review = Review.builder()
                .spotId(spotRepository.findSpotBySpotId(createReviewReq.getSpotId()))
                .tags(tags)
                .reviewContent(createReviewReq.getReviewContent())
                .reviewDate(LocalDateTime.now())
                .reviewImage(createReviewReq.getReviewImage().getOriginalFilename())
                .reviewPassword(passwordEncoder.encode(createReviewReq.getReviewPassword()))
                .build();

        /* Review DB 반영 */
        reviewRepository.save(review);
    }

    public void putReview(ModifyReviewReq modifyReviewReq) throws IOException {

        Review review = reviewRepository.findReviewByReviewId(modifyReviewReq.getReviewId());
        Set<Tag> tags = Arrays.stream(modifyReviewReq.getTags()
                        .split(", "))
                .map(Tag::new)
                .collect(Collectors.toSet());
        s3Utils.upload(modifyReviewReq.getReviewImage(), "review");
        review.modifyReview(tags, modifyReviewReq.getReviewContent(), LocalDateTime.now(), modifyReviewReq.getReviewImage().getOriginalFilename());
    }


    public boolean comparePass(Long reviewId, String password) {
        Review review = reviewRepository.findReviewByReviewId(reviewId);
        if (passwordEncoder.matches(password, review.getReviewPassword())) {
            return true;
        }
        return false;
    }
}
