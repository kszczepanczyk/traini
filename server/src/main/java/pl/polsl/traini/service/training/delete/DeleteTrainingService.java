package pl.polsl.traini.service.training.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.TrainingRepository;

@Service
public class DeleteTrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public DeleteTrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public void deleteTraining (Long trainingId) {
        trainingRepository.delete(trainingRepository.findById(trainingId).orElseThrow());
    }
}
