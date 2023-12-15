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

import java.text.DateFormatSymbols;
import java.util.*;

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
      Date endDate = new Date(date.getTime() + (long)(1000 * 60 * 60 * 24));

      Date today = new Date();
      if(today.getDay() == date.getDay() && today.getMonth() == date.getMonth() && today.getYear() ==  date.getYear()) {
        date = today;
      }

      List<Training> trainings = trainingRepository.findByTrainerIdAndTrainingDateStartBetween(trainer.getId(), date, endDate);
      Date finalDate = date;
      List<Training> cycledTrainings = trainingRepository.findByTrainerIdAndCycledAndTrainingDateStartBefore(trainer.getId(), true, date)
        .stream().filter(t -> checkDay(t.getTrainingDateStart(), finalDate))
        .map(this::changeDate)
        .toList();

      trainings.addAll(cycledTrainings);
      Collections.sort(trainings);
      List<GetTrainingRsp> trainingRspList = new ArrayList<>();

      trainings.forEach(
        training -> {
          User user = userRepository.findById(training.getUserId())
            .orElseThrow(() -> new UsernameNotFoundException("No user with userId = " + training.getUserId()));
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
        return trainingRspList;
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
}
