package com.zr;

import com.zr.sender.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringbootRabbitmqApplicationTests {

    @Autowired
    Sender sender;

    @Test
    public void contextLoads() {
        sender.send();
    }
}
