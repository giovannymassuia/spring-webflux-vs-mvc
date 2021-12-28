package io.giovannymassuia.threadtests.threadstestspringmvc.controllers;

import io.giovannymassuia.threadtests.threadstestspringmvc.services.DummyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadsTestController {

    private final Logger LOGGER = LoggerFactory.getLogger(ThreadsTestController.class);

    private final DummyService dummyService;

    public ThreadsTestController(DummyService dummyService) {this.dummyService = dummyService;}

    @GetMapping()
    public String threadTest(@RequestParam String requestId) {
        String threadName = Thread.currentThread().getName();

        LOGGER.info("Started using thread [{}]. Request ID: [{}]", threadName, requestId);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String result = dummyService.doSomethingSuperSlow(requestId);

        stopWatch.stop();
        LOGGER.info("Completed thread [{}] after {}s. Request ID: [{}]", threadName, stopWatch.getTotalTimeSeconds(), requestId);

        return result;
    }

}
