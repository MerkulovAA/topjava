package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class TestStopWatch extends Stopwatch {

    private static final Logger log = getLogger(TestStopWatch.class);

    private static Map<Description, Long> mapAllResultTest = new HashMap<>();

    private static void logInfo(Description description, long nanos) {
        log.info(String.format("Test %s - %d milliseconds", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
        mapAllResultTest.put(description, nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, nanos);
    }

    public static void getAllStatisticTestsDuration(String className) {
        log.info("+++++++++++++++++++++++++++++++++++++");
        TestStopWatch.mapAllResultTest.forEach((description, aLong) -> log.info(
                String.format("Test %s - %d milliseconds",
                        description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(aLong))));

        log.info(String.format("Total time tests %s class - %d milliseconds", className,
                TimeUnit.NANOSECONDS.toMillis(TestStopWatch.mapAllResultTest.values().stream().mapToLong(Number::longValue).sum())));
        log.info("+++++++++++++++++++++++++++++++++++++");
    }

    public static void clearAllStatisticsResultTests() {
        TestStopWatch.mapAllResultTest.clear();
    }
}
