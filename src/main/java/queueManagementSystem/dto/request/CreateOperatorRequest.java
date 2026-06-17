package queueManagementSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import queueManagementSystem.enums.Role;

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

    private Role role;
}
