package com.wt.config.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
@EnableRetry
@RequiredArgsConstructor
public class TaskExecutorConfig implements AsyncConfigurer {

    private final TaskConfigValue config;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("FlinkBootTaskExecutor-");
        executor.setCorePoolSize(config.getCoreSize());
        executor.setMaxPoolSize(config.getMaxSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(config.getAwaitTerminationSeconds());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (exception, method, objects) ->
                log.error("Exception occurs in async method: " + method.getName() +
                        ", objects is: " + Arrays.deepToString(objects) +
                        ", caused by: " + exception.getMessage(), exception);
    }
}
