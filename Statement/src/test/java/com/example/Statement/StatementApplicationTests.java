package com.example.Statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = StatementApplication.class)
@TestPropertySource("classpath:.env")
class StatementApplicationTests {

    @Test
    void contextLoads() {
    }

}
