package uptc.edu.swii.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uptc.edu.swii.login.application.dto.AuthRequest;
import uptc.edu.swii.login.application.dto.LoginRequest;
import uptc.edu.swii.login.application.dto.LoginResponse;
import uptc.edu.swii.login.application.usecase.AuthenticateLoginUseCase;
import uptc.edu.swii.login.application.usecase.CreateLoginUseCase;
import uptc.edu.swii.login.domain.Login;
import uptc.edu.swii.login.domain.repository.LoginRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logins")
public class LoginController {

    private final CreateLoginUseCase createLoginUseCase;
    private final AuthenticateLoginUseCase authenticateLoginUseCase;
    private final LoginRepository loginRepository;

    public LoginController(CreateLoginUseCase createLoginUseCase,
                           AuthenticateLoginUseCase authenticateLoginUseCase,
                           LoginRepository loginRepository) {
        this.createLoginUseCase = createLoginUseCase;
        this.authenticateLoginUseCase = authenticateLoginUseCase;
        this.loginRepository = loginRepository;
    }

    @GetMapping
    public ResponseEntity<List<Login>> getAllLogins() {
        return ResponseEntity.ok(loginRepository.findAll());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<LoginResponse> getLoginByCustomerId(@PathVariable String customerId) {
        return loginRepository.findByCustomerId(customerId)
                .map(login -> ResponseEntity.ok(new LoginResponse(
                        login.getCustomerId(),
                        null,
                        "Login found"
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        boolean isAuthenticated = authenticateLoginUseCase.execute(request);
        if (isAuthenticated) {
            return ResponseEntity.ok(Map.of("message", "Authenticated successfully"));
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
    }
}