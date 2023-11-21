package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.model.dto.client.update.UpdateUserReq;
import pl.polsl.traini.service.client.getInfo.GetClientInfoService;
import pl.polsl.traini.service.client.progress.add.AddProgressService;
import pl.polsl.traini.service.client.progress.get.GetProgressListService;
import pl.polsl.traini.service.client.update.UpdateUserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final GetClientInfoService getClientInfoService;
    private final UpdateUserService updateUserService;
    private final AddProgressService addProgressService;
    private final GetProgressListService getProgressListService;

    @GetMapping("/{userId}")
    public ResponseEntity<GetClientInfoRsp> getClient(@PathVariable long userId) {
        log.info("Get user info for userId = {}", userId);
        return ResponseEntity.ok(getClientInfoService.getClientInfo(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable long userId, @RequestBody UpdateUserReq req) {
        log.info("Update user info for userId = {}", userId);
        return updateUserService.updateUser(req, userId) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{userId}/progress")
    public ResponseEntity<Void> addProgress(@PathVariable long userId, @RequestBody AddProgressReq req) {
        log.info("Add progress for user with userId = {}", userId);
        return addProgressService.addProgress(userId, req) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/progress/{progressName}")
    public ResponseEntity<GetProgressListRsp> getProgressEntity(@PathVariable long userId, @PathVariable String progressName) {
        log.info("Get progress = {} for user with userId = {}", progressName, userId);
        return ResponseEntity.ok(getProgressListService.getProgressList(userId, progressName));
    }
}
