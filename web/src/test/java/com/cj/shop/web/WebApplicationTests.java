package com.cj.shop.web;

import com.cj.shop.web.utils.UcUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    UcUtil tokenValidator;

    @Test
    public void test() throws Exception {
        int i = 1;
        for (; i < 10; i++) {
            System.out.println("第" + i + "次调用 uid=" + tokenValidator.isExistUser("18879881009"));
        }
    }
}
