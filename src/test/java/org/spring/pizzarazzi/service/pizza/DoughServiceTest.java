package org.spring.pizzarazzi.service.pizza;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.pizzarazzi.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddDoughDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.enums.RoleType;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.repository.MemberRepository;
import org.spring.pizzarazzi.repository.pizza.DoughRepository;
import org.spring.pizzarazzi.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DoughServiceTest {
    private List<RequestAddDoughDTO> doughDTOS = new ArrayList<>();


    @Autowired
    DoughService doughService;

    @BeforeEach
    void initData(){
        doughDTOS = Arrays.asList(
                RequestAddDoughDTO.builder()
                        .name("옥수수")
                        .price(10000)
                        .build(),
                RequestAddDoughDTO.builder()
                        .name("감자")
                        .price(20000)
                        .build()

        );
    }

    @Test
    void addDough() {
        //given
        RequestAddDoughDTO requestAddDoughDTO1 = doughDTOS.get(0);
        RequestAddDoughDTO requestAddDoughDTO2 = doughDTOS.get(0);
        //when
        doughService.addDough(requestAddDoughDTO1);
        doughService.addDough(requestAddDoughDTO2);
        //then
        assertEquals(2, doughService.getAllDoughs().size());
        //모든 도우 id 출력
        for (Dough dough : doughService.getAllDoughs()) {
            System.out.println("dough = " + dough.getId());
        }
    }

    @Test
    void getAllDoughs() {
        //given
        addDough();
        //when
        List<Dough> doughs = doughService.getAllDoughs();
        //then
        assertEquals(2, doughs.size());
    }

    @Test
    void deleteDough() {
        //given
        addDough();
        //when
        doughService.deleteDough(RequestDeletePizzaComDTO.builder().id(doughService.getAllDoughs().get(0).getId()).build());
        //then
        assertEquals(1, doughService.getAllDoughs().size());
    }

    @Test
    void updateDough() {
        //given
        addDough();
        //when
        doughService.updateDough(RequestUpdatePizzaComDTO.builder().id(doughService.getAllDoughs().get(0).getId()).name("감자").price(30220).build());
        //then
        assertEquals("감자", doughService.getAllDoughs().get(0).getName());
        assertEquals(30220, doughService.getAllDoughs().get(0).getPrice());
    }
}