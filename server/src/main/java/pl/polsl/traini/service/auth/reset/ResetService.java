package pl.polsl.traini.service.auth.reset;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.polsl.traini.model.dto.auth.reset.UserResetReq;

@Service
public class ResetService {

    //reset bedzie pozniej..
    public ResponseEntity<Void> reset (UserResetReq req) {
        return ResponseEntity.ok().build();
    }
}
