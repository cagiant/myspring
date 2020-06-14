package com.cagiant.de.applicationdemo.foo;

import com.cagiant.de.applicationdemo.context.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class FooConfig {
    @Bean
    public TestBean testBeanX() {
        return new TestBean("foo");
    }

    @Bean
    public TestBean testBeanY() {
        return new TestBean("foo");
    }

//    @Bean
//    public FooAspect fooAspect() {
//        return new FooAspect();
//    }
}
