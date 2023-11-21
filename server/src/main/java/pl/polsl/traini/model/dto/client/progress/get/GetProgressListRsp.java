package pl.polsl.traini.model.dto.client.progress.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProgressListRsp {
    List<GetProgressEntity> progressList;
}
