package com.arm.mbed.cloud.sdk.internal.mds.api;

import com.arm.mbed.cloud.sdk.internal.mds.ApiClient;
import com.arm.mbed.cloud.sdk.internal.mds.model.AsyncID;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ResourcesApi
 */
public class ResourcesApiTest {

    private ResourcesApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(ResourcesApi.class);
    }

    /**
     * Delete a resource path
     *
     * A request to delete a resource path must be handled by both Device Management Client and Device Management Connect.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  **Example usage:**      curl -X DELETE \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/{resourcePath} \\       -H &#39;authorization: Bearer {api-key}&#39; 
     */
    @Test
    public void deleteResourcePathTest() {
        String deviceId = null;
        String resourcePath = null;
        Boolean noResp = null;
        // AsyncID response = api.deleteResourcePath(deviceId, resourcePath, noResp);

        // TODO: test validations
    }
    /**
     * Execute a function on a Resource or create new Object instance
     *
     * With this API, you can [execute a function](/docs/current/connecting/handle-resource-webapp.html#the-execute-operation) on an existing resource and create new Object instance to the device. The resource-path does not have to exist - it can be created by the call. The maximum length of resource-path is 255 characters.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  Supported content types depend on the device and its resource. Device Management translates HTTP to equivalent CoAP content type.  **Example usage:**  This example resets the min and max values of the [temperature sensor](http://www.openmobilealliance.org/tech/profiles/lwm2m/3303.xml) instance 0 by executing the Resource 5605 &#39;Reset Min and Max Measured Values&#39;.      curl -X POST \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/3303/0/5605 \\       -H &#39;authorization: Bearer {api-key}&#39; 
     */
    @Test
    public void executeOrCreateResourceTest() {
        String deviceId = null;
        String resourcePath = null;
        String resourceFunction = null;
        Boolean noResp = null;
        // AsyncID response = api.executeOrCreateResource(deviceId, resourcePath, resourceFunction, noResp);

        // TODO: test validations
    }
    /**
     * Read from a resource
     *
     * Requests the resource value and when the response is available, an &#x60;AsyncIDResponse&#x60; json object is received in the notification channel. The preferred way to get resource values is to use the **subscribe** and **callback** methods.  All resource APIs are asynchronous. These APIs only respond if the device is turned on and connected to Device Management.   See also how [resource caching](/docs/current/connecting/device-guidelines.html#resource-cache) works.  Please refer to [Lightweight Machine to Machine Technical specification](http://www.openmobilealliance.org/release/LightweightM2M/V1_0-20170208-A/OMA-TS-LightweightM2M-V1_0-20170208-A.pdf) for more inforamtion.  **Example usage:**      curl -X GET \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/{resourcePath} \\       -H &#39;authorization: Bearer {api-key}&#39; 
     */
    @Test
    public void getResourceValueTest() {
        String deviceId = null;
        String resourcePath = null;
        Boolean cacheOnly = null;
        Boolean noResp = null;
        // Void response = api.getResourceValue(deviceId, resourcePath, cacheOnly, noResp);

        // TODO: test validations
    }
    /**
     * Write to a resource or use write-attributes for a resource
     *
     * With this API, you can [write a new value to existing resources](/docs/current/connecting/handle-resource-webapp.html) or [use the write-attributes](/docs/current/connecting/resource-change-webapp.html) for a resource.  This API can also be used to transfer files to the device. Device Management Connect LwM2M server implements the Option 1 from RFC7959. The maximum block size is 1024 bytes. The block size versus transferred file size is something to note in low quality networks. The customer application needs to know what type of file is transferred (for example txt) and the payload can be encrypted by the customer. The maximum size of payload is 1048576 bytes.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  Supported content types depend on the device and its resource. Device Management translates HTTP to equivalent CoAP content type.  **Example usage:**  This example sets the alarm on a buzzer. The command writes the [Buzzer](http://www.openmobilealliance.org/tech/profiles/lwm2m/3338.xml) instance 0, \&quot;On/Off\&quot; boolean resource to &#39;1&#39;.      curl -X PUT \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/3338/0/5850 -H \&quot;content-type: text/plain\&quot; \\       -H &#39;authorization: Bearer {api-key}&#39; -d &#39;1&#39; 
     */
    @Test
    public void updateResourceValueTest() {
        String deviceId = null;
        String resourcePath = null;
        String resourceValue = null;
        Boolean noResp = null;
        // AsyncID response = api.updateResourceValue(deviceId, resourcePath, resourceValue, noResp);

        // TODO: test validations
    }
}
