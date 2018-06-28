package com.advance.mgr;

import com.advance.MgrServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgrServiceApplication.class)
@WebAppConfiguration
public class BaseTest {

}
