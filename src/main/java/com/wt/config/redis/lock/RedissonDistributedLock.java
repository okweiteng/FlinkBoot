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

package com.wt.config.redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonDistributedLock implements DistributedLock {

    private static final String REDISSON_LOCK_PREFIX = "REDISSON_LOCK_PREFIX:";

    private final RedissonClient redissonClient;

    private final LockProperties properties;

    @Override
    public void tryLockNoWait(String lockName, Consumer<Lock> lockConsumer) {
        RLock lock = null;
        try {
            lock = redissonClient.getLock(REDISSON_LOCK_PREFIX + lockName);
            boolean success = lock.tryLock();
            if (!success) {
                return;
            }
            lockConsumer.accept(lock);
        } catch (Exception e) {
            log.error("an exception occurred when executing method with lock", e);
        } finally {
            if (Objects.nonNull(lock)) {
                lock.unlock();
            }
        }
    }

    @Override
    public void tryLockWithWait(String lockName, Consumer<Lock> lockConsumer) {
        RLock lock = null;
        try {
            lock = redissonClient.getLock(REDISSON_LOCK_PREFIX + lockName);
            boolean success = lock.tryLock(properties.getWaitTimeInSec(), properties.getLeaseTimeInSec(), TimeUnit.SECONDS);
            if (!success) {
                return;
            }
            lockConsumer.accept(lock);
        } catch (Exception e) {
            log.error("an exception occurred when executing method with lock", e);
        } finally {
            if (Objects.nonNull(lock)) {
                lock.unlock();
            }
        }
    }
}