package pl.polsl.traini.service.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.trainer.Trainer;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer findByRegisterId(Long registerId) {
        try {
            return trainerRepository.findByRegisteredId(registerId)
                    .orElseThrow(() -> new NoTrainingException("No training for registerId = " + registerId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Trainer();
        }
    }

    public Trainer save(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
