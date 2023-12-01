package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.list.add.AddClientService;
import pl.polsl.traini.service.list.getList.GetClientListService;

import java.util.List;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
@Slf4j
public class ListController {

    private final JwtService jwtService;
    private final GetClientListService getClientListService;
    private final AddClientService addClientService;

    @GetMapping
    public ResponseEntity<List<User>> getClientList(@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Get clients for trainer = {}", username);
        return ResponseEntity.ok(getClientListService.getUserList(username));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addClient(@RequestBody AddClientReq req,@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Add client for trainer");
        User user = addClientService.addUser(req,username);
        return user != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
