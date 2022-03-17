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

package com.wt.flink.transform;

import com.wt.config.spring.SpringContextSingleton;
import com.wt.flink.transform.service.FraudDetectorService;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Skeleton code for implementing a fraud detector.
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(FraudDetector.class);

    private static final double SMALL_AMOUNT = 1.00;

    private static final double LARGE_AMOUNT = 500.00;

    private static final long ONE_MINUTE = 60 * 1000;

    private transient FraudDetectorService service;

    private transient ReducingState<Alert> maxAlertState;

    @Override

    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        maxAlertState = getMaxAlertState();
        service = SpringContextSingleton.getBean(FraudDetectorService.class);
    }

    private ReducingState<Alert> getMaxAlertState() {
        StateTtlConfig ttl = StateTtlConfig
                .newBuilder(Time.seconds(1440))
                // TTL 的更新策略（默认是 OnCreateAndWrite）：仅在创建和写入时更新
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                // 启用全量快照时进行清理的策略，这可以减少整个快照的大小。当前实现中不会清理本地的状态，但从上次快照恢复时，不会恢复那些已经删除的过期数据。
                // 这种策略在 RocksDBStateBackend 的增量 checkpoint 模式下无效。
                .cleanupFullSnapshot()
                .build();
        ReducingStateDescriptor<Alert> descriptor = new ReducingStateDescriptor<>(
                "max-alert",
                new MaxFunction(),
                Alert.class);
        descriptor.enableTimeToLive(ttl);
        return getRuntimeContext().getReducingState(descriptor);
    }

    @Override
    public void processElement(Transaction transaction, Context context, Collector<Alert> collector) {
        if (Objects.isNull(transaction)) {
            return;
        }
        try {
            Alert alert = service.processElement(transaction);
            maxAlertState.add(alert);
            collector.collect(maxAlertState.get());
        } catch (Exception e) {
            LOG.error("processElement failed, transaction is: {}", transaction, e);
        }
    }
}
