package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.model.dto.auth.login.UserLoginReq;
import pl.polsl.traini.model.dto.auth.login.UserLoginRsp;
import pl.polsl.traini.model.dto.auth.register.UserRegisterReq;
import pl.polsl.traini.model.dto.auth.reset.UserResetReq;
import pl.polsl.traini.service.auth.login.LoginService;
import pl.polsl.traini.service.auth.register.RegisterService;
import pl.polsl.traini.service.auth.reset.ResetService;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final LoginService loginService;
    private final ResetService resetService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterReq req) {
        return ResponseEntity.ok(registerService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRsp> authenticate(@RequestBody UserLoginReq req) {
        return ResponseEntity.ok(loginService.login(req));
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset(@RequestBody UserResetReq req) {
        return resetService.reset(req);
    }
}
