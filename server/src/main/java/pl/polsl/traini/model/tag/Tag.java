package pl.polsl.traini.model.tag;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Tag")
public class Tag {

    @Transient
    public static final String SEQUENCE_NAME = "TAG_SEQ";

    long id;
    String name;

}
