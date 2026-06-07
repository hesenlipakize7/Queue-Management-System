package queueManagementSystem.service;

import queueManagementSystem.dto.resppnse.OperatorResponse;

public interface OperatorService {

    OperatorResponse nextTicket(Long operatorId);
    OperatorResponse finishTicket(Long ticketId);

}
