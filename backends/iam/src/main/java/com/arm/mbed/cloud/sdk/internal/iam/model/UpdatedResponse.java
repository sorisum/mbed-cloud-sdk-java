/*
 * Account Management API
 * API for managing accounts, users, creating API keys, uploading trusted certificates
 *
 * OpenAPI spec version: v3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.iam.model;

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
 * This object represents a response to an update request.
 */
@ApiModel(description = "This object represents a response to an update request.")

public class UpdatedResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("code")
  private Integer code = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("message")
  private String message = null;

  /**
   * Entity name: &#39;user&#39;, &#39;api-key&#39;, &#39;group&#39;, &#39;policy&#39; or &#39;account&#39;.
   */
  @JsonAdapter(ObjectEnum.Adapter.class)
  public enum ObjectEnum {
    USER("user"),
    
    API_KEY("api-key"),
    
    GROUP("group"),
    
    POLICY("policy"),
    
    ACCOUNT("account");

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

  @SerializedName("request_id")
  private String requestId = null;

  /**
   * Response type: success.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    SUCCESS("success");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("type")
  private TypeEnum type = null;

  public UpdatedResponse code(Integer code) {
    this.code = code;
    return this;
  }

   /**
   * Response code.
   * @return code
  **/
  @ApiModelProperty(example = "200", required = true, value = "Response code.")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public UpdatedResponse id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Entity ID.
   * @return id
  **/
  @ApiModelProperty(example = "01619571dad80242ac12000600000000", required = true, value = "Entity ID.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UpdatedResponse message(String message) {
    this.message = message;
    return this;
  }

   /**
   * A human readable message with detailed info.
   * @return message
  **/
  @ApiModelProperty(example = "success", required = true, value = "A human readable message with detailed info.")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public UpdatedResponse object(ObjectEnum object) {
    this.object = object;
    return this;
  }

   /**
   * Entity name: &#39;user&#39;, &#39;api-key&#39;, &#39;group&#39;, &#39;policy&#39; or &#39;account&#39;.
   * @return object
  **/
  @ApiModelProperty(required = true, value = "Entity name: 'user', 'api-key', 'group', 'policy' or 'account'.")
  public ObjectEnum getObject() {
    return object;
  }

  public void setObject(ObjectEnum object) {
    this.object = object;
  }

  public UpdatedResponse requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

   /**
   * Request ID.
   * @return requestId
  **/
  @ApiModelProperty(example = "0161991d63150242ac12000600000000", required = true, value = "Request ID.")
  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public UpdatedResponse type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Response type: success.
   * @return type
  **/
  @ApiModelProperty(required = true, value = "Response type: success.")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdatedResponse updatedResponse = (UpdatedResponse) o;
    return Objects.equals(this.code, updatedResponse.code) &&
        Objects.equals(this.id, updatedResponse.id) &&
        Objects.equals(this.message, updatedResponse.message) &&
        Objects.equals(this.object, updatedResponse.object) &&
        Objects.equals(this.requestId, updatedResponse.requestId) &&
        Objects.equals(this.type, updatedResponse.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, id, message, object, requestId, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdatedResponse {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

