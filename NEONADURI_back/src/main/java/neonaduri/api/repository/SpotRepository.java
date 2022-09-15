package neonaduri.api.repository;

import neonaduri.domain.Spot;
import neonaduri.dto.response.SpotContentRes;
import neonaduri.dto.response.SpotDetailsRes;
import neonaduri.dto.response.SpotSearchRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Spot findSpotBySpotId(Long spotId); // Optional X. 빈 값 있지 않음.

    @Query("select s from Spot s join fetch s.reviews")
    Spot findDetailsSpotBySpotId(Long spotId);

//    Optional<List<SpotSearchRes>> findSpotsBy

}
