package pl.polsl.traini.service.home.trainings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.*;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.training.TrainingDate;
import pl.polsl.traini.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HomeTrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

  @Autowired
    public HomeTrainingService(TrainingRepository trainingRepository, TrainerRepository trainerRepository, RegisterRepository registerRepository, UserRepository userRepository, LocationRepository locationRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.registerRepository = registerRepository;
        this.userRepository = userRepository;
    this.locationRepository = locationRepository;
  }

    public List<GetTrainingRsp> getTrainings(String username, Date date) {
      Registered registered =  registerRepository.findByEmail(username).orElseThrow(
        () -> new UsernameNotFoundException("Cant find registered by email = " + username)
      );
      Trainer trainer = trainerRepository.findByRegisteredId(registered.getId()).orElseThrow(
        () -> new UsernameNotFoundException("Cant find trainer by email = " + username)
      );
      List<Training> trainings = trainingRepository.findByTrainerIdAndTrainingDateStartAfter(trainer.getId(), date);

      List<GetTrainingRsp> trainingRspList = new ArrayList<>();

      trainings.forEach(
        training -> {
          User user = userRepository.findById(training.getUserId())
            .orElseThrow(() -> new UsernameNotFoundException("No user with userId = " + training.getUserId()));

          trainingRspList.add(GetTrainingRsp.builder()
            .name(user.getName())
            .surname(user.getSurname())
            .trainingDate(TrainingDate.builder()
              .start(training.getTrainingDateStart())
              .end(training.getTrainingDateEnd())
              .build())
            .description(training.getDescription())
            .location(locationRepository.findById(training.getLocationId())
              .orElseThrow(() -> new UsernameNotFoundException("No location with userId = " + training.getUserId())).getName())
            .cycled(training.isCycled())
              .trainingName(training.getName())
            .build());
        }
      );

        return trainingRspList;
    }
}
