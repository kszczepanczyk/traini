package pl.polsl.traini.service.client.progress.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressEntity;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.model.progress.Progress;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetProgressListService {
    private final ProgressRepository progressRepository;

    @Autowired
    public GetProgressListService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public GetProgressListRsp getProgressList(Long userId, String progressName) {
        List<Progress> progresses = progressRepository.findAllByUserIdAndNameOrCreatedAt(userId, progressName);

        List<GetProgressEntity> rspList = new ArrayList<>();
        progresses.forEach(
                progress -> rspList.add(GetProgressEntity.builder()
                        .value(progress.getValue())
                        .unit(progress.getUnit())
                        .createdAt(progress.getCreatedAt())
                        .build())
        );

        return GetProgressListRsp.builder()
                .progressList(rspList).build();
    }
}
