package pl.polsl.traini.service.progress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.model.progressEntity.ProgressEntity;

import java.util.List;

@Service
public class ProgressEntityService {
    private final ProgressEntityRepository progressEntityRepository;

    @Autowired
    public ProgressEntityService(ProgressEntityRepository progressEntityRepository) {
        this.progressEntityRepository = progressEntityRepository;
    }

    public void deleteAll(Long progressId) {
        progressEntityRepository.deleteAll(progressEntityRepository.findAllByProgressId(progressId));
    }

    public void delete(Long progressEntityId) {
        progressEntityRepository.deleteById(progressEntityId);
    }

    public ProgressEntity save(ProgressEntity progressEntity) {
        return progressEntityRepository.save(progressEntity);
    }

    public List<ProgressEntity> findListByProgressId(Long progressId) {
        return progressEntityRepository.findAllByProgressId(progressId);
    }
}
