package neonaduri.api.repository;

import neonaduri.domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    Classification findClassificationByMdClassAndSmClass(String mdClass, String smClass);
}
