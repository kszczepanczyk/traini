package pl.polsl.traini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.dto.client.info.GetClientProgressRsp;
import pl.polsl.traini.model.dto.client.update.UpdateUserReq;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.training.TrainingDate;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.location.LocationService;
import pl.polsl.traini.service.progress.ProgressEntityService;
import pl.polsl.traini.service.progress.ProgressService;
import pl.polsl.traini.service.seq.SeqGeneratorService;
import pl.polsl.traini.service.trainer.RegisterEntityService;
import pl.polsl.traini.service.trainer.TrainerService;
import pl.polsl.traini.service.training.TrainingService;
import pl.polsl.traini.service.user.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ClientApiImpl {
    private final SeqGeneratorService generatorService;
    private final UserService userService;
    private final RegisterEntityService registerEntityService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final LocationService locationService;
    private final ProgressService progressService;
    private final ProgressEntityService progressEntityService;

    @Autowired
    public ClientApiImpl(SeqGeneratorService generatorService, UserService userService, RegisterEntityService registerEntityService, TrainerService trainerService, TrainingService trainingService, LocationService locationService, ProgressService progressService, ProgressEntityService progressEntityService) {
        this.generatorService = generatorService;
        this.userService = userService;
        this.registerEntityService = registerEntityService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.locationService = locationService;
        this.progressService = progressService;
        this.progressEntityService = progressEntityService;
    }

    public User addClient(AddClientReq req, String username) {
        long trainerId = trainerService.findByRegisterId(registerEntityService.findByEmail(username).getId()).getId();
        return userService.save(createUser(req, trainerId));
    }

    public GetClientInfoRsp getClient(Long clientId) {
        return createGetClientInfoRsp(userService.findByUserId(clientId));
    }

    public User updateClient(UpdateUserReq req, Long userId) {
        return createOrFindUser(req, userId);
    }

    public void deleteClient(Long userId) {
        deleteProgress(userId);

        deleteTrainings(userId);

        userService.delete(userService.findByUserId(userId));
    }

    public List<User> getClientList(String username) {
        return userService.findByTrainerId(
                trainerService.findByRegisterId(registerEntityService.findByEmail(username).getId()).getId()
        );
    }

    private void deleteProgress(Long userId) {
        progressService.findListByUserId(userId)
                .forEach(
                        progress -> {
                            deleteProgressEntity(progress.getId());
                            progressService.delete(progress);
                        }
                );
    }

    private void deleteProgressEntity(Long progressId) {
        progressEntityService.deleteAll(progressId);
    }

    private void deleteTrainings(Long userId) {
        trainingService.deleteAll(userId);
    }

    private User createUser(AddClientReq req, long trainerId) {
        return User.builder()
                .id(generatorService.generateSeq(User.SEQUENCE_NAME))
                .trainerId(trainerId)
                .name(req.getName())
                .surname(req.getSurname())
                .city(req.getCity())
                .description(req.getDescription())
                .gender(req.getGender())
                .phone(req.getPhone())
                .createdAt(new Date())
                .modifyAt(new Date())
                .build();
    }

    private User createOrFindUser(UpdateUserReq req, Long id) {
        User user = userService.findByUserId(id);

        user.setName("".equals(req.getName()) ? user.getName() : req.getName());
        user.setSurname("".equals(req.getSurname()) ? user.getSurname() : req.getSurname());
        user.setDescription("".equals(req.getDescription()) ? user.getDescription() : req.getDescription());
        user.setPhone("".equals(req.getPhone()) ? user.getPhone() : req.getPhone());
        user.setPhoto("".equals(req.getPhoto()) ? user.getPhoto() : req.getPhoto());
        user.setCity("".equals(req.getCity()) ? user.getCity() : req.getCity());
        user.setGender("".equals(req.getGender()) ? user.getGender() : req.getGender());
        user.setModifyAt(new Date());

        return userService.save(user);
    }

    private GetClientInfoRsp createGetClientInfoRsp(User user) {
        List<Training> trainings = trainingService.getTrainingsByUserId(user.getId());
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
                                    .location(locationService.findByLocationId(training.getLocationId()).getName())
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

        progressService.findListByUserId(userId)
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
                .toInstant());
    }
}
