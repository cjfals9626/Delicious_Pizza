package org.spring.pizzarazzi.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.enums.RoleType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestMemberUpdateDTO {
    private String email;
    private String nickName;
    private String password;
    private String address;
    private String zoneCode;
    private String detailAddress;
}
