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
import com.arm.mbed.cloud.sdk.internal.iam.model.UserInfoResp;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * UserInfoRespList
 */

public class UserInfoRespList implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("after")
  private String after = null;

  @SerializedName("has_more")
  private Boolean hasMore = null;

  @SerializedName("total_count")
  private Integer totalCount = null;

  /**
   * Entity name: always &#39;list&#39;
   */
  @JsonAdapter(ObjectEnum.Adapter.class)
  public enum ObjectEnum {
    USER("user"),
    
    API_KEY("api-key"),
    
    GROUP("group"),
    
    ACCOUNT("account"),
    
    ACCOUNT_TEMPLATE("account-template"),
    
    TRUSTED_CERT("trusted-cert"),
    
    LIST("list"),
    
    ERROR("error");

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

  @SerializedName("limit")
  private Integer limit = null;

  @SerializedName("data")
  private List<UserInfoResp> data = new ArrayList<UserInfoResp>();

  /**
   * The order of the records to return based on creation time. Available values: ASC, DESC; by default ASC.
   */
  @JsonAdapter(OrderEnum.Adapter.class)
  public enum OrderEnum {
    ASC("ASC"),
    
    DESC("DESC");

    private String value;

    OrderEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static OrderEnum fromValue(String text) {
      for (OrderEnum b : OrderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<OrderEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final OrderEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public OrderEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return OrderEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("order")
  private OrderEnum order = null;

  public UserInfoRespList after(String after) {
    this.after = after;
    return this;
  }

   /**
   * The entity ID to fetch after the given one.
   * @return after
  **/
  @ApiModelProperty(value = "The entity ID to fetch after the given one.")
  public String getAfter() {
    return after;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public UserInfoRespList hasMore(Boolean hasMore) {
    this.hasMore = hasMore;
    return this;
  }

   /**
   * Flag indicating whether there is more results.
   * @return hasMore
  **/
  @ApiModelProperty(required = true, value = "Flag indicating whether there is more results.")
  public Boolean getHasMore() {
    return hasMore;
  }

  public void setHasMore(Boolean hasMore) {
    this.hasMore = hasMore;
  }

  public UserInfoRespList totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

   /**
   * The total number or records, if requested. It might be returned also for small lists.
   * @return totalCount
  **/
  @ApiModelProperty(required = true, value = "The total number or records, if requested. It might be returned also for small lists.")
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public UserInfoRespList object(ObjectEnum object) {
    this.object = object;
    return this;
  }

   /**
   * Entity name: always &#39;list&#39;
   * @return object
  **/
  @ApiModelProperty(required = true, value = "Entity name: always 'list'")
  public ObjectEnum getObject() {
    return object;
  }

  public void setObject(ObjectEnum object) {
    this.object = object;
  }

  public UserInfoRespList limit(Integer limit) {
    this.limit = limit;
    return this;
  }

   /**
   * The number of results to return, (range: 2-1000), or equals to &#x60;total_count&#x60;
   * @return limit
  **/
  @ApiModelProperty(required = true, value = "The number of results to return, (range: 2-1000), or equals to `total_count`")
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public UserInfoRespList data(List<UserInfoResp> data) {
    this.data = data;
    return this;
  }

  public UserInfoRespList addDataItem(UserInfoResp dataItem) {
    this.data.add(dataItem);
    return this;
  }

   /**
   * A list of entities.
   * @return data
  **/
  @ApiModelProperty(required = true, value = "A list of entities.")
  public List<UserInfoResp> getData() {
    return data;
  }

  public void setData(List<UserInfoResp> data) {
    this.data = data;
  }

  public UserInfoRespList order(OrderEnum order) {
    this.order = order;
    return this;
  }

   /**
   * The order of the records to return based on creation time. Available values: ASC, DESC; by default ASC.
   * @return order
  **/
  @ApiModelProperty(value = "The order of the records to return based on creation time. Available values: ASC, DESC; by default ASC.")
  public OrderEnum getOrder() {
    return order;
  }

  public void setOrder(OrderEnum order) {
    this.order = order;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfoRespList userInfoRespList = (UserInfoRespList) o;
    return Objects.equals(this.after, userInfoRespList.after) &&
        Objects.equals(this.hasMore, userInfoRespList.hasMore) &&
        Objects.equals(this.totalCount, userInfoRespList.totalCount) &&
        Objects.equals(this.object, userInfoRespList.object) &&
        Objects.equals(this.limit, userInfoRespList.limit) &&
        Objects.equals(this.data, userInfoRespList.data) &&
        Objects.equals(this.order, userInfoRespList.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(after, hasMore, totalCount, object, limit, data, order);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoRespList {\n");
    
    sb.append("    after: ").append(toIndentedString(after)).append("\n");
    sb.append("    hasMore: ").append(toIndentedString(hasMore)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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

