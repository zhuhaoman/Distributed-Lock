package com.zhuhaoman.zk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private int retryCount;

    private int elapsedTimeMs;

    private String address;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;
}
