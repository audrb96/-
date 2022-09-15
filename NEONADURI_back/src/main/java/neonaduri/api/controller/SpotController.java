package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.service.SpotService;
import neonaduri.dto.response.SpotDetailsRes;
import neonaduri.dto.response.SpotSearchRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spot")
public class SpotController {

    private final SpotService spotService;

    /**
     * A01: 특정 장소에 작성된 설명과 리뷰 API
     */
    @GetMapping("/{spotId}")
    public ResponseEntity<SpotDetailsRes> showDetailsSpotInfo(@PathVariable("spotId") Long spotId) {
        return ResponseEntity.ok(spotService.getSpotDetailsInfo(spotId));
    }

    /**ㅏ
     * A03: 조건에 따른 스팟 검색 결과 API
     */
    @GetMapping("/search")
    public ResponseEntity<List<SpotSearchRes>> getConditionSpotsInfo(@RequestParam Map<String, List<String>> conditions) {
        return ResponseEntity.ok(spotService.getFitConditionSpotInfo(conditions));
    }
}
