package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.training.Training;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends MongoRepository<Training, Long> {
    List<Training> findByTrainerIdAndTrainingDateStartBetweenOrderByTrainingDateStartAsc(Long trainerId, Date date, Date endDate);

    List<Training> findByTrainerId(Long trainerId);
}
