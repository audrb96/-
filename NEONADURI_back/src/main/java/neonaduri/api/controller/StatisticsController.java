package neonaduri.api.controller;

import jdk.jfr.StackTrace;
import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import neonaduri.api.service.StatisticsService;
import neonaduri.domain.statistics.Satisfaction;
import neonaduri.domain.statistics.Selected;
import neonaduri.domain.statistics.Visited;
import net.bytebuddy.asm.Advice;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputFromUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /** 만족도 */
//    @GetMapping("/sat")
//    public ResponseEntity<List<Satisfaction>> getSatisfaction() {
////        try {
//            return new ResponseEntity<>(statisticsService.findSatisfaction(), HttpStatus.ACCEPTED);
////        } catch (Exception e) {
////            System.out.println("안돼용,,");
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
//    }


    @GetMapping("/sat")
    public ResponseEntity<?> getSatisfaction() {
        return new ResponseEntity<>(statisticsService.findSatisfaction(),HttpStatus.OK);
    }

    /**선택 이유 */
    @GetMapping("/sel")
    public ResponseEntity<?> getSelected() {
        return new ResponseEntity<>(statisticsService.findSelected(), HttpStatus.OK);
    }

    /** 관광지 방문 횟수 */
    @GetMapping("/visited")
    public ResponseEntity<List<Visited>> getVisited() {
        return new ResponseEntity<>(statisticsService.findVisited(), HttpStatus.OK);

    }
}
