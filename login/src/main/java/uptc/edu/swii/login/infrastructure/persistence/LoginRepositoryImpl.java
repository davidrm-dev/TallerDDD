package uptc.edu.swii.login.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import uptc.edu.swii.login.domain.Login;
import uptc.edu.swii.login.domain.repository.LoginRepository;
import uptc.edu.swii.login.domain.valueobject.LoginId;
import uptc.edu.swii.login.domain.valueobject.Password;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private final LoginJpaRepository jpaRepository;

    public LoginRepositoryImpl(LoginJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Login save(Login login) {
        LoginEntity entity = new LoginEntity(
                login.getCustomerId(),
                login.getPassword().getValue()
        );
        jpaRepository.save(entity);
        return login;
    }

    @Override
    public Optional<Login> findById(LoginId id) {
        return jpaRepository.findById(id.getCustomerId())
                .map(this::toDomain);
    }

    @Override
    public Optional<Login> findByCustomerId(String customerId) {
        return jpaRepository.findByCustomerId(customerId)
                .map(this::toDomain);
    }

    @Override
    public List<Login> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(LoginId id) {
        jpaRepository.deleteById(id.getCustomerId());
    }

    @Override
    public boolean existsByCustomerId(String customerId) {
        return jpaRepository.existsByCustomerId(customerId);
    }

    private Login toDomain(LoginEntity entity) {
        return Login.create(
                entity.getCustomerId(),
                entity.getPassword()
        );
    }
}