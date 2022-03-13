/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wt.flink;

import com.wt.config.flink.FlinkConfigValue;
import com.wt.config.springcontext.SpringContextSingleton;
import com.wt.flink.transform.FraudDetector;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FraudDetectionJob {

    private static final Logger LOG = LoggerFactory.getLogger(FraudDetectionJob.class);

    public static void main(String[] args) {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            FlinkConfigValue flinkCfg = SpringContextSingleton.getBean(FlinkConfigValue.class);
            configParallelism(env, flinkCfg);
            configStateBackend(env, flinkCfg);
            configCheckpoint(env, flinkCfg);
            configRestartStrategy(env, flinkCfg);

            DataStream<Transaction> transactions = env
                    .addSource(new TransactionSource())
                    .setParallelism(1)
                    .setMaxParallelism(4)
                    .uid("transactions")
                    .name("transactions");

            DataStream<Alert> alerts = transactions
                    .keyBy(Transaction::getAccountId)
                    .process(new FraudDetector())
                    .setParallelism(1)
                    .setMaxParallelism(4)
                    .uid("fraud-detector")
                    .name("fraud-detector");

            alerts
                    .addSink(new AlertSink())
                    .setParallelism(1)
                    .uid("send-alerts")
                    .name("send-alerts");

            env.execute("Fraud Detection");
        } catch (Exception e) {
            LOG.error("execute job failed, caused by {}", e.getMessage(), e);
        }

    }

    private static void configParallelism(StreamExecutionEnvironment env, FlinkConfigValue flinkCfg) {
        env.setParallelism(flinkCfg.getParallelism());
        env.setMaxParallelism(flinkCfg.getParallelism());
    }

    private static void configStateBackend(StreamExecutionEnvironment env, FlinkConfigValue flinkCfg) {
        env.setStateBackend(new HashMapStateBackend());
    }

    private static void configRestartStrategy(StreamExecutionEnvironment env, FlinkConfigValue flinkCfg) {
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                flinkCfg.getFixedDelayRestartAttempts(), flinkCfg.getFixedDelayBetweenAttempts()));
    }

    private static void configCheckpoint(StreamExecutionEnvironment env, FlinkConfigValue flinkCfg) {
        if (!Objects.equals(flinkCfg.getEnableCheckpoint(), Boolean.TRUE)) {
            return;
        }
        env.enableCheckpointing(flinkCfg.getCheckpointInterval(), CheckpointingMode.valueOf(flinkCfg.getCheckpointMode().toUpperCase()));
        CheckpointConfig checkpointCfg = env.getCheckpointConfig();
        checkpointCfg.setCheckpointTimeout(flinkCfg.getCheckpointTimeout());
        checkpointCfg.setMinPauseBetweenCheckpoints(flinkCfg.getMinPauseBetweenCheckpoints());
        checkpointCfg.setMaxConcurrentCheckpoints(flinkCfg.getMaxConcurrentCheckpoints());
        checkpointCfg.setTolerableCheckpointFailureNumber(flinkCfg.getTolerableCheckpointFailureNumber());
        checkpointCfg.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        if (Objects.equals(flinkCfg.getEnableUnalignedCheckpoints(), Boolean.TRUE)) {
            checkpointCfg.enableUnalignedCheckpoints();
        }
    }
}
