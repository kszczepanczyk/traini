package pl.polsl.traini.service.home.trainings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.model.training.Training;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HomeTrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final RegisterRepository registerRepository;

    @Autowired
    public HomeTrainingService(TrainingRepository trainingRepository, TrainerRepository trainerRepository, RegisterRepository registerRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.registerRepository = registerRepository;
    }

    public List<Training> getTrainings(String username, Date date) {
        return
                trainingRepository.findByTrainerIdAndTrainingDateStart(
                    trainerRepository.findByRegisteredId(
                        registerRepository.findByEmail(username).orElseThrow(
                                    () -> new UsernameNotFoundException("Cant find registered by email = " + username)
                                ).getId()).orElseThrow(
                                        () -> new UsernameNotFoundException("Cant find trainer by email = " + username)
                            ).getId(), date);
    }
}
