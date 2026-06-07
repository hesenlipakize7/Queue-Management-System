package queueManagementSystem.dto.resppnse;

import lombok.*;
import queueManagementSystem.enums.TicketStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {

    private Long id;

    private String ticketNumber;

    private String serviceName;

    private TicketStatus status;

    private LocalDateTime createdAt;}
