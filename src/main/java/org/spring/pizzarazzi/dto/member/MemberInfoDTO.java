package org.spring.pizzarazzi.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.enums.RoleType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberInfoDTO {
    private String email;
    private String nickName;
    private RoleType roleType;
}

