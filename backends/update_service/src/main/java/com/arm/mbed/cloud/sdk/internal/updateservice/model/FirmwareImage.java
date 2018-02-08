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
 * FirmwareImage
 */

public class FirmwareImage implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("datafile")
  private String datafile = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("created_at")
  private DateTime createdAt = null;

  @SerializedName("object")
  private String object = null;

  @SerializedName("updated_at")
  private DateTime updatedAt = null;

  @SerializedName("etag")
  private DateTime etag = null;

  @SerializedName("datafile_checksum")
  private String datafileChecksum = null;

  @SerializedName("datafile_size")
  private Long datafileSize = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("name")
  private String name = null;

  public FirmwareImage datafile(String datafile) {
    this.datafile = datafile;
    return this;
  }

   /**
   * The firmware image file URL
   * @return datafile
  **/
  @ApiModelProperty(required = true, value = "The firmware image file URL")
  public String getDatafile() {
    return datafile;
  }

  public void setDatafile(String datafile) {
    this.datafile = datafile;
  }

  public FirmwareImage description(String description) {
    this.description = description;
    return this;
  }

   /**
   * The description of the object
   * @return description
  **/
  @ApiModelProperty(required = true, value = "The description of the object")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public FirmwareImage createdAt(DateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * The time the object was created
   * @return createdAt
  **/
  @ApiModelProperty(required = true, value = "The time the object was created")
  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public FirmwareImage object(String object) {
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

  public FirmwareImage updatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * The time the object was updated
   * @return updatedAt
  **/
  @ApiModelProperty(required = true, value = "The time the object was updated")
  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public FirmwareImage etag(DateTime etag) {
    this.etag = etag;
    return this;
  }

   /**
   * The entity instance signature
   * @return etag
  **/
  @ApiModelProperty(required = true, value = "The entity instance signature")
  public DateTime getEtag() {
    return etag;
  }

  public void setEtag(DateTime etag) {
    this.etag = etag;
  }

  public FirmwareImage datafileChecksum(String datafileChecksum) {
    this.datafileChecksum = datafileChecksum;
    return this;
  }

   /**
   * Checksum (sha256) generated for the datafile
   * @return datafileChecksum
  **/
  @ApiModelProperty(required = true, value = "Checksum (sha256) generated for the datafile")
  public String getDatafileChecksum() {
    return datafileChecksum;
  }

  public void setDatafileChecksum(String datafileChecksum) {
    this.datafileChecksum = datafileChecksum;
  }

  public FirmwareImage datafileSize(Long datafileSize) {
    this.datafileSize = datafileSize;
    return this;
  }

   /**
   * Size of the datafile in bytes
   * @return datafileSize
  **/
  @ApiModelProperty(value = "Size of the datafile in bytes")
  public Long getDatafileSize() {
    return datafileSize;
  }

  public void setDatafileSize(Long datafileSize) {
    this.datafileSize = datafileSize;
  }

  public FirmwareImage id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The firmware image ID
   * @return id
  **/
  @ApiModelProperty(required = true, value = "The firmware image ID")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public FirmwareImage name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The firmware image name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "The firmware image name")
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
    FirmwareImage firmwareImage = (FirmwareImage) o;
    return Objects.equals(this.datafile, firmwareImage.datafile) &&
        Objects.equals(this.description, firmwareImage.description) &&
        Objects.equals(this.createdAt, firmwareImage.createdAt) &&
        Objects.equals(this.object, firmwareImage.object) &&
        Objects.equals(this.updatedAt, firmwareImage.updatedAt) &&
        Objects.equals(this.etag, firmwareImage.etag) &&
        Objects.equals(this.datafileChecksum, firmwareImage.datafileChecksum) &&
        Objects.equals(this.datafileSize, firmwareImage.datafileSize) &&
        Objects.equals(this.id, firmwareImage.id) &&
        Objects.equals(this.name, firmwareImage.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datafile, description, createdAt, object, updatedAt, etag, datafileChecksum, datafileSize, id, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FirmwareImage {\n");
    
    sb.append("    datafile: ").append(toIndentedString(datafile)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    etag: ").append(toIndentedString(etag)).append("\n");
    sb.append("    datafileChecksum: ").append(toIndentedString(datafileChecksum)).append("\n");
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

