package neonaduri.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewTagsRes {

    private final String reviewContent;
    private final String reviewImage;
    private final String tagContents;

    @Builder
    public ReviewTagsRes(String reviewContent, String reviewImage, String tagContents) {
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.tagContents = tagContents;
    }
}
