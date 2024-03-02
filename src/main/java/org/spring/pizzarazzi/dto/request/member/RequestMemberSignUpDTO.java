package org.spring.pizzarazzi.dto.request.member;

import lombok.*;
import org.spring.pizzarazzi.enums.RoleType;
import org.spring.pizzarazzi.model.user.Member;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestMemberSignUpDTO {
    private String email;
    private String password;
    private String nickName;
    private RoleType roleType;
    private String address;
    private String streetAddress;
    private String detailAddress;

    public Member toEntity(RoleType roleType) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .roleType(roleType)
                .address(address)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .build();
    }

}
