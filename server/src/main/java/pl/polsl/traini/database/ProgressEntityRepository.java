package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.progressEntity.ProgressEntity;

@Repository
public interface ProgressEntityRepository extends MongoRepository<ProgressEntity, Long> {
}
