package neonaduri.dto.response;

import lombok.Getter;
import neonaduri.domain.statistics.Visited;

@Getter
public class VisitedRes {

    private  Long visitedId;

    private Integer visitedRegion;

    private Integer visitedYear;

    private Integer visitedNum;

    public VisitedRes(Visited visited){
        this.visitedId = visited.getVisitedId();
        this.visitedRegion = visited.getVisitedRegion();
        this.visitedYear = visited.getVisitedYear();
        this.visitedNum = visited.getVisitedNum();
    }
}
