package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.member.Create;
import aftermealstudio.findteammate.model.dto.member.Login;
import aftermealstudio.findteammate.model.dto.member.Response;
import aftermealstudio.findteammate.model.dto.token.Token;
import aftermealstudio.findteammate.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Login login) {
        Token token = authenticationService.login(login);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody Create create) {
        Response response = authenticationService.register(create);
        return ResponseEntity.ok(response);
    }
}
