package org.spring.pizzarazzi.service.member;

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

}
