package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.user.UserInfoRsp;
import pl.polsl.traini.model.dto.user.UserModifyReq;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.impl.TrainerApiImpl;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerApiImpl trainerApi;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<UserInfoRsp> getUserInfo(@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Get data about trainer = {}" , username);
        return ResponseEntity.ok(trainerApi.getTrainerInfo(username));
    }

    @PutMapping
    public ResponseEntity<String> setUserInfo(@RequestBody UserModifyReq req, @RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Update data about trainer = {}", username);
        User user = trainerApi.modify(req, username);
        if (user != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getLocations(@RequestHeader(name="Authorization") String token) {
      String username = jwtService.getUsername(token);
      log.info("Get locations for trainer = {}", username);
      return ResponseEntity.ok(trainerApi.getLocations(username));
    }
}
