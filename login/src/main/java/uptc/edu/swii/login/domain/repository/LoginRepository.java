package uptc.edu.swii.login.domain.repository;

import java.util.List;
import java.util.Optional;

import uptc.edu.swii.login.domain.Login;
import uptc.edu.swii.login.domain.valueobject.LoginId;

public interface LoginRepository {
    Login save(Login login);
    Optional<Login> findById(LoginId id);
    Optional<Login> findByCustomerId(String customerId);
    List<Login> findAll();
    void deleteById(LoginId id);
    boolean existsByCustomerId(String customerId);
}