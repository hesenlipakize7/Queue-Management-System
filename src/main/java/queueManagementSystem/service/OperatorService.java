package queueManagementSystem.service;

import queueManagementSystem.dto.request.CreateOperatorRequest;
import queueManagementSystem.dto.request.LoginRequest;
import queueManagementSystem.dto.resppnse.CreateOperatorResponse;
import queueManagementSystem.dto.resppnse.LoginResponse;
import queueManagementSystem.dto.resppnse.OperatorResponse;
import queueManagementSystem.dto.resppnse.OperatorStatisticsResponse;

import java.util.List;

public interface OperatorService {

    OperatorResponse nextTicket(Long operatorId);

    OperatorResponse finishTicket(Long ticketId);

    OperatorStatisticsResponse getOperatorStatistics(Long operatorId);

    CreateOperatorResponse create(CreateOperatorRequest request);

    List<CreateOperatorResponse> getAll();

    CreateOperatorResponse getById(Long id);

    void delete(Long id);

    LoginResponse login(LoginRequest request);
}
