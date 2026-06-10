package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsResponse {

    private Long totalTickets;

    private Long waitingTickets;

    private Long servingTickets;

    private Long finishedTickets;

    private Long cancelledTickets;
}
