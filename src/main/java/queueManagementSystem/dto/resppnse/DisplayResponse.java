package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisplayResponse {

    private Long operatorId;

    private String ticketNumber;

    private Integer deskNumber;
}
