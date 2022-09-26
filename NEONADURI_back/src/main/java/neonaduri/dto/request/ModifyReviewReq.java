package neonaduri.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class ModifyReviewReq {

    @NotNull
    private final Long reviewId;
    @NotNull
    private final String reviewImage;

    @NotNull
    private final String reviewContent;

    private final String tags;

    @Builder
    public ModifyReviewReq(Long reviewId, String reviewImage, String reviewContent, String tags) {
        this.reviewId = reviewId;
        this.reviewImage = reviewImage;
        this.reviewContent = reviewContent;
        this.tags = tags;
    }

}
