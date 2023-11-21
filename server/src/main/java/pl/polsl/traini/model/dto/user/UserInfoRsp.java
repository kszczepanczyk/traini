package pl.polsl.traini.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRsp {
    String name;
    String surname;
    String photoB64;
    List<String> tags;
    String description;
    String phone;
    String email;
    String city;
    String gender;
    boolean trainer;
}
