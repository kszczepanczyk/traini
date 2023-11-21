package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.progress.Progress;

import java.util.List;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, Long> {
    List<Progress> findAllByUserIdAndNameOrCreatedAt(Long userId, String name);
}
