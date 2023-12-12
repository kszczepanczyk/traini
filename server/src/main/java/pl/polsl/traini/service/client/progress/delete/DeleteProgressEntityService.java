package pl.polsl.traini.service.client.progress.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.ProgressEntityRepository;

@Service
public class DeleteProgressEntityService {
  private final ProgressEntityRepository progressEntityRepository;

  @Autowired
  public DeleteProgressEntityService(ProgressEntityRepository progressEntityRepository) {
    this.progressEntityRepository = progressEntityRepository;
  }

  public void deleteProgressEntity(Long progressEntityId) {
    progressEntityRepository.deleteById(progressEntityId);
  }
}
