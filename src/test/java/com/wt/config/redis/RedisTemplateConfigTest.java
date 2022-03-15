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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
@Ignore
public class RedisTemplateConfigTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void stringRedisTemplate() {
        String testKey = "wt_spring_boot_test";
        String testValue = "1";
        redisTemplate.opsForValue().set(testKey, testValue, 100, TimeUnit.SECONDS);
        String actual = redisTemplate.opsForValue().get(testKey);
        Assert.assertEquals(testValue, actual);
    }

    @Test
    public void lock() {
        RLock lock = redissonClient.getLock("flink_boot_test_lock");
        try {
            boolean success = lock.tryLock(10, 10, TimeUnit.SECONDS);
            if (!success) {
                return;
            }
            System.out.println("locked");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(lock)) {
                lock.unlock();
            }
        }
    }

}