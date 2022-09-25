package neonaduri.api.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import neonaduri.api.service.SpotService;
import neonaduri.dto.request.SearchSpotReq;
import neonaduri.dto.response.SearchSpotDto;
import neonaduri.dto.response.SpotDetailsRes;
import neonaduri.utils.S3Uploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

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
