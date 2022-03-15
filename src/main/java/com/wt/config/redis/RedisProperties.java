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
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class RedisProperties {
    @Value("${spring.redis.clientName}")
    private String clientName;

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.maxWait}")
    private Integer poolMaxWait;

    @Value("${spring.redis.pool.maxActive}")
    private Integer poolMaxActive;

    @Value("${spring.redis.pool.maxIdle}")
    private Integer poolMaxIdle;

    @Value("${spring.redis.pool.minIdle}")
    private Integer poolMinIdle;

    @Value("${spring.redis.retryAttempts}")
    private Integer retryAttempts;

    @Value("${spring.redis.retryInterval}")
    private Integer retryInterval;

    @Value("${spring.redis.pool.timeBetweenEvictionRuns}")
    private Integer poolTimeBetweenEvictionRuns;

    @Value("${spring.redisson.pingConnectionInterval}")
    private Integer pingConnectionInterval;

    @Value("${spring.redisson.masterConnectionPoolSize}")
    private Integer masterConnectionPoolSize;

    @Value("${spring.redisson.masterConnectionMinimumIdleSize}")
    private Integer masterConnectionMinimumIdleSize;

    @Value("${spring.redisson.slaveConnectionPoolSize}")
    private Integer slaveConnectionPoolSize;

    @Value("${spring.redisson.slaveConnectionMinimumIdleSize}")
    private Integer slaveConnectionMinimumIdleSize;

    @Value("${spring.redisson.subscriptionConnectionPoolSize}")
    private Integer subscriptionConnectionPoolSize;

    @Value("${spring.redisson.subscriptionConnectionMinimumIdleSize}")
    private Integer subscriptionConnectionMinimumIdleSize;

    public List<String> clusterNodes() {
        return ConfigParser.spit(clusterNodes);
    }
}
