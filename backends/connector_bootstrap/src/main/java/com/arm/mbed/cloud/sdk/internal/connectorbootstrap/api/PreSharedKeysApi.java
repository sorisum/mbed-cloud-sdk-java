package com.arm.mbed.cloud.sdk.internal.connectorbootstrap.api;

import com.arm.mbed.cloud.sdk.internal.connectorbootstrap.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import com.arm.mbed.cloud.sdk.internal.connectorbootstrap.model.ListOfPreSharedKeysWithoutSecret;
import com.arm.mbed.cloud.sdk.internal.connectorbootstrap.model.PreSharedKey;
import com.arm.mbed.cloud.sdk.internal.connectorbootstrap.model.PreSharedKeyWithoutSecret;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PreSharedKeysApi {
  /**
   * Remove a pre-shared key.
   * Remove a pre-shared key.
   * @param endpointName The unique endpoint identifier that this pre-shared key applies to. [Reserved characters](https://en.wikipedia.org/wiki/Percent-encoding#Percent-encoding_reserved_characters) must be percent-encoded. (required)
   * @return Call&lt;Void&gt;
   */
  @DELETE("v2/device-shared-keys/{endpoint_name}")
  Call<Void> deletePreSharedKey(
    @retrofit2.http.Path(value = "endpoint_name", encoded = true) String endpointName
  );

  /**
   * Get a pre-shared key.
   * Check if a pre-shared key for an endpoint exists or not. The response does not contain the secret itself. 
   * @param endpointName The unique endpoint identifier that this pre-shared key applies to. [Reserved characters](https://en.wikipedia.org/wiki/Percent-encoding#Percent-encoding_reserved_characters) must be percent-encoded. (required)
   * @return Call&lt;PreSharedKeyWithoutSecret&gt;
   */
  @GET("v2/device-shared-keys/{endpoint_name}")
  Call<PreSharedKeyWithoutSecret> getPreSharedKey(
    @retrofit2.http.Path(value = "endpoint_name", encoded = true) String endpointName
  );

  /**
   * List pre-shared keys.
   * List pre-shared keys with pagination and default page size of 50 entries. 
   * @param limit The number of entries per page (optional)
   * @param after An offset token for fetching a specific page. Provided by the server. (optional)
   * @return Call&lt;ListOfPreSharedKeysWithoutSecret&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("v2/device-shared-keys")
  Call<ListOfPreSharedKeysWithoutSecret> listPreSharedKeys(
    @retrofit2.http.Query("limit") Integer limit, @retrofit2.http.Query("after") String after
  );

  /**
   * Upload a pre-shared key to Mbed Cloud.
   * Upload a pre-shared key (PSK) for an endpoint to allow it to bootstrap. The existing key will not be overwritten but needs to be deleted first in case of re-setting PSK for an endpoint.  **Note**: The PSK APIs are available only to accounts that have this feature enabled.  &#x60;&#x60;&#x60; Example payloads: {\&quot;endpoint_name\&quot;: \&quot;myEndpoint.host.com\&quot;, \&quot;secret_hex\&quot;: \&quot;4a4a4a4a4a4a4a4a4a4a4a4a4a4a4a4a\&quot; } {\&quot;endpoint_name\&quot;: \&quot;myEndpoint.host.com\&quot;, \&quot;secret_hex\&quot;: \&quot;0x4a4a4a4a4a4a4a4a4a4a4a4a4a4a4a4a\&quot; } &#x60;&#x60;&#x60; 
   * @param body Pre-shared key to be uploaded. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("v2/device-shared-keys")
  Call<Void> uploadPreSharedKey(
    @retrofit2.http.Body PreSharedKey body
  );

}