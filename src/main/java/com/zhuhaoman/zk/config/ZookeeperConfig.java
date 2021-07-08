package com.zhuhaoman.zk.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {
    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(ZookeeperProperties zookeeperProperties) {
        return CuratorFrameworkFactory.newClient(
                zookeeperProperties.getAddress(),
                zookeeperProperties.getSessionTimeoutMs(),
                zookeeperProperties.getConnectionTimeoutMs(),
                new RetryNTimes(zookeeperProperties.getRetryCount(), zookeeperProperties.getElapsedTimeMs()));
    }
}
