package org.spring.pizzarazzi.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi.dto.member.MemberSignUpDTO;
import org.spring.pizzarazzi.enums.RoleType;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.model.user.Member;
import org.spring.pizzarazzi.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService  {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String eamil) throws UsernameNotFoundException {
        log.info("MemberService.loadUserByEmail");
        log.info("LOGIN");
        return memberRepository.findByEmail(eamil)
                .orElseThrow(() -> new UsernameNotFoundException(eamil + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    public void signup(MemberSignUpDTO memberSignUpDTO) {
        log.info("MemberService.signup");
        if(memberRepository.existsByEmail(memberSignUpDTO.getEmail())) {
            throw new DuplicateMemberException("이미 가입되어 있는 중복된 이메일입니다.");
        }
        if(memberRepository.existsByNickName(memberSignUpDTO.getNickName())) {
            throw new DuplicateMemberException("이미 가입되어 있는 중복된 닉네임입니다.");
        }

        Member member = memberSignUpDTO.toEntity(memberSignUpDTO.getRoleType());
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }
}
