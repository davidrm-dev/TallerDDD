package uptc.edu.swii.login.application.usecase;

import org.springframework.stereotype.Service;

import uptc.edu.swii.login.application.dto.AuthRequest;
import uptc.edu.swii.login.domain.Login;
import uptc.edu.swii.login.domain.repository.LoginRepository;
import uptc.edu.swii.login.domain.valueobject.LoginId;

@Service
public class AuthenticateLoginUseCase {

    private final LoginRepository loginRepository;

    public AuthenticateLoginUseCase(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean execute(AuthRequest request) {
        return loginRepository.findById(new LoginId(request.getCustomerId()))
                .map(login -> login.authenticate(request.getPassword()))
                .orElse(false);
    }
}