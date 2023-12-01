package pl.polsl.traini.model.user;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "User")
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_seq";

    long id;
    long trainerId;
    String name;
    String surname;
    String phone;
    String description;
    String city;
    String gender;
    String photo;
    Date createdAt;
    Date modifyAt;
}
