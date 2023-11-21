package pl.polsl.traini.model.dto.client.info;

import lombok.Builder;
import lombok.Data;
import pl.polsl.traini.model.progress.Progress;
import pl.polsl.traini.model.training.Training;

import java.util.Date;
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
    List<Training> trainingList;
    List<Progress> progressList;
}
