package com.wt.config.http;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class HttpPropertiesTest {

    @Autowired
    private HttpProperties configValue;

    @Test
    public void getConnectTimeout() {
        Assert.assertNotNull(configValue);
        Assert.assertNotNull(configValue.getConnectTimeout());
    }
}