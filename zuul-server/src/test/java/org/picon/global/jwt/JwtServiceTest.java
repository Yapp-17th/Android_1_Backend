//package org.picon.global.jwt;
//
//import com.netflix.discovery.converters.Auto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class JwtServiceTest {
//    @Autowired
//    JwtService jwtService;
//
//    @Test
//    @DisplayName("findEmailByToken 테스트 ")
//    void findEmailByTokenTest() {
//        String emailByToken = jwtService.findEmailByToken("eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJFTUFJTCI6InRlc3RFbWFpbDEiLCJleHAiOjE2MDQ4MzI2NzYsImlhdCI6MTYwNDgzMjA3Nn0.3fwOpLaE6Wf9xL7Nhy-1Wu0yNZj8fMzna-d93o1J3Dg");
//        System.out.println("emailByToken = " + emailByToken);
//    }
//
//}