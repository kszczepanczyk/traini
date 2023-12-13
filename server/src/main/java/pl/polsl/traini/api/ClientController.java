package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.model.dto.client.delete.DeleteClientReq;
import pl.polsl.traini.model.dto.client.progress.add.AddProgressReq;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.dto.client.progress.get.GetProgressListRsp;
import pl.polsl.traini.model.dto.client.update.UpdateUserReq;
import pl.polsl.traini.service.client.delete.DeleteClientService;
import pl.polsl.traini.service.client.getInfo.GetClientInfoService;
import pl.polsl.traini.service.client.progress.add.AddProgressService;
import pl.polsl.traini.service.client.progress.delete.DeleteProgressEntityService;
import pl.polsl.traini.service.client.progress.delete.DeleteProgressService;
import pl.polsl.traini.service.client.progress.get.GetProgressListService;
import pl.polsl.traini.service.client.update.UpdateUserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final GetClientInfoService getClientInfoService;
    private final UpdateUserService updateUserService;
    private final DeleteClientService deleteClientService;
    private final AddProgressService addProgressService;
    private final GetProgressListService getProgressListService;
    private final DeleteProgressService deleteProgressService;
    private final DeleteProgressEntityService deleteProgressEntityService;

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
        addProgressService.addProgress(userId, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/progress/{progressId}")
    public ResponseEntity<GetProgressListRsp> getProgressEntity(@PathVariable long userId, @PathVariable long progressId) {
        log.info("Get progress = {} for user with userId = {}", progressId, userId);
        return ResponseEntity.ok(getProgressListService.getProgressList(userId, progressId));
    }

    @DeleteMapping("/progress/{progressId}")
    public ResponseEntity<GetProgressListRsp> deleteProgress(@PathVariable long progressId) {
      log.info("Delete progress = {}", progressId);
      deleteProgressService.deleteProgress(progressId);
      return ResponseEntity.ok().build();
    }

    @DeleteMapping("/progressEntity/{progressEntityId}")
    public ResponseEntity<GetProgressListRsp> deleteProgressEntity(@PathVariable long progressEntityId) {
      log.info("Delete progress = {}", progressEntityId);
      deleteProgressEntityService.deleteProgressEntity(progressEntityId);
      return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClient(@RequestBody DeleteClientReq req) {
        log.info("Delete client with userId = {}", req.getUserId());
        deleteClientService.deleteUser(req.getUserId());
        return ResponseEntity.ok().build();
    }
}
