package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.client.delete.DeleteClientReq;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.dto.client.update.UpdateUserReq;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.impl.ClientApiImpl;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientApiImpl clientApi;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Void> addClient(@RequestBody AddClientReq req, @RequestHeader(name = "Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Add client for trainer");
        User user = clientApi.addClient(req, username);
        return user != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetClientInfoRsp> getClient(@PathVariable long userId) {
        log.info("Get user info for userId = {}", userId);
        return ResponseEntity.ok(clientApi.getClient(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateClient(@PathVariable long userId, @RequestBody UpdateUserReq req) {
        log.info("Update user info for userId = {}", userId);
        return clientApi.updateClient(req, userId) != null ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClient(@RequestBody DeleteClientReq req) {
        log.info("Delete client with userId = {}", req.getUserId());
        clientApi.deleteClient(req.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getClientList(@RequestHeader(name = "Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Get clients for trainer = {}", username);
        return ResponseEntity.ok(clientApi.getClientList(username));
    }


}
