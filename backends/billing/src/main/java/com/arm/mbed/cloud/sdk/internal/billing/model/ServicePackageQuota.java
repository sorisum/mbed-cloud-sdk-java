/*
 * billing REST API documentation
 * This document contains the public REST API definitions of the mbed-billing service.
 *
 * OpenAPI spec version: 1.4.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.billing.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.io.Serializable;

/**
 * Quota of the service package.
 */
@ApiModel(description = "Quota of the service package.")

public class ServicePackageQuota implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Always set to &#39;service-package-quota&#39;.
   */
  @JsonAdapter(ObjectEnum.Adapter.class)
  public enum ObjectEnum {
    QUOTA("service-package-quota");

    private String value;

    ObjectEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ObjectEnum fromValue(String text) {
      for (ObjectEnum b : ObjectEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ObjectEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ObjectEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ObjectEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ObjectEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("object")
  private ObjectEnum object = null;

  @SerializedName("quota")
  private Long quota = null;

  public ServicePackageQuota object(ObjectEnum object) {
    this.object = object;
    return this;
  }

   /**
   * Always set to &#39;service-package-quota&#39;.
   * @return object
  **/
  @ApiModelProperty(required = true, value = "Always set to 'service-package-quota'.")
  public ObjectEnum getObject() {
    return object;
  }

  public void setObject(ObjectEnum object) {
    this.object = object;
  }

  public ServicePackageQuota quota(Long quota) {
    this.quota = quota;
    return this;
  }

   /**
   * Available quota for the service package
   * minimum: 0
   * @return quota
  **/
  @ApiModelProperty(required = true, value = "Available quota for the service package")
  public Long getQuota() {
    return quota;
  }

  public void setQuota(Long quota) {
    this.quota = quota;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServicePackageQuota servicePackageQuota = (ServicePackageQuota) o;
    return Objects.equals(this.object, servicePackageQuota.object) &&
        Objects.equals(this.quota, servicePackageQuota.quota);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object, quota);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServicePackageQuota {\n");
    
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    quota: ").append(toIndentedString(quota)).append("\n");
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

