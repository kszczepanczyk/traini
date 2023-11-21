package pl.polsl.traini.model.progressEntity;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Progress_entity")
public class ProgressEntity {
    @Transient
    public static final String SEQUENCE_NAME = "PROGRESS_ENTITY";

    long id;
    long progressId;
    double value;
    String unit;

}
