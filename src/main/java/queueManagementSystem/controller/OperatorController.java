package queueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import queueManagementSystem.dto.request.CreateOperatorRequest;
import queueManagementSystem.dto.request.LoginRequest;
import queueManagementSystem.dto.resppnse.CreateOperatorResponse;
import queueManagementSystem.dto.resppnse.LoginResponse;
import queueManagementSystem.dto.resppnse.OperatorResponse;
import queueManagementSystem.dto.resppnse.OperatorStatisticsResponse;
import queueManagementSystem.service.OperatorService;

import java.util.List;

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

    @GetMapping("/{id}/statistics")
    public ResponseEntity<OperatorStatisticsResponse> getStatistics(@PathVariable Long id){
        return ResponseEntity.ok(operatorService.getOperatorStatistics(id));
    }

    @PostMapping
    public ResponseEntity<CreateOperatorResponse> create(@RequestBody CreateOperatorRequest request){
        return ResponseEntity.ok(operatorService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CreateOperatorResponse>> getAll(){
        return ResponseEntity.ok(operatorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateOperatorResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(operatorService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        operatorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(operatorService.login(request));
    }
}
