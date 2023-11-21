package pl.polsl.traini.service.client.progress.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.Date;

@Service
public class AddProgressService {

    private final ProgressRepository progressRepository;
    private final SeqGeneratorService generatorService;

    @Autowired
    public AddProgressService(ProgressRepository progressRepository, SeqGeneratorService generatorService) {
        this.progressRepository = progressRepository;
        this.generatorService = generatorService;
    }

    public Progress addProgress (Long userId, AddProgressReq req) {
        return progressRepository.save(createProgress(userId, req));
    }

    private Progress createProgress(Long userId, AddProgressReq req) {
        return Progress.builder()
                .id(generatorService.generateSeq(Progress.SEQUENCE_NAME))
                .userId(userId)
                .name(req.getName())
                .value(req.getValue())
                .unit(req.getUnit())
                .trend(req.isTrend())
                .createdAt(new Date())
                .build();
    }
}
