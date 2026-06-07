package queueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queueManagementSystem.dto.resppnse.OperatorResponse;
import queueManagementSystem.service.OperatorService;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorService operatorService;

    @PostMapping("/{id}/next")
    public ResponseEntity<OperatorResponse> nextTicket(@PathVariable Long id){
        return ResponseEntity.ok(operatorService.nextTicket(id));
    }

    @PostMapping("/{ticketId}/finish")
    public ResponseEntity<OperatorResponse> finishTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(operatorService.finishTicket(ticketId));
    }
}
