package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.home.avatar.HomeGetAvatarRsp;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.service.home.avatar.HomeAvatarService;
import pl.polsl.traini.service.home.trainings.HomeTrainingService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final JwtService jwtService;
    private final HomeTrainingService homeTrainingService;
    private final HomeAvatarService homeAvatarService;

    @GetMapping("/trainings/{date}")
    public ResponseEntity<List<Training>> getTrainings(@RequestHeader(name="Authorization") String token,
                                                       @PathVariable @DateTimeFormat(pattern="dd-MM-yyyy") Date date) {
        String username = jwtService.getUsername(token);
        log.info("Loading trainings for username = {} for date = {}", username, date);
        return ResponseEntity.ok(homeTrainingService.getTrainings(jwtService.getUsername(token), date));
    }

    @GetMapping("/avatar")
    public ResponseEntity<HomeGetAvatarRsp> getAvatar(@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Loading avatar for " + username);
        return ResponseEntity.ok(homeAvatarService.getAvatar(username));
    }

}
