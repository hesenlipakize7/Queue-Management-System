package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KioskResponse {

    private Long serviceTypeId;

    private String serviceName;

    private Long waitingCount;
}
