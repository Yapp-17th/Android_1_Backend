package org.picon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.picon.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MemberController memberController;

    @Test
    @DisplayName("아이디에 따라 회원 조회")
    void getMemberTest() {
        MemberDto id = memberController.getMember("id");
        System.out.println(id);
    }
}