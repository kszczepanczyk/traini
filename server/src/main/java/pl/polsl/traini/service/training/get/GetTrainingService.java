package pl.polsl.traini.service.training.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.LocationRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.training.TrainingDate;
import pl.polsl.traini.model.user.User;

@Service
public class GetTrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public GetTrainingService(TrainingRepository trainingRepository, UserRepository userRepository, LocationRepository locationRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public GetTrainingRsp getTraining(Long trainingId) throws NoTrainingException {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow( () -> new NoTrainingException("No training for trainingId = " + trainingId));

        User user = userRepository.findById(training.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("No user with userId = " + training.getUserId()));

        Location location = locationRepository.findById(training.getLocationId())
                .orElseThrow( () -> new NoTrainingException("No location for locationId = " + training.getLocationId()));

        return createGetTrainingRsp(training, user, location);
    }

    private GetTrainingRsp createGetTrainingRsp(Training training, User user, Location location) {
        return GetTrainingRsp.builder()
          .id(training.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .trainingDate(TrainingDate.builder()
                        .start(training.getTrainingDateStart())
                        .end(training.getTrainingDateEnd())
                        .build())
                .location(location.getName())
                .description(training.getDescription())
                .cycled(training.isCycled())
          .trainingName(training.getName())
                .build();
    }
}
