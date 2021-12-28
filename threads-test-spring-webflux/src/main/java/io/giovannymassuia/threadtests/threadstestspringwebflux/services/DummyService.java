package io.giovannymassuia.threadtests.threadstestspringwebflux.services;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Mono;

@Service
public class DummyService {

    private final Logger LOGGER = LoggerFactory.getLogger(DummyService.class);

    public Mono<String> doSomethingSuperSlow(String requestId) {
        String threadName = Thread.currentThread().getName();
        StopWatch stopWatch = new StopWatch();

        return Mono
                .just("Request: " + requestId + " - Thread: " + threadName)
                .delayElement(Duration.ofSeconds(5))
                .doFirst(() -> {
                    stopWatch.start();
                    LOGGER.info("Started using thread [{}]. Request ID: [{}]", threadName, requestId);
                })
                .doFinally(signalType -> {
                    stopWatch.stop();
                    LOGGER.info("Completed thread [{}] after {}s. Request ID: [{}]", threadName, stopWatch.getTotalTimeSeconds(), requestId);
                });
    }

}
