package pl.polsl.traini.service.client.progress.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressEntity;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.model.progressEntity.ProgressEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class GetProgressListService {
    private final ProgressEntityRepository progressEntityRepository;
    private final ProgressRepository progressRepository;

    @Autowired
    public GetProgressListService(ProgressEntityRepository progressEntityRepository, ProgressRepository progressRepository) {
        this.progressEntityRepository = progressEntityRepository;
        this.progressRepository = progressRepository;
    }

    public GetProgressListRsp getProgressList(Long userId, long progressId) {
        Progress progress = progressRepository.findByUserIdAndId(userId, progressId)
                .orElseThrow();


        List<ProgressEntity> progresses = progressEntityRepository.findAllByProgressId(
                progress.getId()
        );

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
}
