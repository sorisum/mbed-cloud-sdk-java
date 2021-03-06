package com.arm.mbed.cloud.sdk.internal.mds.api;

import com.arm.mbed.cloud.sdk.internal.mds.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import com.arm.mbed.cloud.sdk.internal.mds.model.AsyncID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ResourcesApi {
  /**
   * Delete a resource path
   * A request to delete a resource path must be handled by both Device Management Client and Device Management Connect.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  **Example usage:**      curl -X DELETE \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/{resourcePath} \\       -H &#39;authorization: Bearer {api-key}&#39; 
   * @param deviceId A unique Device Management device ID for the endpoint. Note that the ID must be an exact match. You cannot use wildcards here.  (required)
   * @param resourcePath The URL of the resource.  (required)
   * @param noResp If you make a request with &#x60;noResp&#x3D;true&#x60;, Device Management Connect makes a CoAP non-confirmable request to the device. Such requests are not guaranteed to arrive in the device, and you do not get back an async-response-id.  If calls with this parameter enabled succeed, they return with the status code &#x60;204 No Content&#x60;. If the underlying protocol does not support non-confirmable requests, or if the endpoint is registered in queue mode, the response is status code &#x60;409 Conflict&#x60;.  (optional)
   * @return Call&lt;AsyncID&gt;
   */
  @DELETE("v2/endpoints/{device-id}/{resourcePath}")
  Call<AsyncID> deleteResourcePath(
    @retrofit2.http.Path(value = "device-id", encoded = true) String deviceId, @retrofit2.http.Path(value = "resourcePath", encoded = true) String resourcePath, @retrofit2.http.Query("noResp") Boolean noResp
  );

  /**
   * Execute a function on a Resource or create new Object instance
   * With this API, you can [execute a function](/docs/current/connecting/handle-resource-webapp.html#the-execute-operation) on an existing resource and create new Object instance to the device. The resource-path does not have to exist - it can be created by the call. The maximum length of resource-path is 255 characters.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  Supported content types depend on the device and its resource. Device Management translates HTTP to equivalent CoAP content type.  **Example usage:**  This example resets the min and max values of the [temperature sensor](http://www.openmobilealliance.org/tech/profiles/lwm2m/3303.xml) instance 0 by executing the Resource 5605 &#39;Reset Min and Max Measured Values&#39;.      curl -X POST \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/3303/0/5605 \\       -H &#39;authorization: Bearer {api-key}&#39; 
   * @param deviceId A unique Device Management device ID for the endpoint. Note that the ID must be an exact match. You cannot use wildcards here.  (required)
   * @param resourcePath The URL of the resource. (required)
   * @param resourceFunction This value is not needed. Most of the time resources do not accept a function but they have their own functions predefined. You can use this to trigger them.  If a function is included, the body of this request is passed as a char* to the function in Device Management Client.  (optional)
   * @param noResp If you make a request with &#x60;noResp&#x3D;true&#x60;, Device Management Connect makes a CoAP non-confirmable request to the device. Such requests are not guaranteed to arrive in the device, and you do not get back an async-response-id.  If calls with this parameter enabled succeed, they return with the status code &#x60;204 No Content&#x60;. If the underlying protocol does not support non-confirmable requests, or if the endpoint is registered in queue mode, the response is status code &#x60;409 Conflict&#x60;.  (optional)
   * @return Call&lt;AsyncID&gt;
   */
  @Headers({
    "Content-Type:application/vnd.oma.lwm2m+json"
  })
  @POST("v2/endpoints/{device-id}/{resourcePath}")
  Call<AsyncID> executeOrCreateResource(
    @retrofit2.http.Path(value = "device-id", encoded = true) String deviceId, @retrofit2.http.Path(value = "resourcePath", encoded = true) String resourcePath, @retrofit2.http.Body String resourceFunction, @retrofit2.http.Query("noResp") Boolean noResp
  );

  /**
   * Read from a resource
   * Requests the resource value and when the response is available, an &#x60;AsyncIDResponse&#x60; json object is received in the notification channel. The preferred way to get resource values is to use the **subscribe** and **callback** methods.  All resource APIs are asynchronous. These APIs only respond if the device is turned on and connected to Device Management.   See also how [resource caching](/docs/current/connecting/device-guidelines.html#resource-cache) works.  Please refer to [Lightweight Machine to Machine Technical specification](http://www.openmobilealliance.org/release/LightweightM2M/V1_0-20170208-A/OMA-TS-LightweightM2M-V1_0-20170208-A.pdf) for more inforamtion.  **Example usage:**      curl -X GET \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/{resourcePath} \\       -H &#39;authorization: Bearer {api-key}&#39; 
   * @param deviceId Unique Device Management device ID for the endpoint. Note that the ID needs to be an exact match. You cannot use wildcards here.  (required)
   * @param resourcePath The URL of the resource.  (required)
   * @param cacheOnly If true, the response comes only from the cache. Default: false. Device Management Connect caches the received resource values for the time of [max_age](/docs/current/connecting/working-with-the-resources.html) defined in the client side.  (optional)
   * @param noResp If a request is made with &#x60;noResp&#x3D;true&#x60;, Device Management Connect makes a CoAP non-confirmable request to the device. Such requests are not guaranteed to arrive in the device, and you do not get back an async-response-id.  If calls with this parameter enabled succeed, they return with the status code &#x60;204 No Content&#x60;. If the underlying protocol does not support non-confirmable requests, or if the endpoint is registered in queue mode, the response is status code &#x60;409 Conflict&#x60;.  (optional)
   * @return Call&lt;Void&gt;
   */
  @GET("v2/endpoints/{device-id}/{resourcePath}")
  Call<Void> getResourceValue(
    @retrofit2.http.Path(value = "device-id", encoded = true) String deviceId, @retrofit2.http.Path(value = "resourcePath", encoded = true) String resourcePath, @retrofit2.http.Query("cacheOnly") Boolean cacheOnly, @retrofit2.http.Query("noResp") Boolean noResp
  );

  /**
   * Write to a resource or use write-attributes for a resource
   * With this API, you can [write a new value to existing resources](/docs/current/connecting/handle-resource-webapp.html) or [use the write-attributes](/docs/current/connecting/resource-change-webapp.html) for a resource.  This API can also be used to transfer files to the device. Device Management Connect LwM2M server implements the Option 1 from RFC7959. The maximum block size is 1024 bytes. The block size versus transferred file size is something to note in low quality networks. The customer application needs to know what type of file is transferred (for example txt) and the payload can be encrypted by the customer. The maximum size of payload is 1048576 bytes.  All resource APIs are asynchronous. These APIs respond only if the device is turned on and connected to Device Management Connect and there is an active notification channel.  Supported content types depend on the device and its resource. Device Management translates HTTP to equivalent CoAP content type.  **Example usage:**  This example sets the alarm on a buzzer. The command writes the [Buzzer](http://www.openmobilealliance.org/tech/profiles/lwm2m/3338.xml) instance 0, \&quot;On/Off\&quot; boolean resource to &#39;1&#39;.      curl -X PUT \\       https://api.us-east-1.mbedcloud.com/v2/endpoints/{device-id}/3338/0/5850 -H \&quot;content-type: text/plain\&quot; \\       -H &#39;authorization: Bearer {api-key}&#39; -d &#39;1&#39; 
   * @param deviceId A unique Device Management device ID for the endpoint. Note that the ID must be an exact match. You cannot use wildcards here.  (required)
   * @param resourcePath Resource URL. (required)
   * @param resourceValue The value to be set to the resource.  (required)
   * @param noResp If you make a request with &#x60;noResp&#x3D;true&#x60;, Device Management Connect makes a CoAP non-confirmable request to the device. Such requests are not guaranteed to arrive in the device, and you do not get back an async-response-id.  If calls with this parameter enabled succeed, they return with the status code &#x60;204 No Content&#x60;. If the underlying protocol does not support non-confirmable requests, or if the endpoint is registered in queue mode, the response is status code &#x60;409 Conflict&#x60;.  (optional)
   * @return Call&lt;AsyncID&gt;
   */
  @Headers({
    "Content-Type:application/vnd.oma.lwm2m+json"
  })
  @PUT("v2/endpoints/{device-id}/{resourcePath}")
  Call<AsyncID> updateResourceValue(
    @retrofit2.http.Path(value = "device-id", encoded = true) String deviceId, @retrofit2.http.Path(value = "resourcePath", encoded = true) String resourcePath, @retrofit2.http.Body String resourceValue, @retrofit2.http.Query("noResp") Boolean noResp
  );

}
