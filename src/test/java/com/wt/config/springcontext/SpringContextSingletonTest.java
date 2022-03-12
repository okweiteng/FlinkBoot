package com.wt.config.springcontext;

import com.wt.config.flink.FlinkConfigValue;
import com.wt.config.http.HttpConfigValue;
import com.wt.config.thread.TaskConfigValue;
import org.junit.Assert;
import org.junit.Test;

public class SpringContextSingletonTest {

    @Test
    public void getBean() {
        TaskConfigValue taskConfigValue = SpringContextSingleton.getBean(TaskConfigValue.class);
        Assert.assertNotNull(taskConfigValue);
        Assert.assertNotNull(taskConfigValue.getCoreSize());

        HttpConfigValue httpConfigValue = SpringContextSingleton.getBean(HttpConfigValue.class);
        Assert.assertNotNull(httpConfigValue);
        Assert.assertNotNull(httpConfigValue.getConnectTimeout());

        FlinkConfigValue flinkConfigValue = SpringContextSingleton.getBean(FlinkConfigValue.class);
        Assert.assertNotNull(flinkConfigValue);
        Assert.assertNotNull(flinkConfigValue.getEnableCheckpoint());
    }
}