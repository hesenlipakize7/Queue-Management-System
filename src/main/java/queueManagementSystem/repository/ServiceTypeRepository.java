package queueManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import queueManagementSystem.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType,Long> {
}
