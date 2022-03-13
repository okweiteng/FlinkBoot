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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
@Ignore
public class FlinkConfigCacheServiceTest {

    @Autowired
    private FlinkConfigCacheService cacheService;

    private FlinkConfigAppService mockAppService;

    @Before
    public void setUp() {
        mockAppService = Mockito.mock(FlinkConfigAppService.class);
        ReflectionTestUtils.setField(cacheService, "appService", mockAppService);
        FlinkConfigDto f = new FlinkConfigDto(1, "", 1, 1);
        Mockito.when(mockAppService.findAll()).thenReturn(Collections.singletonList(f));
    }

    @Test
    public void findAll() {
        List<FlinkConfigDto> actual = cacheService.findAll();
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(1, actual.get(0).getId().intValue());
        Mockito.verify(mockAppService, Mockito.times(1)).findAll();

        actual = cacheService.findAll();
        Mockito.verify(mockAppService, Mockito.times(1)).findAll();
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(1, actual.get(0).getId().intValue());
    }
}