package aftermealstudio.findteammate.controller;

import aftermealstudio.findteammate.model.dto.member.Create;
import aftermealstudio.findteammate.model.dto.member.Login;
import aftermealstudio.findteammate.model.dto.member.Response;
import aftermealstudio.findteammate.model.dto.token.Token;
import aftermealstudio.findteammate.service.AuthenticationService;
import aftermealstudio.findteammate.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailVerificationService verificationService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Login login) {
        Token token = authenticationService.login(login);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody Create create) {
        Response response = authenticationService.register(create);
        verificationService.sendVerification(response.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ModelAndView verifyEmail(@RequestParam String email, @RequestParam String code) {
        verificationService.confirm(email, code);
        ModelAndView modelAndView = new ModelAndView("emailVerification");
        return modelAndView;
    }

    @GetMapping("/verify/retry")
    public ResponseEntity retryVerify(@RequestParam String email) {
        verificationService.sendVerification(email);
        return ResponseEntity.ok().build();
    }
}
