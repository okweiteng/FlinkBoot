package com.wt.config.http;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class HttpConfigValue {
    @Value("${app.http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${app.http.connectRequestTimeout}")
    private Integer connectRequestTimeout;

    @Value("${app.http.socketTime}")
    private Integer socketTime;

    @Value("${app.http.maxTotalConnections}")
    private Integer maxTotalConnections;

    @Value("${app.http.maxPerRoute}")
    private Integer maxPerRoute;

    @Value("${app.http.defaultKeepAliveTime}")
    private Integer defaultKeepAliveTime;

    @Value("${app.http.closeIdleConnectionWaitTime}")
    private Integer closeIdleConnectionWaitTime;
}
