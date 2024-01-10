package pl.polsl.traini.service.progress;

import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.progress.Progress;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {
    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public List<Progress> findListByUserId(Long userId) {
        return progressRepository.findAllByUserId(userId);
    }

    public Optional<Progress> findByUserIdAndName(Long userId, String name) {
        return progressRepository.findByUserIdAndName(userId, name);
    }

    public Progress findByUserIdAndId(Long userId, long progressId) {
        try {
            return progressRepository.findByUserIdAndId(userId, progressId)
                    .orElseThrow(() -> new NoTrainingException("No progress for userId = " + userId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Progress();
        }
    }

    public Progress findByProgressId(Long progressId) {
        try {
            return progressRepository.findById(progressId)
                    .orElseThrow(() -> new NoTrainingException("No progress for progressId = " + progressId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Progress();
        }
    }

    public void delete(Progress progress) {
        progressRepository.delete(progress);
    }

    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }
}
