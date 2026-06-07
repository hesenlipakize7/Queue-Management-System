package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.request.CreateTicketRequest;
import queueManagementSystem.dto.resppnse.TicketResponse;
import queueManagementSystem.entity.ServiceType;
import queueManagementSystem.entity.Ticket;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.exception.ServiceTypeNotFoundException;
import queueManagementSystem.repository.ServiceTypeRepository;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public TicketResponse createTicket(CreateTicketRequest createTicketRequest) {
        ServiceType serviceType = serviceTypeRepository.findById(createTicketRequest.getServiceTypeId())
                .orElseThrow(()->new ServiceTypeNotFoundException("Service type not found"));
        String ticketNumber = generateTicketNumber(serviceType);

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .serviceType(serviceType)
                .status(TicketStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        ticketRepository.save(ticket);
        ticketRepository.save(ticket);

        return TicketResponse.builder()
                .id((long) ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .serviceName(ticket.getServiceType().getName())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .build();
    }

    @Override
    public List<TicketResponse> getWaitingTickets() {
        return ticketRepository
                .findAllByStatusOrderByCreatedAtAsc(TicketStatus.WAITING)
                .stream()
                .map(ticket -> TicketResponse.builder()
                        .id((long) ticket.getId())
                        .ticketNumber(ticket.getTicketNumber())
                        .serviceName(ticket.getServiceType().getName())
                        .status(ticket.getStatus())
                        .createdAt(ticket.getCreatedAt())
                        .build())
                .toList();
    }

    public String generateTicketNumber(ServiceType serviceType) {
        Optional<Ticket> lastTicket  = ticketRepository.findTopByServiceTypeOrderByIdDesc(serviceType);
        if(lastTicket.isEmpty()) {
            return serviceType.getPrefix() + "001";
        }
        String lastNumber = lastTicket.get().getTicketNumber();
        int number = Integer.parseInt(lastNumber.substring(1));
        number++;
        return serviceType.getPrefix() +String.format("%03d", number);

    }
}
