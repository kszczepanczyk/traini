package pl.polsl.traini.service.client.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;

@Service
public class DeleteClientService {
    //delete progresu
    //delete treningu
    //delete user

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final ProgressRepository progressRepository;
    private final ProgressEntityRepository progressEntityRepository;

    @Autowired
    public DeleteClientService(UserRepository userRepository, TrainingRepository trainingRepository, ProgressRepository progressRepository, ProgressEntityRepository progressEntityRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.progressRepository = progressRepository;
        this.progressEntityRepository = progressEntityRepository;
    }

    public void deleteUser(Long userId) {
        deleteProgress(userId);

        deleteTrainings(userId);

        userRepository.delete(userRepository.findById(userId).orElseThrow());
    }

    private void deleteProgress(Long userId) {
        progressRepository.findAllByUserId(userId)
                .forEach(
                        progress -> {
                            deleteProgressEntity(progress.getId());
                            progressRepository.delete(progress);
                        }
                );
    }

    private void deleteProgressEntity(Long progressId) {
        progressEntityRepository.deleteAll(progressEntityRepository.findAllByProgressId(progressId));
    }

    private void deleteTrainings(Long userId) {
        trainingRepository.deleteAll(trainingRepository.findTrainingByUserId(userId));
    }
}
