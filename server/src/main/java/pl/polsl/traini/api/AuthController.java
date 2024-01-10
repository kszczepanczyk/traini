package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.traini.model.dto.auth.login.UserLoginReq;
import pl.polsl.traini.model.dto.auth.login.UserLoginRsp;
import pl.polsl.traini.model.dto.auth.register.UserRegisterReq;
import pl.polsl.traini.service.auth.login.LoginService;
import pl.polsl.traini.service.auth.register.RegisterService;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterReq req) {
        return ResponseEntity.ok(registerService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRsp> authenticate(@RequestBody UserLoginReq req) {
        return ResponseEntity.ok(loginService.login(req));
    }
}
