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

import com.wt.config.flink.FlinkConfigValue;
import com.wt.config.springcontext.SpringContextSingleton;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Skeleton code for implementing a fraud detector.
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(FraudDetector.class);
    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;
    private transient FlinkConfigValue flinkCfg;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        flinkCfg = SpringContextSingleton.getBean(FlinkConfigValue.class);
    }

    @Override
    public void processElement(Transaction transaction, Context context, Collector<Alert> collector) {
        try {
            String namePrefix = flinkCfg.getNamePrefix();
            LOG.info("processElement, test spring context: " + namePrefix);
            Alert alert = new Alert();
            alert.setId(transaction.getAccountId());
            collector.collect(alert);
        } catch (Exception e) {
            LOG.error("processElement failed", e);
        }

    }
}
