package neonaduri.dto.response;

import lombok.Getter;
import neonaduri.domain.statistics.Selected;

@Getter
public class SelectedRes {

    private Long selId;

    private String selReason;

    private Integer visitedYear;

    private Float selPercent;

    public SelectedRes(Selected selected){
        this.selId = selected.getSelId();
        this.selReason = selected.getSelReason();
        this.visitedYear = selected.getVisitedYear();
        this.selPercent = selected.getSelPercent();
    }

}
