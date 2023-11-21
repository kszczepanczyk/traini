package pl.polsl.traini.model.seq;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Database_sequences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseSequence {
    @MongoId
    private String id;
    private long seq;
}
