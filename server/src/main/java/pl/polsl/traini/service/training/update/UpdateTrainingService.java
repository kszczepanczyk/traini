package pl.polsl.traini.service.training.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.LocationRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.exceptions.NoLocationException;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.dto.training.update.UpdateTrainingReq;
import pl.polsl.traini.model.training.Training;

@Service
public class UpdateTrainingService {

    private final TrainingRepository trainingRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UpdateTrainingService(TrainingRepository trainingRepository, LocationRepository locationRepository) {
        this.trainingRepository = trainingRepository;
        this.locationRepository = locationRepository;
    }

    public Training updateTraining(Long trainingId, UpdateTrainingReq req) throws NoTrainingException, NoLocationException {
        return trainingRepository.save(findAndCreateTraining(trainingId, req));
    }

    private Training findAndCreateTraining(Long trainingId, UpdateTrainingReq req) throws NoTrainingException, NoLocationException {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow( () -> new NoTrainingException("No training for trainingId = " + trainingId));

        training.setName(req.getName());
        training.setTrainingDateStart(req.getTrainingDate().getStart());
        training.setTrainingDateEnd(req.getTrainingDate().getEnd());
        training.setUserId(req.getUserId());
        training.setDescription(req.getDescription());
        training.setLocationId(
                locationRepository.findByName(req.getName())
                        .orElseThrow( () -> new NoLocationException("No location for name = " + req.getName()))
                        .getId());
        training.setCycled(req.isCycled());

        return training;
    }
}
