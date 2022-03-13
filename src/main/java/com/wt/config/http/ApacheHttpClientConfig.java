package com.wt.config.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class ApacheHttpClientConfig {
    // https://springframework.guru/using-resttemplate-with-apaches-httpclient
    private static final int SCHEDULED_FIXED_DELAY_TIME = 20000;

    private static final int SEC_TO_MILLIS = 1000;

    private final HttpConfigValue config;

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        // set a total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(config.getMaxTotalConnections());
        // set a maximum amount of connections for each HTTP route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(config.getMaxPerRoute());
        return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
            HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

            while (elementIterator.hasNext()) {
                HeaderElement element = elementIterator.nextElement();
                String param = element.getName();
                String value = element.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    try {
                        return Long.parseLong(value) * SEC_TO_MILLIS; // convert to ms
                    } catch (Exception ignore) {
                    }
                }
            }
            return config.getDefaultKeepAliveTime();
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = SCHEDULED_FIXED_DELAY_TIME)
            public void run() {
                closeIdleConnections(pool);
            }
        };
    }

    private void closeIdleConnections(PoolingHttpClientConnectionManager pool) {
        try {
            if (Objects.isNull(pool)) {
                return;
            }
            pool.closeExpiredConnections();
            pool.closeIdleConnections(config.getCloseIdleConnectionWaitTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("closeIdleConnections failed, caused by: " + e.getMessage(), e);
        }
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.getConnectTimeout())
                .setConnectionRequestTimeout(config.getConnectRequestTimeout())
                .setSocketTimeout(config.getSocketTime())
                .build();
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }
}
