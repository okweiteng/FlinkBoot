package com.wt.config.spring;

import com.wt.config.datasource.DataSourceProperties;
import com.wt.config.flink.FlinkProperties;
import com.wt.config.http.HttpProperties;
import com.wt.config.thread.TaskProperties;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SpringContextSingletonTest {

    @Test
    public void getBean() {
        TaskProperties taskProperties = SpringContextSingleton.getBean(TaskProperties.class);
        Assert.assertNotNull(taskProperties);
        Assert.assertNotNull(taskProperties.getCoreSize());

        HttpProperties httpProperties = SpringContextSingleton.getBean(HttpProperties.class);
        Assert.assertNotNull(httpProperties);
        Assert.assertNotNull(httpProperties.getConnectTimeout());

        FlinkProperties flinkProperties = SpringContextSingleton.getBean(FlinkProperties.class);
        Assert.assertNotNull(flinkProperties);
        Assert.assertNotNull(flinkProperties.getEnableCheckpoint());

        DataSourceProperties dataSourceProperties = SpringContextSingleton.getBean(DataSourceProperties.class);
        Assert.assertNotNull(dataSourceProperties);
        Assert.assertNotNull(dataSourceProperties.getDriverClassName());
    }
}