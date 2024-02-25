package org.spring.pizzarazzi.dto.member;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.spring.pizzarazzi.enums.RoleType;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MemberLoginInfoDTO {
    private String email;
    private String nickName;
    private RoleType roleType;
}
