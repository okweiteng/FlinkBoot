package com.wt.config.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Objects;

public final class SpringContext implements AutoCloseable {
    private static volatile ClassPathXmlApplicationContext context;

    private SpringContext() {
    }

    /**
     * init spring context.
     */
    public static void init() {
        if (Objects.nonNull(context)) {
            return;
        }
        synchronized (SpringContext.class) {
            if (Objects.nonNull(context)) {
                return;
            }
            context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        }
    }

    public static synchronized <T> T getBean(Class<T> clazz) {
        if (Objects.isNull(context)) {
            init();
        }
        return context.getBean(clazz);

    }

    @Override
    public void close() {
        if (Objects.isNull(context)) {
            return;
        }
        try {
            context.close();
        } catch (Exception ignore) {
        }

    }
}
