package queueManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import queueManagementSystem.entity.ServiceType;
import queueManagementSystem.entity.Ticket;
import queueManagementSystem.enums.TicketStatus;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findTopByServiceTypeOrderByIdDesc(ServiceType serviceType);
    Optional<Ticket> findFirstByServiceTypeAndStatusOrderByCreatedAtAsc(ServiceType serviceType, TicketStatus status);
    Optional<Ticket> findByIdAndStatus(Long id, TicketStatus status);
    List<Ticket> findAllByStatusOrderByCreatedAtAsc(TicketStatus status);
}

