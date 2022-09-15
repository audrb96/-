package neonaduri.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SpotSearchRes {

    private final Long spotId;

    private final String spotName;

    private final String spotImage;

    @Builder
    public SpotSearchRes(Long spotId, String spotName, String spotImage) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.spotImage = spotImage;
    }
}
