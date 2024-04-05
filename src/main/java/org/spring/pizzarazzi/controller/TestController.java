package org.spring.pizzarazzi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Slf4j
public class TestController {
    @GetMapping("/testThread")
    public String getTestMessage() throws InterruptedException {
        Thread.sleep(10000L);
        return "test";
    }

    @GetMapping("/testMemory")
    public String getMemory() throws InterruptedException {
        log.info("========>>>>> test start ");
        List<MsgDTO> msgList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            msgList.add(new MsgDTO());
        }
        Thread.sleep(10000L);
        return "test";
    }
}
