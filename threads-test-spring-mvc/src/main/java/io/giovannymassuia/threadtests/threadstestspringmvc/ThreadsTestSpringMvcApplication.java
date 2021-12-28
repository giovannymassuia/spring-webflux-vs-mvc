package io.giovannymassuia.threadtests.threadstestspringmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ThreadsTestSpringMvcApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(ThreadsTestSpringMvcApplication.class);

    @Value("${server.tomcat.threads.max}")
    private int totalThreads;

    public static void main(String[] args) {
        SpringApplication.run(ThreadsTestSpringMvcApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        LOGGER.info("Application running with only {} threads.", totalThreads);
    }

}
