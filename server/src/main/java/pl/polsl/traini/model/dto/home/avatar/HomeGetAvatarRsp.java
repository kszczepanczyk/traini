package pl.polsl.traini.model.dto.home.avatar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeGetAvatarRsp {
  String name;
  String photo;
}
