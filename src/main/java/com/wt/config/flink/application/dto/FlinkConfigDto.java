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

package com.wt.config.flink.application.dto;

import com.wt.config.flink.domain.model.FlinkConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FlinkConfigDto {
    private Integer id;

    private String namePrefix;

    private Integer parallelism;

    private Integer maxParallelism;

    public FlinkConfigDto(FlinkConfig f) {
        this.id = f.getId();
        this.namePrefix = f.getNamePrefix();
        this.parallelism = f.getParallelism();
        this.maxParallelism = f.getMaxParallelism();
    }
}
