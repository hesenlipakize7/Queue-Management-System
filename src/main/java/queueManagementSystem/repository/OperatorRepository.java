package queueManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import queueManagementSystem.entity.Operator;

public interface OperatorRepository extends JpaRepository<Operator,Long> {
}
