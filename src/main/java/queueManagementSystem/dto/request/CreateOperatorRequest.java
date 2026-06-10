package queueManagementSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOperatorRequest {

    private String name;

    private Integer deskNumber;

    private String username;

    private String password;

    private Long serviceTypeId;
}
