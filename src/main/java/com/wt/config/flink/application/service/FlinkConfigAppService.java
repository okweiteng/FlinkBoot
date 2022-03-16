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

package com.wt.config.flink.application.service;

import com.wt.config.flink.application.dto.FlinkConfigDto;
import com.wt.config.flink.domain.service.FlinkConfigRepository;
import com.wt.config.retry.RetryableProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlinkConfigAppService {
    private final FlinkConfigRepository repository;

    private final RetryableProperties retryableProperties;

    @Transactional(readOnly = true)
    @Retryable(maxAttemptsExpression = "#{@retryableProperties.maxAttempts}",
            backoff = @Backoff(
                    delayExpression = "#{@retryableProperties.delay}",
                    maxDelayExpression = "#{@retryableProperties.maxDelay}",
                    multiplierExpression = "#{@retryableProperties.multiplier}"))
    public List<FlinkConfigDto> findAll() {
        return repository.findAll().stream()
                .map(FlinkConfigDto::new)
                .collect(Collectors.toList());
    }
}
