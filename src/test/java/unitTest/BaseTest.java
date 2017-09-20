package unitTest;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cart.config.ApplicationContextConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationContextConfig.class)
//@TransactionConfiguration(defaultRollback = true)
//@Transactional 
@WebAppConfiguration
public class BaseTest  {

}

