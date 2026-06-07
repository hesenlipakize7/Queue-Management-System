package queueManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import queueManagementSystem.enums.TicketStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ticketNumber;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime calledAt;

    private LocalDateTime finishedAt;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;
}
