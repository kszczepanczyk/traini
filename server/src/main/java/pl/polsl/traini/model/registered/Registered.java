package pl.polsl.traini.model.registered;


import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "Registered")
public class Registered {

    @Transient
    public static final String SEQUENCE_NAME = "registered_seq";

    Long id;
    Long userId;
    String email;
    String password;
    String type;
    boolean enabled;
}
