package com.baeldung.common;

import static com.baeldung.common.GlobalConstants.TestMetricTypes.FAILED;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestMetricsExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent() && !context.getTags().contains(GlobalConstants.TAG_SKIP_METRICS)) {
            BaseTest.recordMetrics(1, FAILED);
        }

    }

}
