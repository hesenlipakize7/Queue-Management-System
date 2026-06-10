package queueManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import queueManagementSystem.entity.Operator;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator,Long> {

    Optional<Operator> findByUsernameAndPassword(String username, String password);
}
