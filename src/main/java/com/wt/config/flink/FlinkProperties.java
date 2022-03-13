package com.wt.config.flink;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FlinkProperties {
    @Value("${flink.job.name.prefix}")
    private String namePrefix;

    @Value("${flink.job.parallelism}")
    private Integer parallelism;

    @Value("${flink.job.maxParallelism}")
    private Integer maxParallelism;

    @Value("${flink.job.checkpoint.enableCheckpoint}")
    private Boolean enableCheckpoint;

    @Value("${flink.job.checkpoint.checkpointMode}")
    private String checkpointMode;

    @Value("${flink.job.checkpoint.checkpointInterval}")
    private Integer checkpointInterval;

    @Value("${flink.job.checkpoint.minPauseBetweenCheckpoints}")
    private Integer minPauseBetweenCheckpoints;

    @Value("${flink.job.checkpoint.checkpointTimeout}")
    private Integer checkpointTimeout;

    @Value("${flink.job.checkpoint.tolerableCheckpointFailureNumber}")
    private Integer tolerableCheckpointFailureNumber;

    @Value("${flink.job.checkpoint.maxConcurrentCheckpoints}")
    private Integer maxConcurrentCheckpoints;

    @Value("${flink.job.checkpoint.enableUnalignedCheckpoints}")
    private Boolean enableUnalignedCheckpoints;

    @Value("${flink.job.restartStrategy.fixedDelay.restartAttempts}")
    private Integer fixedDelayRestartAttempts;

    @Value("${flink.job.restartStrategy.fixedDelay.delayBetweenAttempts}")
    private Integer fixedDelayBetweenAttempts;
}
