package queueManagementSystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueManagementSystem.dto.request.CreateTicketRequest;
import queueManagementSystem.dto.resppnse.TicketResponse;
import queueManagementSystem.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody @Valid CreateTicketRequest createTicketRequest) {
        return  ResponseEntity.ok(ticketService.createTicket(createTicketRequest));
    }
    @GetMapping("/waiting")
    public ResponseEntity<List<TicketResponse>> getWaitingTickets() {
        return ResponseEntity.ok(ticketService.getWaitingTickets());
    }
}
