package com.cart.config;
import java.util.Properties;

import javax.sql.DataSource;

import com.cart.dao.AccountDAO;
import com.cart.dao.ChargeDAO;
import com.cart.dao.OrderDAO;
import com.cart.dao.ProductDAO;
import com.cart.dao.UserDAO;
import com.cart.dao.impl.AccountDAOImpl;
import com.cart.dao.impl.ChargeDAOImpl;
import com.cart.dao.impl.OrderDAOImpl;
import com.cart.dao.impl.ProductDAOImpl;
import com.cart.dao.impl.UserDAOImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages="com.cart")
@EnableWebMvc
@EnableTransactionManagement
// Load to Environment.
@PropertySource("classpath:ds-hibernate-cfg.properties")
public class ApplicationContextConfig {

    // The Environment class serves as the property holder
    // and stores all the properties loaded by the @PropertySource
    @Autowired
    private Environment env;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        // Load property in message/validator.properties
        rb.setBasenames(new String[] { "messages/validator" });
        return rb;
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    
//    @Bean
//    public JpaTransactionManager transactionManager() {
//            JpaTransactionManager transactionManager = new JpaTransactionManager();
//            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//            return transactionManager;
//    }
    
//    @Bean
//    public FooRepository fooRepository() {
//        // configure and return a class having @Transactional methods
//        return new JdbcFooRepository(dataSource());
//    }
    // Config for Upload.
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//
//        // Set Max Size...
//        // commonsMultipartResolver.setMaxUploadSize(...);
//
//        return commonsMultipartResolver;
//    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // See: ds-hibernate-cfg.properties
        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));

        System.out.println("## getDataSource: " + dataSource);

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        // See: ds-hibernate-cfg.properties
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("current_session_context_class", env.getProperty("current_session_context_class"));


        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // Package contain entity classes
        factoryBean.setPackagesToScan(new String[] { "com.cart.entity" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        //
        SessionFactory sf = factoryBean.getObject();
//        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

        return transactionManager;
    }

    @Bean(name = "accountDAO")
    public AccountDAO getApplicantDAO() {
        return new AccountDAOImpl();
    }

    @Bean(name = "productDAO")
    public ProductDAO getProductDAO() {
        return new ProductDAOImpl();
    }

    @Bean(name = "orderDAO")
    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }

    @Bean(name = "accountDAO")
    public AccountDAO getAccountDAO()  {
        return new AccountDAOImpl();
    }
    
    @Bean(name = "chargeDao")
    public ChargeDAO getChargeDAO()
    {
    	return new ChargeDAOImpl();
    }
    
    @Bean(name = "UserDAO")
    public UserDAO getUserDAO()
    {
    	return new UserDAOImpl();
    }
}