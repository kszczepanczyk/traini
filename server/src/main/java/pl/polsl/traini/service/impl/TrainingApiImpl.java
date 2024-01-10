package pl.polsl.traini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.exceptions.NoLocationException;
import pl.polsl.traini.model.dto.training.add.AddTrainingReq;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.dto.training.update.UpdateTrainingReq;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.model.training.TrainingDate;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.location.LocationService;
import pl.polsl.traini.service.seq.SeqGeneratorService;
import pl.polsl.traini.service.trainer.RegisterEntityService;
import pl.polsl.traini.service.trainer.TrainerService;
import pl.polsl.traini.service.training.TrainingService;
import pl.polsl.traini.service.user.UserService;

import java.text.DateFormatSymbols;
import java.util.*;

@Service
public class TrainingApiImpl {
    private final TrainingService trainingService;
    private final TrainerService trainerService;
    private final RegisterEntityService registerEntityService;
    private final UserService userService;
    private final LocationService locationService;
    private final SeqGeneratorService generatorService;

    @Autowired
    public TrainingApiImpl(TrainingService trainingService, TrainerService trainerService, RegisterEntityService registerEntityService, UserService userService, LocationService locationService, SeqGeneratorService generatorService) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.registerEntityService = registerEntityService;
        this.userService = userService;
        this.locationService = locationService;
        this.generatorService = generatorService;
    }

    public GetTrainingRsp getTraining(Long trainingId) {
        Training training = trainingService.findTrainingById(trainingId);
        User user = userService.findByUserId(training.getUserId());
        Location location = locationService.findByLocationId(training.getLocationId());

        return createGetTrainingRsp(training, user, location);
    }

    public Training updateTraining(Long trainingId, UpdateTrainingReq req) {
        return trainingService.save(findAndCreateTraining(trainingId, req));
    }

    public Training addTraining(String username, AddTrainingReq req) {
        try {
            return trainingService.save(createTraining(req, username));
        } catch (NoLocationException e) {
            System.out.println("Can't create new traning for username = " + username);
            return new Training();
        }
    }

    public void deleteTraining (Long trainingId) {
        trainingService.delete(trainingId);
    }

    public List<GetTrainingRsp> getTrainingList(String username, Date date) {
        Registered registered =  registerEntityService.findByEmail(username);
        Trainer trainer = trainerService.findByRegisterId(registered.getId());

        Date endDate = new Date(date.getTime() + (long)(1000 * 60 * 60 * 24));
        Date today = new Date();
        if(today.getDay() == date.getDay() && today.getMonth() == date.getMonth() && today.getYear() ==  date.getYear()) {
            date = today;
        }

        List<Training> trainings = trainingService.getTrainingsByTrainerIdAndDates(trainer.getId(), date, endDate);
        Date finalDate = date;
        List<Training> cycledTrainings = trainingService.getTrainingsCycledAndInDate(trainer.getId(), date)
                .stream().filter(t -> checkDay(t.getTrainingDateStart(), finalDate))
                .map(this::changeDate)
                .toList();

        return createGetTrainingRspList(trainings, cycledTrainings);
    }

    public List<GetTrainingRsp> getSchedule(String username) {
        Registered registered =  registerEntityService.findByEmail(username);
        Trainer trainer = trainerService.findByRegisterId(registered.getId());
        Date date = new Date();

        List<Training> trainings = trainingService.getTrainingsByTrainerIdAndAfterDate(trainer.getId(), date);
        List<Training> cycledTrainings = trainingService.getTrainingsCycledAndInDate(trainer.getId(), date)
                .stream().filter(t -> checkDay(t.getTrainingDateStart(), date))
                .map(this::changeDate)
                .toList();

        return createGetTrainingRspList(trainings, cycledTrainings);
    }

    private Training findAndCreateTraining(Long trainingId, UpdateTrainingReq req) {
        Training training = trainingService.findTrainingById(trainingId);

        training.setName(req.getName());
        training.setTrainingDateStart(req.getTrainingDate().getStart());
        training.setTrainingDateEnd(req.getTrainingDate().getEnd());
        training.setUserId(req.getUserId());
        training.setDescription(req.getDescription());
        training.setLocationId(
                locationService.findByName(req.getName()).getId());
        training.setCycled(req.isCycled());

        return training;
    }

    private Training createTraining(AddTrainingReq req, String username) throws NoLocationException {
        return Training.builder()
                .id(generatorService.generateSeq(Training.SEQUENCE_NAME))
                .trainerId(getTrainerId(username))
                .name(req.getName())
                .trainingDateStart(req.getTrainingDate().getStart())
                .trainingDateEnd(req.getTrainingDate().getEnd())
                .createdAt(new Date())
                .description(req.getDescription())
                .cycled(req.isCycled())
                .userId(req.getUserId())
                .locationId(
                        locationService.findByName(req.getLocalization())
                                .getId())
                .build();
    }

    private Long getTrainerId(String username) {
        return trainerService.findByRegisterId(registerEntityService.findByEmail(username).getId()).getId();
    }

    private Training changeDate(Training t) {
        t.setTrainingDateStart(changeDay(t.getTrainingDateStart()));
        t.setTrainingDateEnd(changeDay(t.getTrainingDateEnd()));

        return t;
    }

    private Date changeDay(Date date) {
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendarToday.get(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    private boolean checkDay(Date date, Date dateReq) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(dateReq);

        String[] dayNames = new DateFormatSymbols().getWeekdays();

        return dayNames[calendarToday.get(Calendar.DAY_OF_WEEK)].equals(dayNames[calendar.get(Calendar.DAY_OF_WEEK)]);
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

    private List<GetTrainingRsp> createGetTrainingRspList (List<Training> trainings, List<Training> cycledTrainings) {
        trainings.addAll(cycledTrainings);
        Collections.sort(trainings);
        List<GetTrainingRsp> trainingRspList = new ArrayList<>();

        trainings.forEach(
                training -> {
                    User user = userService.findByUserId(training.getUserId());
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
        return trainingRspList;
    }
}
