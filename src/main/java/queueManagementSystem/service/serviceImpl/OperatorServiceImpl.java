package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.request.CreateOperatorRequest;
import queueManagementSystem.dto.request.LoginRequest;
import queueManagementSystem.dto.request.TransferTicketRequest;
import queueManagementSystem.dto.resppnse.CreateOperatorResponse;
import queueManagementSystem.dto.resppnse.LoginResponse;
import queueManagementSystem.dto.resppnse.OperatorResponse;
import queueManagementSystem.dto.resppnse.OperatorStatisticsResponse;
import queueManagementSystem.entity.Operator;
import queueManagementSystem.entity.ServiceType;
import queueManagementSystem.entity.Ticket;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.exception.NoWaitingTicketException;
import queueManagementSystem.exception.OperatorNotFoundException;
import queueManagementSystem.exception.ServiceTypeNotFoundException;
import queueManagementSystem.exception.TicketNotFoundException;
import queueManagementSystem.repository.OperatorRepository;
import queueManagementSystem.repository.ServiceTypeRepository;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.OperatorService;
import queueManagementSystem.util.JwtService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final TicketRepository ticketRepository;
    private final OperatorRepository operatorRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public OperatorResponse nextTicket(Long operatorId) {

        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() ->
                        new RuntimeException("Operator not found"));

        List<Ticket> activeTickets =
                ticketRepository.findAllByOperatorIdAndStatus(
                        operatorId,
                        TicketStatus.SERVING
                );

        System.out.println("Active ticket count = " + activeTickets.size());

        for (Ticket t : activeTickets) {
            System.out.println(
                    t.getId() + " | " +
                    t.getTicketNumber() + " | " +
                    t.getStatus() + " | " +
                    t.getOperator().getId()
            );
        }

        if (!activeTickets.isEmpty()) {
            throw new RuntimeException(
                    "Operator already has an active ticket");
        }

        Ticket ticket = ticketRepository
                        .findFirstByServiceTypeAndStatusOrderByCreatedAtAsc(
                                operator.getServiceType(),
                                TicketStatus.WAITING)
                        .orElseThrow(() ->
                                new RuntimeException("No waiting ticket"));

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

    @Override
    public OperatorStatisticsResponse getOperatorStatistics(Long operatorId) {
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new OperatorNotFoundException("Operator not found"));

        Long servedCount = ticketRepository.countByOperatorIdAndStatus(operatorId, TicketStatus.FINISHED);

        return OperatorStatisticsResponse.builder()
                .operatorName(operator.getName())
                .deskNumber(operator.getDeskNumber())
                .servedTicketCount(servedCount)
                .build();
    }

    @Override
    public CreateOperatorResponse create(CreateOperatorRequest request) {
        ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
                .orElseThrow(() -> new ServiceTypeNotFoundException("Service type not found"));

        Operator operator = Operator.builder()
                .name(request.getName())
                .deskNumber(request.getDeskNumber())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .serviceType(serviceType)
                .role(request.getRole())
                .build();

        operatorRepository.save(operator);

        return CreateOperatorResponse.builder()
                .id(operator.getId())
                .name(operator.getName())
                .deskNumber(operator.getDeskNumber())
                .serviceType(serviceType.getName())
                .build();
    }

    @Override
    public List<CreateOperatorResponse> getAll() {
        return operatorRepository.findAll()
                .stream()
                .map(operator -> CreateOperatorResponse.builder()
                        .id(operator.getId())
                        .name(operator.getName())
                        .deskNumber(operator.getDeskNumber())
                        .serviceType(operator.getServiceType().getName())
                        .build())
                .toList();    }

    @Override
    public CreateOperatorResponse getById(Long id) {
        Operator operator = operatorRepository.findById(id)
                .orElseThrow(() -> new OperatorNotFoundException("Operator not found"));

        return CreateOperatorResponse.builder()
                .id(operator.getId())
                .name(operator.getName())
                .deskNumber(operator.getDeskNumber())
                .serviceType(operator.getServiceType().getName())
                .build();
    }

    @Override
    public void delete(Long id) {
        if (!operatorRepository.existsById(id)) {
            throw new OperatorNotFoundException("Operator not found");
        }
        operatorRepository.deleteById(id);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        System.out.println("Authenticated!");

        Operator operator = operatorRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Operator not found"));

        String token = jwtService.generateToken(operator.getUsername());

        return LoginResponse.builder()
                .operatorId(operator.getId())
                .name(operator.getName())
                .deskNumber(operator.getDeskNumber())
                .serviceType(operator.getServiceType().getName())
                .token(token)
                .build();
    }

    @Override
    public OperatorResponse recallTicket(Long operatorId) {

        List<Ticket> tickets =
                ticketRepository.findAllByOperatorIdAndStatus(
                        operatorId,
                        TicketStatus.SERVING
                );

        if (tickets.isEmpty()) {
            throw new RuntimeException("No active ticket");
        }

        Ticket ticket = tickets.get(0);

        return OperatorResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .deskNumber(ticket.getOperator().getDeskNumber())
                .build();
    }

    @Override
    public OperatorResponse skipTicket(Long operatorId) {

        List<Ticket> tickets =
                ticketRepository.findAllByOperatorIdAndStatus(
                        operatorId,
                        TicketStatus.SERVING
                );

        if (tickets.isEmpty()) {
            throw new RuntimeException("No active ticket");
        }

        Ticket ticket = tickets.get(0);

        ticket.setStatus(TicketStatus.SKIPPED);
        ticket.setFinishedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return OperatorResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .deskNumber(ticket.getOperator().getDeskNumber())
                .build();
    }

    @Override
    public OperatorResponse transferTicket(Long operatorId, TransferTicketRequest request) {

        Ticket ticket = ticketRepository
                .findAllByOperatorIdAndStatus(operatorId, TicketStatus.SERVING)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("No active ticket"));

        ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
                        .orElseThrow(() ->
                                new RuntimeException("Service type not found"));

        ticket.setServiceType(serviceType);

        ticket.setStatus(TicketStatus.WAITING);

        ticket.setOperator(null);

        ticket.setCalledAt(null);

        ticketRepository.save(ticket);

        return OperatorResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .deskNumber(null)
                .build();
    }

}
