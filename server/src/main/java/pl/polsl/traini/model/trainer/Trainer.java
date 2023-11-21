package pl.polsl.traini.model.trainer;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "Trainer")
public class Trainer {

    @Transient
    public static final String SEQUENCE_NAME = "TRAINER_SEQ";

    Long id;
    Long registeredId;
    ArrayList<Long> tags;
    ArrayList<Long> locations;
}
