package pl.polsl.traini.model.dto.client.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReq {
    String name;
    String surname;
    String description;
    String phone;
    String photo;
    String city;
    String gender;
}
