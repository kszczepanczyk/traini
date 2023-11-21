package pl.polsl.traini.model.location;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Location")
public class Location {

    @Transient
    public static final String SEQUENCE_NAME = "LOCATION_SEQ";

    @MongoId
    long id;
    String name;
}
