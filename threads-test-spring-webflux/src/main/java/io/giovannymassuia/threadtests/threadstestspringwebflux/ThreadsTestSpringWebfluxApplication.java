package io.giovannymassuia.threadtests.threadstestspringwebflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ThreadsTestSpringWebfluxApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(ThreadsTestSpringWebfluxApplication.class);

    @Value("${server.netty.threads.max}")
    private int totalThreads;

    public static void main(String[] args) {
        SpringApplication.run(ThreadsTestSpringWebfluxApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        LOGGER.info("Application running with only {} threads.", totalThreads);
    }

}
