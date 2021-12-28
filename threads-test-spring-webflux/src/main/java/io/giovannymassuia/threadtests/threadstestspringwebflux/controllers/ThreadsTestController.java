package io.giovannymassuia.threadtests.threadstestspringwebflux.controllers;

import io.giovannymassuia.threadtests.threadstestspringwebflux.services.DummyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ThreadsTestController {

    private final Logger LOGGER = LoggerFactory.getLogger(ThreadsTestController.class);

    private final DummyService dummyService;

    public ThreadsTestController(DummyService dummyService) {this.dummyService = dummyService;}

    @GetMapping
    public Mono<String> threadTest(@RequestParam String requestId) {
        String threadName = Thread.currentThread().getName();

        return dummyService
                .doSomethingSuperSlow(requestId)
                .doFirst(() -> LOGGER.info("Started using thread [{}]. Request ID: [{}]", threadName, requestId))
                .log();
    }

}
