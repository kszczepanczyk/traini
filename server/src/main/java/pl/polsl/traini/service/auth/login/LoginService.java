package pl.polsl.traini.service.auth.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TokenRepository;
import pl.polsl.traini.model.dto.auth.login.UserLoginReq;
import pl.polsl.traini.model.dto.auth.login.UserLoginRsp;
import pl.polsl.traini.model.token.Token;
import pl.polsl.traini.model.token.TokenType;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final RegisterRepository registerRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final SeqGeneratorService generatorService;

    public UserLoginRsp login(UserLoginReq req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        var user = registerRepository.findByEmail(req.getUsername())
                .map(registered -> new User(registered.getEmail(), registered.getPassword(), new ArrayList<>()))
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user.getUsername(), jwtToken);
        return UserLoginRsp.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(String user, String jwtToken) {
        var token = Token.builder()
                .id(generatorService.generateSeq(Token.SEQUENCE_NAME))
                .username(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findByUsernameOrderById(user.getUsername());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
