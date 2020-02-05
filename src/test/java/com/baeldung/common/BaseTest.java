package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.common.GlobalConstants.TestMetricTypes;

public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected static Logger baseLogger = LoggerFactory.getLogger(BaseTest.class);
    protected static AtomicInteger failedTests = new AtomicInteger();

    @BeforeAll
    public static void init() {
        failedTests.set(0);
    }

    protected static void recordMetrics(int count, TestMetricTypes metricType) {
        if (metricType.equals(TestMetricTypes.FAILED)) {
            failedTests.getAndAdd(count);
        }
    }

    protected static int getMetrics(TestMetricTypes metricType) {
        if (metricType.equals(TestMetricTypes.FAILED)) {
            return failedTests.get();
        }
        return -1;
    }

    protected void failTestWithLoggingTotalNoOfFailures(String fialureMessage) {
        fail(fialureMessage + Utils.messageForTotalNoOfFailuresAtTheTestLevel(getMetrics(TestMetricTypes.FAILED)));
    }

    @AfterAll
    public static void logTestMertics() {
        if (failedTests.get() != 0) {
            baseLogger.info(Utils.messageForTotalNoOfFailures(failedTests.get()));
        }

    }
}
