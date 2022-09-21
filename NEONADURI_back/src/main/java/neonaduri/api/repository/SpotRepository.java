package neonaduri.api.repository;

import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import neonaduri.dto.response.MyeonResponseDto;
import neonaduri.dto.response.SidoResponseDto;
import neonaduri.dto.response.SigunguResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {


    //시군구, 면을 넣어서 일치하는 정보를 추출
    List<Spot> findSpotBySpotName(String spotName);

}
