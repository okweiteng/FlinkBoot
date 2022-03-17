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

package com.wt.flink.transform;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.walkthrough.common.entity.Alert;

import java.util.Objects;

public class MaxFunction implements ReduceFunction<Alert> {
    @Override
    public Alert reduce(Alert value1, Alert value2) {
        if (Objects.isNull(value1) && Objects.isNull(value2)) {
            return new Alert();
        }
        if (Objects.isNull(value2)) {
            return value1;
        }
        if (Objects.isNull(value1)) {
            return value2;
        }
        return value1.getId() >= value2.getId() ? value1 : value2;
    }
}
