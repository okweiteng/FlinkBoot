package com.wt.config.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Closeable;

public final class SpringContextSingleton implements Closeable {

    private SpringContextSingleton() {
    }

    public static synchronized <T> T getBean(Class<T> clazz) {
        return InnerApplicationContext.context.getBean(clazz);
    }

    @Override
    public void close() {
        try {
            InnerApplicationContext.context.close();
        } catch (Exception ignore) {
        }
    }

    private static class InnerApplicationContext {
        private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }

}
