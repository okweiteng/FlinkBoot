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

package com.wt.config.datasource;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DataSourceProperties {
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.jdbcUrl}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.maximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${spring.datasource.hikari.minimumIdle}")
    private Integer minimumIdle;

    @Value("${spring.datasource.hikari.idleTimeout}")
    private Integer idleTimeout;

    @Value("${spring.datasource.hikari.maxLifetime}")
    private Integer maxLifetime;

    @Value("${spring.datasource.hikari.connectionTimeout}")
    private Integer connectionTimeout;

    @Value("${spring.datasource.hikari.connectionTestQuery}")
    private String connectionTestQuery;

    @Value("${spring.datasource.hikari.autoCommit}")
    private Boolean autoCommit;

    @Value("${spring.datasource.hikari.poolName}")
    private String poolName;

    @Value("${spring.datasource.jpa.hibernate.dialect}")
    private String dialect;

    @Value("${spring.datasource.jpa.hibernate.show_sql}")
    private String showSql;

    @Value("${spring.datasource.jpa.hibernate.format_sql}")
    private String formatSql;

    @Value("${spring.datasource.jpa.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

}
