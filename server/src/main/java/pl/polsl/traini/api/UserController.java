package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.exceptions.UserNotModifiedException;
import pl.polsl.traini.model.dto.user.UserInfoRsp;
import pl.polsl.traini.model.dto.user.UserModifyReq;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.user.info.UserInfoService;
import pl.polsl.traini.service.user.modify.UserModifyService;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserModifyService userModifyService;
    private final UserInfoService userInfoService;
    private final JwtService jwtService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoRsp> getUserInfo(@RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Get data about trainer = {}" , username);
        return ResponseEntity.ok(userInfoService.getUserInfo(username));
    }

    @PutMapping("/modify")
    public ResponseEntity<String> setUserInfo(@RequestBody UserModifyReq req, @RequestHeader(name="Authorization") String token) {
        String username = jwtService.getUsername(token);
        log.info("Update data about trainer = {}", username);
        try {
            User user = userModifyService.modifyUser(req);
            if (user != null) {
                return ResponseEntity.ok().build();
            }
        } catch (UserNotModifiedException e) {
            log.warn("Imo bad id!");
        }
        return ResponseEntity.badRequest().build();
    }
}
