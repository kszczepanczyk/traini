package pl.polsl.traini.service.training.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.LocationRepository;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.exceptions.NoLocationException;
import pl.polsl.traini.model.dto.training.add.AddTrainingReq;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.Date;

@Service
public class AddTrainingService {
    private final TrainingRepository trainingRepository;
    private final SeqGeneratorService generatorService;
    private final LocationRepository locationRepository;
    private final RegisterRepository registerRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public AddTrainingService(TrainingRepository trainingRepository, SeqGeneratorService generatorService, LocationRepository locationRepository, RegisterRepository registerRepository, TrainerRepository trainerRepository) {
        this.trainingRepository = trainingRepository;
        this.generatorService = generatorService;
        this.locationRepository = locationRepository;
        this.registerRepository = registerRepository;
        this.trainerRepository = trainerRepository;
    }

    public Training addTraining(String username, AddTrainingReq req) throws NoLocationException {
        return trainingRepository.save(createTraining(req, username));
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
                        locationRepository.findByName(req.getLocalization())
                        .orElseThrow( () -> new NoLocationException("No location for name = " + req.getName()))
                        .getId())
                .build();
    }

    private Long getTrainerId (String username) {
        return trainerRepository.findByRegisteredId(
                    registerRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("No register with email = " + username))
                        .getId())
                    .orElseThrow(() -> new UsernameNotFoundException("No trainer with email = " + username))
                    .getId();
    }
}
