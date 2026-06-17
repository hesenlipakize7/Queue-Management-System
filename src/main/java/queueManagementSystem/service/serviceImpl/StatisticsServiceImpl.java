package queueManagementSystem.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import queueManagementSystem.dto.resppnse.KioskResponse;
import queueManagementSystem.dto.resppnse.StatisticsResponse;
import queueManagementSystem.entity.ServiceType;
import queueManagementSystem.enums.TicketStatus;
import queueManagementSystem.repository.ServiceTypeRepository;
import queueManagementSystem.repository.TicketRepository;
import queueManagementSystem.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TicketRepository ticketRepository;

    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<KioskResponse> getKioskData() {

        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

        List<KioskResponse> response = new ArrayList<>();

        for (ServiceType serviceType : serviceTypes) {
            System.out.println(serviceType.getId());

            Long waitingCount = ticketRepository.countByServiceType_IdAndStatus(
                    serviceType.getId(),
                    TicketStatus.WAITING
            );

            response.add(KioskResponse.builder()
                            .serviceTypeId( serviceType.getId())
                            .serviceName(serviceType.getName())
                            .waitingCount(waitingCount)
                            .build());
        }
        return response;
    }

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
