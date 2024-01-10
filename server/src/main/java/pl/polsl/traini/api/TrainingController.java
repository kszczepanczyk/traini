package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.training.add.AddTrainingReq;
import pl.polsl.traini.model.dto.training.delete.DeleteTrainingReq;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.dto.training.update.UpdateTrainingReq;
import pl.polsl.traini.service.impl.TrainingApiImpl;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final TrainingApiImpl trainingApi;
    private final JwtService jwtService;

    @GetMapping("/{trainingId}")
    public ResponseEntity<GetTrainingRsp> getTraining(@PathVariable Long trainingId) {
        log.info("Get training with trainingId = {}", trainingId);
        return ResponseEntity.ok(trainingApi.getTraining(trainingId));
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<Void> updateTraining(@PathVariable Long trainingId, @RequestBody UpdateTrainingReq req) {
        log.info("Update training with trainingId = {}", trainingId);
        return trainingApi.updateTraining(trainingId, req) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestHeader(name="Authorization") String token, @RequestBody AddTrainingReq req) {
        String username = jwtService.getUsername(token);
        log.info("Add training for username = {}", username);
        return trainingApi.addTraining(username, req) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTraining(@RequestBody DeleteTrainingReq req) {
        log.info("Delete training with trainingId = {}", req.getTrainingId());
        trainingApi.deleteTraining(req.getTrainingId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{date}")
    public ResponseEntity<List<GetTrainingRsp>> getTrainings(@RequestHeader(name="Authorization") String token,
                                                             @PathVariable @DateTimeFormat(pattern="dd-MM-yyyy") Date date) {
      String username = jwtService.getUsername(token);
      log.info("Loading trainings for username = {} for date = {}", username, date);
      return ResponseEntity.ok(trainingApi.getTrainingList(jwtService.getUsername(token), date));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<GetTrainingRsp>> getSchedule(@RequestHeader(name="Authorization") String token) {
      String username = jwtService.getUsername(token);
      log.info("Get all trainings for username = {}", username);
      return ResponseEntity.ok(trainingApi.getSchedule(username));
    }
}
