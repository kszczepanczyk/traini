package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.trainer.Trainer;

import java.util.Optional;

@Repository
public interface TrainerRepository extends MongoRepository<Trainer, Long> {
    Optional<Trainer> findByRegisteredId(Long registerId);
}
