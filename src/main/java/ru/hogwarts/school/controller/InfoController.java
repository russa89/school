package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.FacultyService;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getServerPort() {
        return "The application is running on port: " + serverPort;
    }

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/sum")
    public void getSum() {
        long start = System.currentTimeMillis();
        logger.info("start 1st");
        int result = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);

        long finish = System.currentTimeMillis() - start;
        logger.info("finish 1st" + finish);

        long start2 = System.currentTimeMillis();
        logger.info("start 2nd");
        int result2 = Stream
                .iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);

        long finish2 = System.currentTimeMillis() - start2;
        logger.info("finish 2nd" + finish2);
    }
}
