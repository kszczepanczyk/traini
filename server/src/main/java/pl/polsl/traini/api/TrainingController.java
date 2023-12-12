package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.exceptions.NoLocationException;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.dto.training.add.AddTrainingReq;
import pl.polsl.traini.model.dto.training.delete.DeleteTrainingReq;
import pl.polsl.traini.model.dto.training.get.GetTrainingRsp;
import pl.polsl.traini.model.dto.training.update.UpdateTrainingReq;
import pl.polsl.traini.service.training.add.AddTrainingService;
import pl.polsl.traini.service.training.delete.DeleteTrainingService;
import pl.polsl.traini.service.training.get.GetTrainingService;
import pl.polsl.traini.service.training.update.UpdateTrainingService;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final GetTrainingService getTrainingService;
    private final UpdateTrainingService updateTrainingService;
    private final AddTrainingService addTrainingService;
    private final DeleteTrainingService deleteTrainingService;
    private final JwtService jwtService;

    @GetMapping("/{trainingId}")
    public ResponseEntity<GetTrainingRsp> getTraining(@PathVariable Long trainingId) throws NoTrainingException {
        log.info("Get training with trainingId = {}", trainingId);
        return ResponseEntity.ok(getTrainingService.getTraining(trainingId));
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<Void> updateTraining(@PathVariable Long trainingId, @RequestBody UpdateTrainingReq req)
            throws NoTrainingException, NoLocationException {
        log.info("Update training with trainingId = {}", trainingId);
        return updateTrainingService.updateTraining(trainingId, req) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestHeader(name="Authorization") String token, @RequestBody AddTrainingReq req)
            throws NoLocationException {
        String username = jwtService.getUsername(token);
        log.info("Add training for username = {}", username);
        return addTrainingService.addTraining(username, req) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTraining(@RequestBody DeleteTrainingReq req) {
        log.info("Delete training with trainingId = {}", req.getTrainingId());
        deleteTrainingService.deleteTraining(req.getTrainingId());
        return ResponseEntity.ok().build();
    }
}
