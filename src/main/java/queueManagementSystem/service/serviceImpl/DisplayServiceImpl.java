package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.resppnse.DisplayResponse;
import queueManagementSystem.entity.Operator;
import queueManagementSystem.entity.Ticket;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.repository.OperatorRepository;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.DisplayService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisplayServiceImpl implements DisplayService {

    private final TicketRepository ticketRepository;

    private final OperatorRepository operatorRepository;

    @Override
    public List<DisplayResponse> getDisplay() {

        List<Operator> operators = operatorRepository.findAll();

        List<DisplayResponse> response = new ArrayList<>();

        for (Operator operator : operators) {

            List<Ticket> activeTickets =
                    ticketRepository.findAllByOperatorIdAndStatus(
                            operator.getId(),
                            TicketStatus.SERVING
                    );

            String currentTicket = "-";

            if (!activeTickets.isEmpty()) {
                currentTicket =
                        activeTickets.getFirst().getTicketNumber();
            }

            response.add(
                    DisplayResponse.builder()
                            .operatorId(operator.getId())
                            .deskNumber(operator.getDeskNumber())
                            .ticketNumber(currentTicket)
                            .build()
            );
        }
        return response;
    }
}
