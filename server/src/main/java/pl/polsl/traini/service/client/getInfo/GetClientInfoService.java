package pl.polsl.traini.service.client.getInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.LocationRepository;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.dto.client.info.GetClientProgressRsp;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.training.TrainingDate;
import pl.polsl.traini.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GetClientInfoService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final LocationRepository locationRepository;
    private final ProgressRepository progressRepository;

    @Autowired
    public GetClientInfoService(UserRepository userRepository, TrainingRepository trainingRepository, LocationRepository locationRepository, ProgressRepository progressRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.locationRepository = locationRepository;
        this.progressRepository = progressRepository;
    }

    public GetClientInfoRsp getClientInfo(Long clientId) {
        User user = userRepository.findById(clientId).orElseThrow(
                () -> new UsernameNotFoundException("Cant find user by userId = " + clientId));
        return createGetClientInfoRsp(user);
    }

    private GetClientInfoRsp createGetClientInfoRsp(User user) {
        List<Training> trainings = trainingRepository.findTrainingByUserIdOrderByTrainingDateStart(user.getId());
        List<GetTrainingRsp> trainingRspList = new ArrayList<>();
            trainings
                .stream().filter(t -> t.getTrainingDateStart().after(getTodayStartDate()) || (t.isCycled()))
                .forEach(
                    training -> {
                        trainingRspList.add(GetTrainingRsp.builder()
                            .id(training.getId())
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

        Collections.sort(trainingRspList);

        return GetClientInfoRsp.builder()
                .id(user.getId())
                .description(user.getDescription())
                .name(user.getName())
                .surname(user.getSurname())
                .gender(user.getGender())
                .phone(user.getPhone())
                .photo(user.getPhoto())
                .city(user.getCity())
                .trainingList(trainingRspList)
                .progressList(findClientProgress(user.getId()))
                .build();
    }

    private List<GetClientProgressRsp> findClientProgress(Long userId) {
        ArrayList<GetClientProgressRsp> rsp = new ArrayList<>();

        progressRepository.findAllByUserId(userId)
                .forEach(progress -> rsp.add(GetClientProgressRsp.builder()
                                .progressId(progress.getId())
                                .progressName(progress.getName())
                                .unit(progress.getUnit())
                                .value(progress.getValue())
                                .build()));

        return rsp;
    }

    private Date getTodayStartDate() {
        return Date.from(LocalDate.now()
          .atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant()) ;
    }
}
