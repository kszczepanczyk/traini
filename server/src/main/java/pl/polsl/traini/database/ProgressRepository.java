package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.progress.Progress;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, Long> {
    Optional<Progress> findByUserIdAndName(Long userId, String name);

  Optional<Progress> findByUserIdAndId(Long userId, long progressId);

    List<Progress> findAllByUserId(Long userId);
}
