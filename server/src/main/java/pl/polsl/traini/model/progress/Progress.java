package pl.polsl.traini.model.progress;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Progress")
public class Progress {

    @Transient
    public static final String SEQUENCE_NAME = "PROGRESS_SEQ";

    Long id;
    Long userId;
    String name;
    String value;
    String unit;
    boolean trend;
    Date createdAt;
}
