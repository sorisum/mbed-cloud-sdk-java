/*
 * billing REST API documentation
 * This document contains the public REST API definitions of the mbed-billing service.
 *
 * OpenAPI spec version: 1.4.4
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.billing.model;

import java.util.Objects;
import com.arm.mbed.cloud.sdk.internal.billing.model.QuotaUsageReport;
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
 * SubtenantServicePackageReport
 */

public class SubtenantServicePackageReport implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("quota_usage")
  private List<QuotaUsageReport> quotaUsage = new ArrayList<QuotaUsageReport>();

  public SubtenantServicePackageReport quotaUsage(List<QuotaUsageReport> quotaUsage) {
    this.quotaUsage = quotaUsage;
    return this;
  }

  public SubtenantServicePackageReport addQuotaUsageItem(QuotaUsageReport quotaUsageItem) {
    this.quotaUsage.add(quotaUsageItem);
    return this;
  }

   /**
   * Get quotaUsage
   * @return quotaUsage
  **/
  @ApiModelProperty(required = true, value = "")
  public List<QuotaUsageReport> getQuotaUsage() {
    return quotaUsage;
  }

  public void setQuotaUsage(List<QuotaUsageReport> quotaUsage) {
    this.quotaUsage = quotaUsage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubtenantServicePackageReport subtenantServicePackageReport = (SubtenantServicePackageReport) o;
    return Objects.equals(this.quotaUsage, subtenantServicePackageReport.quotaUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quotaUsage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubtenantServicePackageReport {\n");
    
    sb.append("    quotaUsage: ").append(toIndentedString(quotaUsage)).append("\n");
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
