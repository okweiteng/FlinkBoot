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

package com.wt.config.redis;

import com.wt.config.util.ConfigParser;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RedissonClientConfig {
    private final RedisProperties properties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(convert(properties.getClusterNodes()))
                .setPassword(properties.getPassword())
                .setConnectTimeout(properties.getTimeout())
                .setIdleConnectionTimeout(properties.getTimeout())
                .setPingConnectionInterval(properties.getPingConnectionInterval())
                .setClientName(properties.getClientName())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setCheckSlotsCoverage(false)
                .setMasterConnectionPoolSize(properties.getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(properties.getMasterConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(properties.getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(properties.getSlaveConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(properties.getSubscriptionConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getSubscriptionConnectionMinimumIdleSize());
        return Redisson.create(config);
    }

    private String[] convert(String nodeObjects) {
        return ConfigParser.spit(nodeObjects).stream()
                .map(node -> (!node.startsWith("redis://") && !node.startsWith("rediss://"))
                        ? ("redis://" + node) : node)
                .toArray(String[]::new);
    }
}
