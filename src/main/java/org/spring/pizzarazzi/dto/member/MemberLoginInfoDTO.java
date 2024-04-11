package org.spring.pizzarazzi.dto.member;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.spring.pizzarazzi.enums.RoleType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberLoginInfoDTO {
    private String email;
    private String nickName;
    private RoleType roleType;
    private String address;
    private String zoneCode;
    private String detailAddress;
}
