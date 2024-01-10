package pl.polsl.traini.service.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.training.Training;

import java.util.Date;
import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Training findTrainingById(Long trainingId) {
        try {
            return trainingRepository.findById(trainingId)
                    .orElseThrow(() -> new NoTrainingException("No training for trainingId = " + trainingId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Training();
        }
    }

    public Training save(Training t) {
        return trainingRepository.save(t);
    }

    public void delete(Long trainingId) {
        try {
            trainingRepository.delete(trainingRepository.findById(trainingId)
                    .orElseThrow(() -> new NoTrainingException("No training for trainingId = " + trainingId)));
        } catch (NoTrainingException e) {
            System.out.println("Can't delete training with trainingId = " + trainingId);
        }
    }

    public List<Training> getTrainingsByTrainerIdAndDates(Long trainerId, Date date, Date endDate) {
        return trainingRepository.findByTrainerIdAndTrainingDateStartBetween(trainerId, date, endDate);
    }

    public List<Training> getTrainingsByTrainerIdAndAfterDate(Long trainerId, Date date) {
        return trainingRepository.findAllByTrainerIdAndTrainingDateStartAfter(trainerId, date);
    }

    public List<Training> getTrainingsCycledAndInDate(Long trainerId, Date date) {
        return trainingRepository.findByTrainerIdAndCycledAndTrainingDateStartBefore(trainerId, true, date);
    }

    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findTrainingByUserIdOrderByTrainingDateStart(userId);
    }

    public void deleteAll(Long userId) {
        trainingRepository.deleteAll(trainingRepository.findTrainingByUserId(userId));
    }
}
