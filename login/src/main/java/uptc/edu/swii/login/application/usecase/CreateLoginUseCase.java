package uptc.edu.swii.login.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.login.domain.Login;
import uptc.edu.swii.login.domain.repository.LoginRepository;

@Service
@Transactional
public class CreateLoginUseCase {

    private final LoginRepository loginRepository;

    public CreateLoginUseCase(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login execute(String customerId, String username, String password) {
        if (loginRepository.existsByCustomerId(customerId)) {
            throw new IllegalStateException("Login already exists for customer: " + customerId);
        }
        Login login = Login.create(customerId, password);
        return loginRepository.save(login);
    }
}