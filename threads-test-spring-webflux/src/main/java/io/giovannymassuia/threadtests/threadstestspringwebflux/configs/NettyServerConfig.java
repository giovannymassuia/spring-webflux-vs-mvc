package io.giovannymassuia.threadtests.threadstestspringwebflux.configs;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;

@Configuration
public class NettyServerConfig {

    @Bean
    public ReactorResourceFactory reactorResourceFactory(@Value("${server.netty.threads.max}") int totalThreads) {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setLoopResources(b -> new NioEventLoopGroup(totalThreads));
        factory.setUseGlobalResources(false);
        return factory;
    }

}
