package neonaduri.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import neonaduri.domain.statistics.Satisfaction;
import neonaduri.domain.type.SatisfactionType;

@Getter
public class SatisfactionRes {

    private Long satId;

    private Integer satYear;

    private String satRegion;

    private Float satScore;

    private SatisfactionType satType;

    public SatisfactionRes(Satisfaction satisfaction){
        this.satId = satisfaction.getSatId();
        this.satYear = satisfaction.getSatYear();
        this.satRegion = satisfaction.getSatRegion();
        this.satScore = satisfaction.getSatScore();
        this.satType = satisfaction.getSatType();
    }


}
