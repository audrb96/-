package neonaduri.api.repository;

import neonaduri.domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    //중분류, 소분류를 넣어서 일치하는 정보 추출
    Classification findClassificationByMdClassAndSmClass(String mdClass, String smClass);
}
