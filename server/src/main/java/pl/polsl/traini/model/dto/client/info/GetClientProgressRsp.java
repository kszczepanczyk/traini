package pl.polsl.traini.model.dto.client.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClientProgressRsp {
    Long progressId;
    String progressName;
    double value;
    String unit;
}
