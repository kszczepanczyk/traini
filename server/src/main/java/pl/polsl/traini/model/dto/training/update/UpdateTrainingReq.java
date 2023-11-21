package pl.polsl.traini.model.dto.training.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.traini.model.training.TrainingDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainingReq {
    String name;
    TrainingDate trainingDate;
    Long userId;
    String description;
    String localization;
    boolean cycled;
}
