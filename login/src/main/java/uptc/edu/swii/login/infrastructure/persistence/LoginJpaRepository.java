package uptc.edu.swii.login.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginJpaRepository extends JpaRepository<LoginEntity, String> {
    Optional<LoginEntity> findByCustomerId(String customerId);
    boolean existsByCustomerId(String customerId);
}