package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.training.Training;
import pl.polsl.traini.service.schedule.GetScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final JwtService jwtService;
    private final GetScheduleService getScheduleService;

    @GetMapping()
    public ResponseEntity<List<Training>> getSchedule(@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Get all trainings for username = {}", username);
        return ResponseEntity.ok(getScheduleService.getSchedule(username));
    }
}
