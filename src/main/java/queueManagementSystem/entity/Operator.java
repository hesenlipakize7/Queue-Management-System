package queueManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import queueManagementSystem.enums.Role;

@Entity
@Table(name = "operators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer deskNumber;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

}
