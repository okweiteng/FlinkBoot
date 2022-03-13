package com.wt.config.thread;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TaskConfigValue {
    @Value("${app.async.threadPool.coreSize}")
    private Integer coreSize;

    @Value("${app.async.threadPool.maxSize}")
    private Integer maxSize;

    @Value("${app.async.threadPool.queueCapacity}")
    private Integer queueCapacity;

    @Value("${app.async.threadPool.awaitTerminationSeconds}")
    private Integer awaitTerminationSeconds;
}
