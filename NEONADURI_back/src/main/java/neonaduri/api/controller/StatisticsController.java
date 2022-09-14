package neonaduri.api.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import neonaduri.api.service.StatisticsService;
import neonaduri.domain.statistics.Satisfaction;
import neonaduri.domain.statistics.Selected;
import neonaduri.domain.statistics.Visited;
import net.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    //만족도
    @GetMapping("/sat")
    public List<Satisfaction> getSatisfaction(){
        return statisticsService.findSatisfaction();
    }

    //선택 이유
    @GetMapping("/sel")
    public List<Selected> getSelected(){
        return statisticsService.findSelected();
    }

    //관광지 방문 횟수
    @GetMapping("/visited")
    public List<Visited> getVisited(){
        return statisticsService.findVisited();
    }
}
