package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.resppnse.OperatorResponse;
import queueManagementSystem.entity.Operator;
import queueManagementSystem.entity.Ticket;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.exception.NoWaitingTicketException;
import queueManagementSystem.exception.OperatorNotFoundException;
import queueManagementSystem.exception.TicketNotFoundException;
import queueManagementSystem.repository.OperatorRepository;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.OperatorService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final TicketRepository ticketRepository;
    private final OperatorRepository operatorRepository;


    @Override
    public OperatorResponse nextTicket(Long operatorId) {

        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new OperatorNotFoundException("Operator not found"));

        Ticket ticket = ticketRepository.findFirstByServiceTypeAndStatusOrderByCreatedAtAsc(
                        operator.getServiceType(), TicketStatus.WAITING)
                .orElseThrow(() -> new NoWaitingTicketException("No waiting ticket"));

        ticket.setStatus(TicketStatus.SERVING);
        ticket.setCalledAt(LocalDateTime.now());
        ticket.setOperator(operator);

        ticketRepository.save(ticket);

        return OperatorResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .deskNumber(operator.getDeskNumber())
                .build();
    }

    @Override
    public OperatorResponse finishTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndStatus(ticketId, TicketStatus.SERVING)
                .orElseThrow(() -> new TicketNotFoundException("Active ticket not found"));

        ticket.setStatus(TicketStatus.FINISHED);
        ticket.setFinishedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return OperatorResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .deskNumber(ticket.getOperator() != null
                        ? ticket.getOperator().getDeskNumber()
                        : null)
                .build();
    }

}
