package pl.polsl.traini.model.training;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Training")
public class Training {
    @Transient
    public static final String SEQUENCE_NAME = "TRAINING_SEQ";

    Long id;
    Long trainerId;
    Long userId;
    String name;
    Long locationId;
    Date trainingDateStart;
    Date trainingDateEnd;
    String description;
    Date createdAt;
    boolean cycled;
}
