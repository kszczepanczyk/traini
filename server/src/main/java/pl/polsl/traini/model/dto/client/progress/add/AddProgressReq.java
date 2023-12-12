package pl.polsl.traini.model.dto.client.progress.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProgressReq {
    String name;
    double value;
    String unit;
    boolean trend;
    Date createdAt;
}
