package pl.polsl.traini.model.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddClientReq {
    String name;
    String surname;
    String description;
    String gender;
    String phone;
    String city;
}
