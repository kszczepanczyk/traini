package pl.polsl.traini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressEntity;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.model.progressEntity.ProgressEntity;
import pl.polsl.traini.service.progress.ProgressEntityService;
import pl.polsl.traini.service.progress.ProgressService;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressApiImpl {
    private final ProgressService progressService;
    private final ProgressEntityService progressEntityService;
    private final SeqGeneratorService generatorService;

    @Autowired
    public ProgressApiImpl(ProgressService progressService, ProgressEntityService progressEntityService, SeqGeneratorService generatorService) {
        this.progressService = progressService;
        this.progressEntityService = progressEntityService;
        this.generatorService = generatorService;
    }

    public void addProgress (Long userId, AddProgressReq req) {
        Progress progress = progressService.findByUserIdAndName(userId, req.getName())
                .map(p -> updateValues(p, req))
                .orElseGet(() -> createProgress(userId, req));

        progressEntityService.save(createProgressEntity(progress.getId(), req));
    }

    public void deleteProgress(Long progressId) {
        progressEntityService.deleteAll(progressId);
        progressService.delete(progressService.findByProgressId(progressId));
    }

    public void deleteProgressEntity(Long progressEntityId) {
        progressEntityService.delete(progressEntityId);
    }

    public GetProgressListRsp getProgressList(Long userId, long progressId) {
        Progress progress = progressService.findByUserIdAndId(userId, progressId);
        List<ProgressEntity> progresses = progressEntityService.findListByProgressId(progress.getId());

        List<GetProgressEntity> rspList = new ArrayList<>();
        progresses.forEach(
                p -> rspList.add(GetProgressEntity.builder()
                        .value(p.getValue())
                        .progressEntityId(p.getId())
                        .createdAt(p.getCreatedAt())
                        .build())
        );
        rspList.sort(Comparator.comparing(GetProgressEntity::getCreatedAt));
        return GetProgressListRsp.builder()
                .progressList(rspList)
                .trend(progress.isTrend())
                .unit(progress.getUnit())
                .progressName(progress.getName())
                .build();
    }



    private Progress createProgress(Long userId, AddProgressReq req) {
        return progressService.save(Progress.builder()
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
        return progressService.save(progress);
    }
}
