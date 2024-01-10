package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.service.impl.ProgressApiImpl;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
@Slf4j
public class ProgressController {

    private final ProgressApiImpl progressApi;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addProgress(@PathVariable long userId, @RequestBody AddProgressReq req) {
        log.info("Add progress for user with userId = {}", userId);
        progressApi.addProgress(userId, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/entity/{userId}/{progressId}")
    public ResponseEntity<GetProgressListRsp> getProgressEntity(@PathVariable long userId, @PathVariable long progressId) {
        log.info("Get progress = {} for user with userId = {}", progressId, userId);
        return ResponseEntity.ok(progressApi.getProgressList(userId, progressId));
    }

    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> deleteProgress(@PathVariable long progressId) {
        log.info("Delete progress = {}", progressId);
        progressApi.deleteProgress(progressId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/entity/{progressEntityId}")
    public ResponseEntity<Void> deleteProgressEntity(@PathVariable long progressEntityId) {
        log.info("Delete progress = {}", progressEntityId);
        progressApi.deleteProgressEntity(progressEntityId);
        return ResponseEntity.ok().build();
    }
}
