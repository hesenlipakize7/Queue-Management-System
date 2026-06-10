package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.resppnse.StatisticsResponse;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.StatisticsService;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TicketRepository ticketRepository;

    @Override
    public StatisticsResponse getStatistics() {

        return StatisticsResponse.builder()
                .totalTickets(ticketRepository.count())
                .waitingTickets(ticketRepository.countByStatus(TicketStatus.WAITING))
                .servingTickets(ticketRepository.countByStatus(TicketStatus.SERVING))
                .finishedTickets(ticketRepository.countByStatus(TicketStatus.FINISHED))
                .cancelledTickets(ticketRepository.countByStatus(TicketStatus.CANCELLED))
                .build();

    }
}
