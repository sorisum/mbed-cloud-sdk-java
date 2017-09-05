/*
 * Device Directory API
 * This is the API Documentation for the mbed device directory update service.
 *
 * OpenAPI spec version: 3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.devicedirectory.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import java.io.Serializable;

/**
 * DeviceDataPostRequest
 */

public class DeviceDataPostRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("bootstrap_expiration_date")
  private DateTime bootstrapExpirationDate = null;

  @SerializedName("bootstrapped_timestamp")
  private DateTime bootstrappedTimestamp = null;

  @SerializedName("connector_expiration_date")
  private DateTime connectorExpirationDate = null;

  /**
   * The ID of the channel used to communicate with the device.
   */
  public enum MechanismEnum {
    @SerializedName("connector")
    CONNECTOR("connector"),
    
    @SerializedName("direct")
    DIRECT("direct");

    private String value;

    MechanismEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("mechanism")
  private MechanismEnum mechanism = null;

  @SerializedName("device_class")
  private String deviceClass = null;

  @SerializedName("endpoint_name")
  private String endpointName = null;

  @SerializedName("auto_update")
  private Boolean autoUpdate = null;

  @SerializedName("host_gateway")
  private String hostGateway = null;

  @SerializedName("device_execution_mode")
  private Integer deviceExecutionMode = null;

  @SerializedName("custom_attributes")
  private Map<String, String> customAttributes = new HashMap<String, String>();

  /**
   * The current state of the device.
   */
  public enum StateEnum {
    @SerializedName("unenrolled")
    UNENROLLED("unenrolled"),
    
    @SerializedName("cloud_enrolling")
    CLOUD_ENROLLING("cloud_enrolling"),
    
    @SerializedName("bootstrapped")
    BOOTSTRAPPED("bootstrapped"),
    
    @SerializedName("registered")
    REGISTERED("registered"),
    
    @SerializedName("deregistered")
    DEREGISTERED("deregistered");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("state")
  private StateEnum state = null;

  @SerializedName("serial_number")
  private String serialNumber = null;

  @SerializedName("firmware_checksum")
  private String firmwareChecksum = null;

  @SerializedName("vendor_id")
  private String vendorId = null;

  @SerializedName("description")
  private String description = null;

  /**
   * DEPRECATED The state of the device's deployment.
   */
  public enum DeployedStateEnum {
    @SerializedName("development")
    DEVELOPMENT("development"),
    
    @SerializedName("production")
    PRODUCTION("production");

    private String value;

    DeployedStateEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("deployed_state")
  private DeployedStateEnum deployedState = null;

  @SerializedName("object")
  private String object = null;

  @SerializedName("endpoint_type")
  private String endpointType = null;

  @SerializedName("deployment")
  private String deployment = null;

  @SerializedName("mechanism_url")
  private String mechanismUrl = null;

  @SerializedName("trust_level")
  private Integer trustLevel = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("device_key")
  private String deviceKey = null;

  @SerializedName("manifest")
  private String manifest = null;

  @SerializedName("ca_id")
  private String caId = null;

  public DeviceDataPostRequest bootstrapExpirationDate(DateTime bootstrapExpirationDate) {
    this.bootstrapExpirationDate = bootstrapExpirationDate;
    return this;
  }

   /**
   * Expiration date of the certificate used to connect to bootstrap server.
   * @return bootstrapExpirationDate
  **/
  @ApiModelProperty(example = "null", value = "Expiration date of the certificate used to connect to bootstrap server.")
  public DateTime getBootstrapExpirationDate() {
    return bootstrapExpirationDate;
  }

  public void setBootstrapExpirationDate(DateTime bootstrapExpirationDate) {
    this.bootstrapExpirationDate = bootstrapExpirationDate;
  }

  public DeviceDataPostRequest bootstrappedTimestamp(DateTime bootstrappedTimestamp) {
    this.bootstrappedTimestamp = bootstrappedTimestamp;
    return this;
  }

   /**
   * Timestamp of when the device last went through the bootstrap process.
   * @return bootstrappedTimestamp
  **/
  @ApiModelProperty(example = "null", value = "Timestamp of when the device last went through the bootstrap process.")
  public DateTime getBootstrappedTimestamp() {
    return bootstrappedTimestamp;
  }

  public void setBootstrappedTimestamp(DateTime bootstrappedTimestamp) {
    this.bootstrappedTimestamp = bootstrappedTimestamp;
  }

  public DeviceDataPostRequest connectorExpirationDate(DateTime connectorExpirationDate) {
    this.connectorExpirationDate = connectorExpirationDate;
    return this;
  }

   /**
   * Expiration date of the certificate used to connect to the lwm2m server.
   * @return connectorExpirationDate
  **/
  @ApiModelProperty(example = "null", value = "Expiration date of the certificate used to connect to the lwm2m server.")
  public DateTime getConnectorExpirationDate() {
    return connectorExpirationDate;
  }

  public void setConnectorExpirationDate(DateTime connectorExpirationDate) {
    this.connectorExpirationDate = connectorExpirationDate;
  }

  public DeviceDataPostRequest mechanism(MechanismEnum mechanism) {
    this.mechanism = mechanism;
    return this;
  }

   /**
   * The ID of the channel used to communicate with the device.
   * @return mechanism
  **/
  @ApiModelProperty(example = "null", value = "The ID of the channel used to communicate with the device.")
  public MechanismEnum getMechanism() {
    return mechanism;
  }

  public void setMechanism(MechanismEnum mechanism) {
    this.mechanism = mechanism;
  }

  public DeviceDataPostRequest deviceClass(String deviceClass) {
    this.deviceClass = deviceClass;
    return this;
  }

   /**
   * An id representing the model and hardware revision of the device.
   * @return deviceClass
  **/
  @ApiModelProperty(example = "null", value = "An id representing the model and hardware revision of the device.")
  public String getDeviceClass() {
    return deviceClass;
  }

  public void setDeviceClass(String deviceClass) {
    this.deviceClass = deviceClass;
  }

  public DeviceDataPostRequest endpointName(String endpointName) {
    this.endpointName = endpointName;
    return this;
  }

   /**
   * The endpoint name given to the device.
   * @return endpointName
  **/
  @ApiModelProperty(example = "null", value = "The endpoint name given to the device.")
  public String getEndpointName() {
    return endpointName;
  }

  public void setEndpointName(String endpointName) {
    this.endpointName = endpointName;
  }

  public DeviceDataPostRequest autoUpdate(Boolean autoUpdate) {
    this.autoUpdate = autoUpdate;
    return this;
  }

   /**
   * DEPRECATED Mark this device for auto firmware update.
   * @return autoUpdate
  **/
  @ApiModelProperty(example = "null", value = "DEPRECATED Mark this device for auto firmware update.")
  public Boolean getAutoUpdate() {
    return autoUpdate;
  }

  public void setAutoUpdate(Boolean autoUpdate) {
    this.autoUpdate = autoUpdate;
  }

  public DeviceDataPostRequest hostGateway(String hostGateway) {
    this.hostGateway = hostGateway;
    return this;
  }

   /**
   * The endpoint_name of the host gateway, if appropriate.
   * @return hostGateway
  **/
  @ApiModelProperty(example = "null", value = "The endpoint_name of the host gateway, if appropriate.")
  public String getHostGateway() {
    return hostGateway;
  }

  public void setHostGateway(String hostGateway) {
    this.hostGateway = hostGateway;
  }

  public DeviceDataPostRequest deviceExecutionMode(Integer deviceExecutionMode) {
    this.deviceExecutionMode = deviceExecutionMode;
    return this;
  }

   /**
   * Defines the type of certificate used.
   * @return deviceExecutionMode
  **/
  @ApiModelProperty(example = "null", value = "Defines the type of certificate used.")
  public Integer getDeviceExecutionMode() {
    return deviceExecutionMode;
  }

  public void setDeviceExecutionMode(Integer deviceExecutionMode) {
    this.deviceExecutionMode = deviceExecutionMode;
  }

  public DeviceDataPostRequest customAttributes(Map<String, String> customAttributes) {
    this.customAttributes = customAttributes;
    return this;
  }

  public DeviceDataPostRequest putCustomAttributesItem(String key, String customAttributesItem) {
    this.customAttributes.put(key, customAttributesItem);
    return this;
  }

   /**
   * Custom attributes(key/value). Up to 5 attributes
   * @return customAttributes
  **/
  @ApiModelProperty(example = "null", value = "Custom attributes(key/value). Up to 5 attributes")
  public Map<String, String> getCustomAttributes() {
    return customAttributes;
  }

  public void setCustomAttributes(Map<String, String> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public DeviceDataPostRequest state(StateEnum state) {
    this.state = state;
    return this;
  }

   /**
   * The current state of the device.
   * @return state
  **/
  @ApiModelProperty(example = "null", value = "The current state of the device.")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public DeviceDataPostRequest serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

   /**
   * The serial number of the device.
   * @return serialNumber
  **/
  @ApiModelProperty(example = "null", value = "The serial number of the device.")
  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public DeviceDataPostRequest firmwareChecksum(String firmwareChecksum) {
    this.firmwareChecksum = firmwareChecksum;
    return this;
  }

   /**
   * The SHA256 checksum of the current firmware image.
   * @return firmwareChecksum
  **/
  @ApiModelProperty(example = "null", value = "The SHA256 checksum of the current firmware image.")
  public String getFirmwareChecksum() {
    return firmwareChecksum;
  }

  public void setFirmwareChecksum(String firmwareChecksum) {
    this.firmwareChecksum = firmwareChecksum;
  }

  public DeviceDataPostRequest vendorId(String vendorId) {
    this.vendorId = vendorId;
    return this;
  }

   /**
   * The device vendor ID.
   * @return vendorId
  **/
  @ApiModelProperty(example = "null", value = "The device vendor ID.")
  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public DeviceDataPostRequest description(String description) {
    this.description = description;
    return this;
  }

   /**
   * The description of the device.
   * @return description
  **/
  @ApiModelProperty(example = "null", value = "The description of the device.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DeviceDataPostRequest deployedState(DeployedStateEnum deployedState) {
    this.deployedState = deployedState;
    return this;
  }

   /**
   * DEPRECATED The state of the device's deployment.
   * @return deployedState
  **/
  @ApiModelProperty(example = "null", value = "DEPRECATED The state of the device's deployment.")
  public DeployedStateEnum getDeployedState() {
    return deployedState;
  }

  public void setDeployedState(DeployedStateEnum deployedState) {
    this.deployedState = deployedState;
  }

  public DeviceDataPostRequest object(String object) {
    this.object = object;
    return this;
  }

   /**
   * The API resource entity.
   * @return object
  **/
  @ApiModelProperty(example = "null", value = "The API resource entity.")
  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public DeviceDataPostRequest endpointType(String endpointType) {
    this.endpointType = endpointType;
    return this;
  }

   /**
   * The endpoint type of the device - e.g. if the device is a gateway.
   * @return endpointType
  **/
  @ApiModelProperty(example = "null", value = "The endpoint type of the device - e.g. if the device is a gateway.")
  public String getEndpointType() {
    return endpointType;
  }

  public void setEndpointType(String endpointType) {
    this.endpointType = endpointType;
  }

  public DeviceDataPostRequest deployment(String deployment) {
    this.deployment = deployment;
    return this;
  }

   /**
   * DEPRECATED The last deployment used on the device.
   * @return deployment
  **/
  @ApiModelProperty(example = "null", value = "DEPRECATED The last deployment used on the device.")
  public String getDeployment() {
    return deployment;
  }

  public void setDeployment(String deployment) {
    this.deployment = deployment;
  }

  public DeviceDataPostRequest mechanismUrl(String mechanismUrl) {
    this.mechanismUrl = mechanismUrl;
    return this;
  }

   /**
   * The address of the connector to use.
   * @return mechanismUrl
  **/
  @ApiModelProperty(example = "null", value = "The address of the connector to use.")
  public String getMechanismUrl() {
    return mechanismUrl;
  }

  public void setMechanismUrl(String mechanismUrl) {
    this.mechanismUrl = mechanismUrl;
  }

  public DeviceDataPostRequest trustLevel(Integer trustLevel) {
    this.trustLevel = trustLevel;
    return this;
  }

   /**
   * The device trust level.
   * @return trustLevel
  **/
  @ApiModelProperty(example = "null", value = "The device trust level.")
  public Integer getTrustLevel() {
    return trustLevel;
  }

  public void setTrustLevel(Integer trustLevel) {
    this.trustLevel = trustLevel;
  }

  public DeviceDataPostRequest name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the device.
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "The name of the device.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeviceDataPostRequest deviceKey(String deviceKey) {
    this.deviceKey = deviceKey;
    return this;
  }

   /**
   * Fingerprint of the device certificate.
   * @return deviceKey
  **/
  @ApiModelProperty(example = "null", required = true, value = "Fingerprint of the device certificate.")
  public String getDeviceKey() {
    return deviceKey;
  }

  public void setDeviceKey(String deviceKey) {
    this.deviceKey = deviceKey;
  }

  public DeviceDataPostRequest manifest(String manifest) {
    this.manifest = manifest;
    return this;
  }

   /**
   * DEPRECATED The URL for the current device manifest.
   * @return manifest
  **/
  @ApiModelProperty(example = "null", value = "DEPRECATED The URL for the current device manifest.")
  public String getManifest() {
    return manifest;
  }

  public void setManifest(String manifest) {
    this.manifest = manifest;
  }

  public DeviceDataPostRequest caId(String caId) {
    this.caId = caId;
    return this;
  }

   /**
   * ID of the issuer of the certificate.
   * @return caId
  **/
  @ApiModelProperty(example = "null", required = true, value = "ID of the issuer of the certificate.")
  public String getCaId() {
    return caId;
  }

  public void setCaId(String caId) {
    this.caId = caId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceDataPostRequest deviceDataPostRequest = (DeviceDataPostRequest) o;
    return Objects.equals(this.bootstrapExpirationDate, deviceDataPostRequest.bootstrapExpirationDate) &&
        Objects.equals(this.bootstrappedTimestamp, deviceDataPostRequest.bootstrappedTimestamp) &&
        Objects.equals(this.connectorExpirationDate, deviceDataPostRequest.connectorExpirationDate) &&
        Objects.equals(this.mechanism, deviceDataPostRequest.mechanism) &&
        Objects.equals(this.deviceClass, deviceDataPostRequest.deviceClass) &&
        Objects.equals(this.endpointName, deviceDataPostRequest.endpointName) &&
        Objects.equals(this.autoUpdate, deviceDataPostRequest.autoUpdate) &&
        Objects.equals(this.hostGateway, deviceDataPostRequest.hostGateway) &&
        Objects.equals(this.deviceExecutionMode, deviceDataPostRequest.deviceExecutionMode) &&
        Objects.equals(this.customAttributes, deviceDataPostRequest.customAttributes) &&
        Objects.equals(this.state, deviceDataPostRequest.state) &&
        Objects.equals(this.serialNumber, deviceDataPostRequest.serialNumber) &&
        Objects.equals(this.firmwareChecksum, deviceDataPostRequest.firmwareChecksum) &&
        Objects.equals(this.vendorId, deviceDataPostRequest.vendorId) &&
        Objects.equals(this.description, deviceDataPostRequest.description) &&
        Objects.equals(this.deployedState, deviceDataPostRequest.deployedState) &&
        Objects.equals(this.object, deviceDataPostRequest.object) &&
        Objects.equals(this.endpointType, deviceDataPostRequest.endpointType) &&
        Objects.equals(this.deployment, deviceDataPostRequest.deployment) &&
        Objects.equals(this.mechanismUrl, deviceDataPostRequest.mechanismUrl) &&
        Objects.equals(this.trustLevel, deviceDataPostRequest.trustLevel) &&
        Objects.equals(this.name, deviceDataPostRequest.name) &&
        Objects.equals(this.deviceKey, deviceDataPostRequest.deviceKey) &&
        Objects.equals(this.manifest, deviceDataPostRequest.manifest) &&
        Objects.equals(this.caId, deviceDataPostRequest.caId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bootstrapExpirationDate, bootstrappedTimestamp, connectorExpirationDate, mechanism, deviceClass, endpointName, autoUpdate, hostGateway, deviceExecutionMode, customAttributes, state, serialNumber, firmwareChecksum, vendorId, description, deployedState, object, endpointType, deployment, mechanismUrl, trustLevel, name, deviceKey, manifest, caId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceDataPostRequest {\n");
    
    sb.append("    bootstrapExpirationDate: ").append(toIndentedString(bootstrapExpirationDate)).append("\n");
    sb.append("    bootstrappedTimestamp: ").append(toIndentedString(bootstrappedTimestamp)).append("\n");
    sb.append("    connectorExpirationDate: ").append(toIndentedString(connectorExpirationDate)).append("\n");
    sb.append("    mechanism: ").append(toIndentedString(mechanism)).append("\n");
    sb.append("    deviceClass: ").append(toIndentedString(deviceClass)).append("\n");
    sb.append("    endpointName: ").append(toIndentedString(endpointName)).append("\n");
    sb.append("    autoUpdate: ").append(toIndentedString(autoUpdate)).append("\n");
    sb.append("    hostGateway: ").append(toIndentedString(hostGateway)).append("\n");
    sb.append("    deviceExecutionMode: ").append(toIndentedString(deviceExecutionMode)).append("\n");
    sb.append("    customAttributes: ").append(toIndentedString(customAttributes)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
    sb.append("    firmwareChecksum: ").append(toIndentedString(firmwareChecksum)).append("\n");
    sb.append("    vendorId: ").append(toIndentedString(vendorId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    deployedState: ").append(toIndentedString(deployedState)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    endpointType: ").append(toIndentedString(endpointType)).append("\n");
    sb.append("    deployment: ").append(toIndentedString(deployment)).append("\n");
    sb.append("    mechanismUrl: ").append(toIndentedString(mechanismUrl)).append("\n");
    sb.append("    trustLevel: ").append(toIndentedString(trustLevel)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    deviceKey: ").append(toIndentedString(deviceKey)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
    sb.append("    caId: ").append(toIndentedString(caId)).append("\n");
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

