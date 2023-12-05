package pl.polsl.traini.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModifyReq {
    long id;
    String name;
    String surname;
    String email;
    String description;
    String phone;
    String city;
    String gender;
    ArrayList<String> tags;
    ArrayList<String> locations;
    String photoB64;
}
