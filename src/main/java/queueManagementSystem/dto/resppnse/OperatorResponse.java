package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperatorResponse {

    private String ticketNumber;

    private Integer deskNumber;
}
