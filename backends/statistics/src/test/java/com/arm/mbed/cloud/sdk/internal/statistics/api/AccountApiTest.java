package com.arm.mbed.cloud.sdk.internal.statistics.api;

import com.arm.mbed.cloud.sdk.internal.ApiClient;
import com.arm.mbed.cloud.sdk.internal.statistics.model.ErrorResponse;
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
    public void v3MetricsGetTest() {
        String include = null;
        String interval = null;
        String start = null;
        String end = null;
        String period = null;
        Integer limit = null;
        String after = null;
        String order = null;
        // SuccessfulResponse response = api.v3MetricsGet(include, interval, start, end, period, limit, after, order);

        // TODO: test validations
    }
    
}
