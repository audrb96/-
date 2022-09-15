package neonaduri.api.service;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import neonaduri.dto.response.ReviewTagsRes;
import neonaduri.dto.response.SpotDetailsRes;
import neonaduri.dto.response.SpotSearchRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final RegionRepository regionRepository;

    private final ClassificationRepository classificationRepository;

    public SpotDetailsRes getSpotDetailsInfo(Long spotId) {

        /* 1. fetch join을 통해 review까지 가져와서 */
        Spot spot = spotRepository.findDetailsSpotBySpotId(spotId);

        /* 2. review 목록에서 리뷰 내용고 태그를 꺼내와서 ReviewTagsRes로 정리해준다. */
        List<ReviewTagsRes> reviews = spot.getReviews().stream()
                .map(review -> ReviewTagsRes.builder()
                        .reviewContent(review.getReviewContent())
                        .reviewImage(review.getReviewImage())
                        .tagContents(review.getTags().toString())
                        .build())
                .sorted()
                .collect(Collectors.toList());

        /* 3. 마지막으로 SpotDetailsRes로 반환시켜준다. */
        return SpotDetailsRes.builder()
                .spotContent(spot.getSpotContent())
                .spotName(spot.getSpotName())
                .spotImage(spot.getSpotImage())
                .reviewContainsTags(reviews)
                .build();
    }

    public List<SpotSearchRes> getFitConditionSpotInfo(Map<String, List<String>> conditions) {

        final String SIDO = "sido";
        final String SIGUNGU = "sigungu";
        final String MYEON = "myeon";


//        /* 1. 해당 지역을 찾고 */
//        Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(
//                conditions.get(SIDO).get(0), conditions.get(SIGUNGU).get(0), conditions.get(MYEON).get(0)).orElseThrow(() -> {
//            throw new IllegalArgumentException();
//        });

        /* 2. 중분류, 소분류를 통해  */
        /**
         *
         * 자연 -> 슾, 체험활동, ...
         * 액티비티 -> 수영장, 목욕탕 ...
         */
        List<String[]> collect = conditions.keySet().stream()
                .filter(key -> !key.equals(SIDO) && !key.equals(SIGUNGU) && !key.equals(MYEON))
                .map(mdClass -> mdClass.split(","))
                .collect(Collectors.toList());

        for (String[] col : collect) {
            System.out.println("col.toString() = " + Arrays.toString(col));
        }

        /* 3. 테마에 맞는 장소를 찾고 반환해준다. */
//        List<SpotSearchRes> spotSearchRes =


        return null;
    }
}
