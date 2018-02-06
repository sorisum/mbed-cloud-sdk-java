/*
 * Update Service API
 * This is the API documentation for the Mbed deployment service, which is part of the update service.
 *
 * OpenAPI spec version: 3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.updateservice.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import org.joda.time.DateTime;
import java.io.Serializable;

/**
 * UpdateCampaignPutRequest
 */

public class UpdateCampaignPutRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("description")
  private String description = null;

  @SerializedName("root_manifest_id")
  private String rootManifestId = null;

  @SerializedName("object")
  private String object = null;

  @SerializedName("when")
  private DateTime when = null;

  /**
   * The state of the campaign
   */
  @JsonAdapter(StateEnum.Adapter.class)
  public enum StateEnum {
    DRAFT("draft"),
    
    SCHEDULED("scheduled"),
    
    ALLOCATINGQUOTA("allocatingquota"),
    
    ALLOCATEDQUOTA("allocatedquota"),
    
    INSUFFICIENTQUOTA("insufficientquota"),
    
    CHECKINGMANIFEST("checkingmanifest"),
    
    CHECKEDMANIFEST("checkedmanifest"),
    
    DEVICEFETCH("devicefetch"),
    
    DEVICECOPY("devicecopy"),
    
    DEVICECHECK("devicecheck"),
    
    PUBLISHING("publishing"),
    
    DEPLOYING("deploying"),
    
    DEPLOYED("deployed"),
    
    MANIFESTREMOVED("manifestremoved"),
    
    EXPIRED("expired"),
    
    STOPPING("stopping"),
    
    AUTOSTOPPED("autostopped"),
    
    USERSTOPPED("userstopped"),
    
    CONFLICT("conflict");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StateEnum fromValue(String text) {
      for (StateEnum b : StateEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StateEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StateEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("state")
  private StateEnum state = null;

  @SerializedName("device_filter")
  private String deviceFilter = null;

  @SerializedName("name")
  private String name = null;

  public UpdateCampaignPutRequest description(String description) {
    this.description = description;
    return this;
  }

   /**
   * An optional description of the campaign
   * @return description
  **/
  @ApiModelProperty(required = true, value = "An optional description of the campaign")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UpdateCampaignPutRequest rootManifestId(String rootManifestId) {
    this.rootManifestId = rootManifestId;
    return this;
  }

   /**
   * Get rootManifestId
   * @return rootManifestId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getRootManifestId() {
    return rootManifestId;
  }

  public void setRootManifestId(String rootManifestId) {
    this.rootManifestId = rootManifestId;
  }

  public UpdateCampaignPutRequest object(String object) {
    this.object = object;
    return this;
  }

   /**
   * The API resource entity
   * @return object
  **/
  @ApiModelProperty(required = true, value = "The API resource entity")
  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public UpdateCampaignPutRequest when(DateTime when) {
    this.when = when;
    return this;
  }

   /**
   * The scheduled start time for the update campaign
   * @return when
  **/
  @ApiModelProperty(required = true, value = "The scheduled start time for the update campaign")
  public DateTime getWhen() {
    return when;
  }

  public void setWhen(DateTime when) {
    this.when = when;
  }

  public UpdateCampaignPutRequest state(StateEnum state) {
    this.state = state;
    return this;
  }

   /**
   * The state of the campaign
   * @return state
  **/
  @ApiModelProperty(required = true, value = "The state of the campaign")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public UpdateCampaignPutRequest deviceFilter(String deviceFilter) {
    this.deviceFilter = deviceFilter;
    return this;
  }

   /**
   * The filter for the devices the campaign will target
   * @return deviceFilter
  **/
  @ApiModelProperty(required = true, value = "The filter for the devices the campaign will target")
  public String getDeviceFilter() {
    return deviceFilter;
  }

  public void setDeviceFilter(String deviceFilter) {
    this.deviceFilter = deviceFilter;
  }

  public UpdateCampaignPutRequest name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The campaign&#39;s name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "The campaign's name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCampaignPutRequest updateCampaignPutRequest = (UpdateCampaignPutRequest) o;
    return Objects.equals(this.description, updateCampaignPutRequest.description) &&
        Objects.equals(this.rootManifestId, updateCampaignPutRequest.rootManifestId) &&
        Objects.equals(this.object, updateCampaignPutRequest.object) &&
        Objects.equals(this.when, updateCampaignPutRequest.when) &&
        Objects.equals(this.state, updateCampaignPutRequest.state) &&
        Objects.equals(this.deviceFilter, updateCampaignPutRequest.deviceFilter) &&
        Objects.equals(this.name, updateCampaignPutRequest.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, rootManifestId, object, when, state, deviceFilter, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCampaignPutRequest {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    rootManifestId: ").append(toIndentedString(rootManifestId)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    when: ").append(toIndentedString(when)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    deviceFilter: ").append(toIndentedString(deviceFilter)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

