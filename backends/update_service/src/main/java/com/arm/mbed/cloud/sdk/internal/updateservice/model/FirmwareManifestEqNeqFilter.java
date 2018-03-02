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
 * FirmwareManifestEqNeqFilter
 */

public class FirmwareManifestEqNeqFilter implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("datafile")
  private String datafile = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("timestamp")
  private DateTime timestamp = null;

  @SerializedName("created_at")
  private DateTime createdAt = null;

  @SerializedName("updated_at")
  private DateTime updatedAt = null;

  @SerializedName("etag")
  private DateTime etag = null;

  @SerializedName("device_class")
  private String deviceClass = null;

  @SerializedName("datafile_size")
  private Integer datafileSize = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("name")
  private String name = null;

  public FirmwareManifestEqNeqFilter datafile(String datafile) {
    this.datafile = datafile;
    return this;
  }

   /**
   * Get datafile
   * @return datafile
  **/
  @ApiModelProperty(value = "")
  public String getDatafile() {
    return datafile;
  }

  public void setDatafile(String datafile) {
    this.datafile = datafile;
  }

  public FirmwareManifestEqNeqFilter description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public FirmwareManifestEqNeqFilter timestamp(DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @ApiModelProperty(value = "")
  public DateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(DateTime timestamp) {
    this.timestamp = timestamp;
  }

  public FirmwareManifestEqNeqFilter createdAt(DateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")
  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public FirmwareManifestEqNeqFilter updatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Get updatedAt
   * @return updatedAt
  **/
  @ApiModelProperty(value = "")
  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public FirmwareManifestEqNeqFilter etag(DateTime etag) {
    this.etag = etag;
    return this;
  }

   /**
   * Get etag
   * @return etag
  **/
  @ApiModelProperty(value = "")
  public DateTime getEtag() {
    return etag;
  }

  public void setEtag(DateTime etag) {
    this.etag = etag;
  }

  public FirmwareManifestEqNeqFilter deviceClass(String deviceClass) {
    this.deviceClass = deviceClass;
    return this;
  }

   /**
   * Get deviceClass
   * @return deviceClass
  **/
  @ApiModelProperty(value = "")
  public String getDeviceClass() {
    return deviceClass;
  }

  public void setDeviceClass(String deviceClass) {
    this.deviceClass = deviceClass;
  }

  public FirmwareManifestEqNeqFilter datafileSize(Integer datafileSize) {
    this.datafileSize = datafileSize;
    return this;
  }

   /**
   * Get datafileSize
   * @return datafileSize
  **/
  @ApiModelProperty(value = "")
  public Integer getDatafileSize() {
    return datafileSize;
  }

  public void setDatafileSize(Integer datafileSize) {
    this.datafileSize = datafileSize;
  }

  public FirmwareManifestEqNeqFilter id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public FirmwareManifestEqNeqFilter name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
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
    FirmwareManifestEqNeqFilter firmwareManifestEqNeqFilter = (FirmwareManifestEqNeqFilter) o;
    return Objects.equals(this.datafile, firmwareManifestEqNeqFilter.datafile) &&
        Objects.equals(this.description, firmwareManifestEqNeqFilter.description) &&
        Objects.equals(this.timestamp, firmwareManifestEqNeqFilter.timestamp) &&
        Objects.equals(this.createdAt, firmwareManifestEqNeqFilter.createdAt) &&
        Objects.equals(this.updatedAt, firmwareManifestEqNeqFilter.updatedAt) &&
        Objects.equals(this.etag, firmwareManifestEqNeqFilter.etag) &&
        Objects.equals(this.deviceClass, firmwareManifestEqNeqFilter.deviceClass) &&
        Objects.equals(this.datafileSize, firmwareManifestEqNeqFilter.datafileSize) &&
        Objects.equals(this.id, firmwareManifestEqNeqFilter.id) &&
        Objects.equals(this.name, firmwareManifestEqNeqFilter.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datafile, description, timestamp, createdAt, updatedAt, etag, deviceClass, datafileSize, id, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FirmwareManifestEqNeqFilter {\n");
    
    sb.append("    datafile: ").append(toIndentedString(datafile)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    etag: ").append(toIndentedString(etag)).append("\n");
    sb.append("    deviceClass: ").append(toIndentedString(deviceClass)).append("\n");
    sb.append("    datafileSize: ").append(toIndentedString(datafileSize)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
