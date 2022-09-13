package neonaduri.api.repository;

import neonaduri.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findRegionBySidoAndSigunguAndMyeon(String sido, String sigungu, String myeon);
}
