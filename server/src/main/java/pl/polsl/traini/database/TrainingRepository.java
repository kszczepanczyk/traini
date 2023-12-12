package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.training.Training;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends MongoRepository<Training, Long> {
    List<Training> findByTrainerIdAndTrainingDateStartBetween(Long trainerId, Date date, Date endDate);
    List<Training> findByTrainerIdAndCycledAndTrainingDateStartBefore(Long trainerId, boolean cycled, Date date);
    List<Training> findByTrainerId(Long trainerId);
    List<Training> findTrainingByUserId(Long userId);
    List<Training> findTrainingByUserIdOrderByTrainingDateStart(Long userId);
}
