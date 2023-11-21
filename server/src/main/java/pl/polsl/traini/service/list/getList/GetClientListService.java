package pl.polsl.traini.service.list.getList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetClientListService {

    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public GetClientListService(RegisterRepository registerRepository, UserRepository userRepository, TrainerRepository trainerRepository, TrainingRepository trainingRepository) {
        this.registerRepository = registerRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
    }

    public List<User> getUserList (String username) {
        List<Training> trainings = trainingRepository.findByTrainerId(
                trainerRepository.findByRegisteredId(
                        registerRepository.findByEmail(username)
                                .orElseThrow(
                                        () -> new UsernameNotFoundException("Cant find register with username = " + username))
                                .getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Cant find trainer with username = " + username))
                        .getId()
                );
        List<Long> userIds = new ArrayList<>();

        trainings.forEach(training -> userIds.add(training.getUserId()));

        return userRepository.findAllById(userIds);
    }
}
