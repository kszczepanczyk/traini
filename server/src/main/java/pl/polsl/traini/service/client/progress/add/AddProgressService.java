package pl.polsl.traini.service.client.progress.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.model.progressEntity.ProgressEntity;
import pl.polsl.traini.service.seq.SeqGeneratorService;

@Service
public class AddProgressService {

    private final ProgressRepository progressRepository;
    private final ProgressEntityRepository progressEntityRepository;
    private final SeqGeneratorService generatorService;

    @Autowired
    public AddProgressService(ProgressRepository progressRepository, ProgressEntityRepository progressEntityRepository, SeqGeneratorService generatorService) {
        this.progressRepository = progressRepository;
        this.progressEntityRepository = progressEntityRepository;
        this.generatorService = generatorService;
    }

    public void addProgress (Long userId, AddProgressReq req) {
        Progress progress = progressRepository.findByUserIdAndName(userId, req.getName())
                .map(p -> updateValues(p, req))
                .orElseGet(() -> createProgress(userId, req));

        progressEntityRepository.save(createProgressEntity(progress.getId(), req));
    }

    private Progress createProgress(Long userId, AddProgressReq req) {
        return progressRepository.save(Progress.builder()
                .id(generatorService.generateSeq(Progress.SEQUENCE_NAME))
                .userId(userId)
                .name(req.getName())
                .value(req.getValue())
                .unit(req.getUnit())
                .trend(req.isTrend())
                .build());
    }

    private ProgressEntity createProgressEntity(Long progressId, AddProgressReq req) {
        return ProgressEntity.builder()
                .id(generatorService.generateSeq(ProgressEntity.SEQUENCE_NAME))
                .progressId(progressId)
                .value(req.getValue())
                .createdAt(req.getCreatedAt())
                .build();
    }

    private Progress updateValues(Progress progress, AddProgressReq req) {
        progress.setValue(req.getValue());
        progress.setTrend(req.isTrend());
        return progressRepository.save(progress);
    }
}
