package ru.javawebinar.topjava.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextSpringUtil {


    private static ContextSpringUtil ourInstance = new ContextSpringUtil();


    private ConfigurableApplicationContext appCtx;


    public ConfigurableApplicationContext getAppCtx() {
        return appCtx;
    }

    public static ContextSpringUtil getOurInstance() {
        return ourInstance;
    }

    private ContextSpringUtil() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    }
}
