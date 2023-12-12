package pl.polsl.traini.service.client.progress.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;
import pl.polsl.traini.database.ProgressRepository;
import pl.polsl.traini.model.progressEntity.ProgressEntity;

import java.util.stream.Collectors;

@Service
public class DeleteProgressService {
  private final ProgressRepository progressRepository;
  private final ProgressEntityRepository progressEntityRepository;

  @Autowired
  public DeleteProgressService(ProgressRepository progressRepository, ProgressEntityRepository progressEntityRepository) {
    this.progressRepository = progressRepository;
    this.progressEntityRepository = progressEntityRepository;
  }

  public void deleteProgress(Long progressId) {
    progressEntityRepository.deleteAllById(progressEntityRepository.findAllByProgressId(progressId).stream().map(ProgressEntity::getId).collect(Collectors.toList()));
    progressRepository.delete(progressRepository.findById(progressId).orElseThrow());
  }
}
