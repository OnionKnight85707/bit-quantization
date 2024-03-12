package com.mzwise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author piao
 * @Date 2021/05/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryMemberTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void queryMemberList() {
        System.out.println(passwordEncoder.encode("123456"));
//        System.out.println("123456");
    }
}
