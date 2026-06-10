package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperatorStatisticsResponse {

    private String operatorName;

    private Integer deskNumber;

    private Long servedTicketCount;
}
