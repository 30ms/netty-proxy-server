package com.example.datacollector.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventLoopGroupConfig {

    @Bean
    EventLoopGroup eventExecutors() {
        return new NioEventLoopGroup();
    }
}
