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

package com.wt.flink.transform.service;

import com.wt.config.flink.application.dto.FlinkConfigDto;
import com.wt.config.flink.application.service.FlinkConfigCacheService;
import lombok.RequiredArgsConstructor;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudDetectorService {

    private static final Logger LOG = LoggerFactory.getLogger(FraudDetectorService.class);

    private final FlinkConfigCacheService flinkConfigCacheService;

    public Alert processElement(Transaction transaction) {
        printSpringFunction();
        Alert alert = new Alert();
        alert.setId(transaction.getAccountId());
        return alert;
    }

    private void printSpringFunction() {
        // bad case, just show that it works reading db data and cache in flink operator
        List<FlinkConfigDto> all = flinkConfigCacheService.findAll();
        String info = "processElement, test spring context: " + all.stream()
                .map(FlinkConfigDto::toString)
                .collect(Collectors.joining("; "));
        System.out.println(info);
        LOG.info(info);
    }
}
