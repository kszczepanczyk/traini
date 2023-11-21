package pl.polsl.traini.model.dto.training.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.traini.model.training.TrainingDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTrainingReq {
    String name;
    TrainingDate trainingDate;
    Long userId;
    String description;
    String localization;
    boolean cycled;
}
