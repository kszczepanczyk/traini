package pl.polsl.traini.model.dto.client.progress.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProgressReq {
    Long userId;
    String name;
    String value;
    String unit;
    boolean trend;
}
