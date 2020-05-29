package com.baeldung.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.common.GlobalConstants.TestMetricTypes;

public class JVMShutdownHook {
    private static Logger logger = LoggerFactory.getLogger(JVMShutdownHook.class);
    static {
        Thread hook = new Thread(() -> {
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("=====================================================");
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("Total failures = " + BaseTest.getMetrics(TestMetricTypes.FAILED));
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("=====================================================");
            resultBuilder.append(System.lineSeparator());           
            logger.info(resultBuilder.toString());
        });
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
