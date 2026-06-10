package queueManagementSystem.dto.resppnse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {


    private Long operatorId;

    private String name;

    private Integer deskNumber;

    private String serviceType;

    private String token;
}
