package neonaduri.api.service;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.SatisfactionRepository;
import neonaduri.api.repository.SelectedRepository;
import neonaduri.api.repository.VisitedRepository;
import neonaduri.domain.statistics.Satisfaction;
import neonaduri.domain.statistics.Selected;
import neonaduri.domain.statistics.Visited;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsService {

    private final SatisfactionRepository satisfactionRepository;

    private final SelectedRepository selectedRepository;

    private final VisitedRepository visitedRepository;

    //만족도 조회
    public List<Satisfaction> findSatisfaction() {
        return satisfactionRepository.findAll();

    }

    //방문지 선택이유
    public List<Selected> findSelected(){
        return selectedRepository.findAll();
    }

    //관광지여행횟수
    public List<Visited> findVisited(){
        return visitedRepository.findAll();
    }

}
