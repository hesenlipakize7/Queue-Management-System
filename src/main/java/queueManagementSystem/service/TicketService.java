package queueManagementSystem.service;

import queueManagementSystem.dto.request.CreateTicketRequest;
import queueManagementSystem.dto.resppnse.TicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest createTicketRequest);
    List<TicketResponse> getWaitingTickets();
}
