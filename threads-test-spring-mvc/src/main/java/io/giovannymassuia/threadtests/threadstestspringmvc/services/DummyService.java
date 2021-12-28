package io.giovannymassuia.threadtests.threadstestspringmvc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class DummyService {

    private final Logger LOGGER = LoggerFactory.getLogger(DummyService.class);

    public String doSomethingSuperSlow(String requestId) {

        String threadName = Thread.currentThread().getName();

        LOGGER.info("Started using thread [{}]. Request ID: [{}]", threadName, requestId);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopWatch.stop();
        LOGGER.info("Completed thread [{}] after {}s. Request ID: [{}]", threadName, stopWatch.getTotalTimeSeconds(), requestId);

        return "Request: " + requestId + " - Thread: " + threadName;
    }

}
