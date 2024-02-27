package org.spring.pizzarazzi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.pizzarazzi.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi.enums.RoleType;
import org.spring.pizzarazzi.repository.MemberRepository;
import org.spring.pizzarazzi.service.member.MemberService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    private List<RequestMemberSignUpDTO> memberDtos = new ArrayList<>();


    //회원가입 단위 테스트를 위해 오로지 서비스 계층만의 함수를 확인하기 위한 목 객체
    //단위 테스트이므로 DB와 관련되어 있는 리포지토리 계층과 의존 관계가 없어야 함. 그래서 목 객체 이용
    //만약 Autowired MemberService로 테스트를 한다면 이건 통합 테스트임. 오직 서비스 계층만이 아닌 DAO(repository) 계층과도 관련이 있으므로
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    MemberService memberService;

    @BeforeEach
    void initData(){
        memberService = new MemberService(memberRepository,passwordEncoder);

        memberDtos = Arrays.asList(
                RequestMemberSignUpDTO.builder()
                        .email("user1@naver.com")
                        .password("1234")
                        .nickName("사용자1")
                        .roleType(RoleType.valueOf("CONSUMER"))
                        .build(),
                RequestMemberSignUpDTO.builder()
                        .email("user2@naver.com")
                        .password("1234")
                        .nickName("사용자2")
                        .roleType(RoleType.SHOPKEEPER)
                        .build()

        );
    }

    @Test
    void signUp(){
        //given
        when(memberRepository.save(any())).thenReturn(memberDtos.get(0).toEntity(RoleType.CONSUMER));
        when(memberRepository.save(any())).thenReturn(memberDtos.get(1).toEntity(RoleType.CONSUMER));

        //when
        memberService.signup(memberDtos.get(0));
        memberService.signup(memberDtos.get(1));
        
        //then
        // Id 생성 전략을 Identity를 사용하므로, 실제 DBd에 저장되야만 Id가 생성된다. 따라서 테스트에서 Id를 검증할 수 없다.
        // 만약 Id를 검증하려면 Repository를 Mock이 아니라 실제 Bean으로 사용해야 가능할 듯 싶다.

    }
}
