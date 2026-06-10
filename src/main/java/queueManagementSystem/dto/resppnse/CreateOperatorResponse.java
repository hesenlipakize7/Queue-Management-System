package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOperatorResponse {

    private Long id;

    private String name;

    private Integer deskNumber;

    private String ticketNumber;

    private String serviceType;
}
