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

package com.wt.config.flink.domain.model;

import lombok.Getter;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;

@Getter
@NoRepositoryBean
@Entity(name = "FLINK_BOOT_FLINK_CONFIG")
public class FlinkConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "JOB_NAME_PREFIX")
    private String namePrefix;

    @Column(name = "JOB_PARALLELISM")
    private Integer parallelism;

    @Column(name = "JOB_MAX_PARALLELISM")
    private Integer maxParallelism;
}
