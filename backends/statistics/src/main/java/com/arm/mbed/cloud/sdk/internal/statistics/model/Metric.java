/*
 * Connect Statistics API
 * mbed Cloud Connect Statistics API provides statistics about other cloud services through defined counters.
 *
 * OpenAPI spec version: 3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.statistics.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * Metric
 */

public class Metric implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("device_server_rest_api_error")
  private Long deviceServerRestApiError = null;

  @SerializedName("bootstraps_failed")
  private Long bootstrapsFailed = null;

  @SerializedName("transactions")
  private Long transactions = null;

  @SerializedName("timestamp")
  private String timestamp = null;

  @SerializedName("registered_devices")
  private Long registeredDevices = null;

  @SerializedName("bootstraps_pending")
  private Long bootstrapsPending = null;

  @SerializedName("device_server_rest_api_success")
  private Long deviceServerRestApiSuccess = null;

  @SerializedName("handshakes_successful")
  private Long handshakesSuccessful = null;

  @SerializedName("bootstraps_successful")
  private Long bootstrapsSuccessful = null;

  @SerializedName("id")
  private String id = null;

  public Metric deviceServerRestApiError(Long deviceServerRestApiError) {
    this.deviceServerRestApiError = deviceServerRestApiError;
    return this;
  }

   /**
   * The number of failed [Connect API](/docs/v1.2/api-references/connect-api.html) requests the account has performed.
   * @return deviceServerRestApiError
  **/
  @ApiModelProperty(example = "null", value = "The number of failed [Connect API](/docs/v1.2/api-references/connect-api.html) requests the account has performed.")
  public Long getDeviceServerRestApiError() {
    return deviceServerRestApiError;
  }

  public void setDeviceServerRestApiError(Long deviceServerRestApiError) {
    this.deviceServerRestApiError = deviceServerRestApiError;
  }

  public Metric bootstrapsFailed(Long bootstrapsFailed) {
    this.bootstrapsFailed = bootstrapsFailed;
    return this;
  }

   /**
   * The number of failed bootstraps the account has performed.
   * @return bootstrapsFailed
  **/
  @ApiModelProperty(example = "null", value = "The number of failed bootstraps the account has performed.")
  public Long getBootstrapsFailed() {
    return bootstrapsFailed;
  }

  public void setBootstrapsFailed(Long bootstrapsFailed) {
    this.bootstrapsFailed = bootstrapsFailed;
  }

  public Metric transactions(Long transactions) {
    this.transactions = transactions;
    return this;
  }

   /**
   * The number of transaction events from or to devices linked to the account. A transaction is a 512-byte block of data processed by mbed Cloud. It can be either sent by the device (device --> mbed cloud) or received by the device (mbed cloud --> device). A transaction does not include IP, TCP or UDP, TLS or DTLS packet overhead. It only contains the packet payload (full CoAP packet including CoAP headers).
   * @return transactions
  **/
  @ApiModelProperty(example = "null", value = "The number of transaction events from or to devices linked to the account. A transaction is a 512-byte block of data processed by mbed Cloud. It can be either sent by the device (device --> mbed cloud) or received by the device (mbed cloud --> device). A transaction does not include IP, TCP or UDP, TLS or DTLS packet overhead. It only contains the packet payload (full CoAP packet including CoAP headers).")
  public Long getTransactions() {
    return transactions;
  }

  public void setTransactions(Long transactions) {
    this.transactions = transactions;
  }

  public Metric timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * UTC time in RFC3339 format. The timestamp is the starting point of the interval for which the data is aggregated. Each interval includes data for the time greater than or equal to the timestamp and less than the next interval's starting point.
   * @return timestamp
  **/
  @ApiModelProperty(example = "null", value = "UTC time in RFC3339 format. The timestamp is the starting point of the interval for which the data is aggregated. Each interval includes data for the time greater than or equal to the timestamp and less than the next interval's starting point.")
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Metric registeredDevices(Long registeredDevices) {
    this.registeredDevices = registeredDevices;
    return this;
  }

   /**
   * The maximum number of registered devices linked to the account. The registered devices count is calculated based on unique registrations plus registration updates over a period of 5 minutes.
   * @return registeredDevices
  **/
  @ApiModelProperty(example = "null", value = "The maximum number of registered devices linked to the account. The registered devices count is calculated based on unique registrations plus registration updates over a period of 5 minutes.")
  public Long getRegisteredDevices() {
    return registeredDevices;
  }

  public void setRegisteredDevices(Long registeredDevices) {
    this.registeredDevices = registeredDevices;
  }

  public Metric bootstrapsPending(Long bootstrapsPending) {
    this.bootstrapsPending = bootstrapsPending;
    return this;
  }

   /**
   * The number of pending bootstraps the account has performed.
   * @return bootstrapsPending
  **/
  @ApiModelProperty(example = "null", value = "The number of pending bootstraps the account has performed.")
  public Long getBootstrapsPending() {
    return bootstrapsPending;
  }

  public void setBootstrapsPending(Long bootstrapsPending) {
    this.bootstrapsPending = bootstrapsPending;
  }

  public Metric deviceServerRestApiSuccess(Long deviceServerRestApiSuccess) {
    this.deviceServerRestApiSuccess = deviceServerRestApiSuccess;
    return this;
  }

   /**
   * The number of successful [Connect API](/docs/v1.2/api-references/connect-api.html) requests the account has performed.
   * @return deviceServerRestApiSuccess
  **/
  @ApiModelProperty(example = "null", value = "The number of successful [Connect API](/docs/v1.2/api-references/connect-api.html) requests the account has performed.")
  public Long getDeviceServerRestApiSuccess() {
    return deviceServerRestApiSuccess;
  }

  public void setDeviceServerRestApiSuccess(Long deviceServerRestApiSuccess) {
    this.deviceServerRestApiSuccess = deviceServerRestApiSuccess;
  }

  public Metric handshakesSuccessful(Long handshakesSuccessful) {
    this.handshakesSuccessful = handshakesSuccessful;
    return this;
  }

   /**
   * The number of successful handshakes the account has performed.
   * @return handshakesSuccessful
  **/
  @ApiModelProperty(example = "null", value = "The number of successful handshakes the account has performed.")
  public Long getHandshakesSuccessful() {
    return handshakesSuccessful;
  }

  public void setHandshakesSuccessful(Long handshakesSuccessful) {
    this.handshakesSuccessful = handshakesSuccessful;
  }

  public Metric bootstrapsSuccessful(Long bootstrapsSuccessful) {
    this.bootstrapsSuccessful = bootstrapsSuccessful;
    return this;
  }

   /**
   * The number of successful bootstraps the account has performed.
   * @return bootstrapsSuccessful
  **/
  @ApiModelProperty(example = "null", value = "The number of successful bootstraps the account has performed.")
  public Long getBootstrapsSuccessful() {
    return bootstrapsSuccessful;
  }

  public void setBootstrapsSuccessful(Long bootstrapsSuccessful) {
    this.bootstrapsSuccessful = bootstrapsSuccessful;
  }

  public Metric id(String id) {
    this.id = id;
    return this;
  }

   /**
   * A unique metric ID.
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "A unique metric ID.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Metric metric = (Metric) o;
    return Objects.equals(this.deviceServerRestApiError, metric.deviceServerRestApiError) &&
        Objects.equals(this.bootstrapsFailed, metric.bootstrapsFailed) &&
        Objects.equals(this.transactions, metric.transactions) &&
        Objects.equals(this.timestamp, metric.timestamp) &&
        Objects.equals(this.registeredDevices, metric.registeredDevices) &&
        Objects.equals(this.bootstrapsPending, metric.bootstrapsPending) &&
        Objects.equals(this.deviceServerRestApiSuccess, metric.deviceServerRestApiSuccess) &&
        Objects.equals(this.handshakesSuccessful, metric.handshakesSuccessful) &&
        Objects.equals(this.bootstrapsSuccessful, metric.bootstrapsSuccessful) &&
        Objects.equals(this.id, metric.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceServerRestApiError, bootstrapsFailed, transactions, timestamp, registeredDevices, bootstrapsPending, deviceServerRestApiSuccess, handshakesSuccessful, bootstrapsSuccessful, id);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Metric {\n");
    
    sb.append("    deviceServerRestApiError: ").append(toIndentedString(deviceServerRestApiError)).append("\n");
    sb.append("    bootstrapsFailed: ").append(toIndentedString(bootstrapsFailed)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    registeredDevices: ").append(toIndentedString(registeredDevices)).append("\n");
    sb.append("    bootstrapsPending: ").append(toIndentedString(bootstrapsPending)).append("\n");
    sb.append("    deviceServerRestApiSuccess: ").append(toIndentedString(deviceServerRestApiSuccess)).append("\n");
    sb.append("    handshakesSuccessful: ").append(toIndentedString(handshakesSuccessful)).append("\n");
    sb.append("    bootstrapsSuccessful: ").append(toIndentedString(bootstrapsSuccessful)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}
