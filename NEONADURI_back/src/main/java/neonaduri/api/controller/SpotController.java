package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.service.SpotService;
import neonaduri.dto.request.SearchSpotReq;
import neonaduri.dto.response.SearchSpotDto;
import neonaduri.dto.response.SpotDetailsRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spot")
public class SpotController {

    private final SpotService spotService;

    /** A01: 특정 장소에 작성된 설명과 리뷰 출력 API */
    @GetMapping("/{spotId}")
    public ResponseEntity<SpotDetailsRes> showDetailsSpotInfo(@PathVariable("spotId") Long spotId) {
        return ResponseEntity.ok(spotService.getSpotDetailsInfo(spotId));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SearchSpotDto>> showSpotsByCon(SearchSpotReq searchSpotReq, Pageable pageable) {
        return ResponseEntity.ok(spotService.searchSpotService(searchSpotReq,pageable));
    }
}
