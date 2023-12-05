package pl.polsl.traini.model.dto.training.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.traini.model.training.TrainingDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTrainingRsp {
  Long id;
    String name;
    String surname;
    TrainingDate trainingDate;
    String description;
    String location;
    String trainingName;
    boolean cycled;
}
