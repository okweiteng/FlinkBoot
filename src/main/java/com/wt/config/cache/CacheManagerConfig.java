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

package com.wt.config.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class CacheManagerConfig {
    private final CacheProperties properties;

    @Bean
    public CacheManager cacheManager() {
        List<String> cacheNames = properties.cacheNames();
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCacheNames(cacheNames);
        manager.setAllowNullValues(Objects.equals(properties.getAllowNullValues(), Boolean.TRUE));
        Caffeine<Object, Object> caffeine = Caffeine.from(properties.getCaffeineSpec());
        manager.setCaffeine(caffeine);
        return manager;
    }

    /**
     * 生效条件
     * 1、在 CacheManager 中引用
     * 2、有效的声明 refreshAfterWrite
     * 3、不定义 CacheManager
     * <p>
     * 关于 refreshAfterWrite 和 CacheLoader，只有当该key被调用之后才会执行这里
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {
            @Nullable
            @Override
            public Object load(Object key) throws Exception {
                return null;
            }

            @Nullable
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };
    }
}
