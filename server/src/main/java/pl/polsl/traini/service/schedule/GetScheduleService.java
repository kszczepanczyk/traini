package pl.polsl.traini.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.model.training.Training;

import java.util.List;

@Service
public class GetScheduleService {

    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final RegisterRepository registerRepository;

    @Autowired
    public GetScheduleService(TrainingRepository trainingRepository, TrainerRepository trainerRepository, RegisterRepository registerRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.registerRepository = registerRepository;
    }

    public List<Training> getSchedule(String username) {
        return
                trainingRepository.findByTrainerId(
                        trainerRepository.findByRegisteredId(
                                registerRepository.findByEmail(username).orElseThrow(
                                        () -> new UsernameNotFoundException("Cant find registred by email = " + username)
                                ).getId()).orElseThrow(
                                () -> new UsernameNotFoundException("Cant find trainer by email = " + username)
                        ).getId());
    }
}
