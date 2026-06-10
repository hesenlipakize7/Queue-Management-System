package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.request.CreateOperatorRequest;
import queueManagementSystem.dto.request.LoginRequest;
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
                .password(request.getPassword())
                .serviceType(serviceType)
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

        Operator operator = operatorRepository
                .findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() ->
                        new RuntimeException("Username or password is incorrect"));

        String token = jwtService.generateToken(operator.getUsername());

        return LoginResponse.builder()
                .operatorId(operator.getId())
                .name(operator.getName())
                .deskNumber(operator.getDeskNumber())
                .serviceType(operator.getServiceType().getName())
                .token(token)
                .build();
    }

}
