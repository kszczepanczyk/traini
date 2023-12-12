package pl.polsl.traini.model.dto.client.info;

import lombok.Builder;
import lombok.Data;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;

import java.util.List;

@Data
@Builder
public class GetClientInfoRsp {
    Long id;
    String name;
    String surname;
    String photo;
    String phone;
    String description;
    String city;
    String gender;
    List<GetTrainingRsp> trainingList;
    List<GetClientProgressRsp> progressList;
}
