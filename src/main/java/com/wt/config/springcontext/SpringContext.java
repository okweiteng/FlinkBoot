package com.wt.config.springcontext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Objects;

public final class SpringContext implements AutoCloseable {
    private volatile static ClassPathXmlApplicationContext CONTEXT;

    private SpringContext() {
    }

    public static void init() {
        if (Objects.nonNull(CONTEXT)) {
            return;
        }
        synchronized (SpringContext.class) {
            if (Objects.nonNull(CONTEXT)) {
                return;
            }
            CONTEXT = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        }
    }

    public static synchronized <T> T getBean(Class<T> clazz) {
        if (Objects.isNull(CONTEXT)) {
            init();
        }
        return CONTEXT.getBean(clazz);

    }

    @Override
    public void close() {
        if (Objects.isNull(CONTEXT)) {
            return;
        }
        try {
            CONTEXT.close();
        } catch (Exception ignore) {
        }

    }
}
