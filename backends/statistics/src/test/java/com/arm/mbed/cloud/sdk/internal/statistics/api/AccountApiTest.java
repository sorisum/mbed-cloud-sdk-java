package com.arm.mbed.cloud.sdk.internal.statistics.api;

import com.arm.mbed.cloud.sdk.internal.statistics.ApiClient;
import com.arm.mbed.cloud.sdk.internal.statistics.model.ErrorResponse;
import org.joda.time.LocalDate;
import com.arm.mbed.cloud.sdk.internal.statistics.model.SuccessfulResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AccountApi
 */
public class AccountApiTest {

    private AccountApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(AccountApi.class);
    }

    /**
     * Provides account-specific statistics for other cloud services.
     *
     * This REST API is used to get account-specific statistics.
     */
    @Test
    public void getMetricsTest() {
        String include = null;
        String interval = null;
        LocalDate start = null;
        LocalDate end = null;
        String period = null;
        Integer limit = null;
        String after = null;
        String order = null;
        // SuccessfulResponse response = api.getMetrics(include, interval, start, end, period, limit, after, order);

        // TODO: test validations
    }
}
