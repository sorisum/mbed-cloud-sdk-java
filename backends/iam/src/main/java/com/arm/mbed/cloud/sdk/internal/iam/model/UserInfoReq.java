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
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * This object represents a user in requests towards Device Management.
 */
@ApiModel(description = "This object represents a user in requests towards Device Management.")

public class UserInfoReq implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("address")
  private String address = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("full_name")
  private String fullName = null;

  @SerializedName("groups")
  private List<String> groups = null;

  @SerializedName("is_gtc_accepted")
  private Boolean isGtcAccepted = null;

  @SerializedName("is_marketing_accepted")
  private Boolean isMarketingAccepted = null;

  @SerializedName("login_profiles")
  private List<String> loginProfiles = null;

  @SerializedName("password")
  private String password = null;

  @SerializedName("phone_number")
  private String phoneNumber = null;

  @SerializedName("username")
  private String username = null;

  public UserInfoReq address(String address) {
    this.address = address;
    return this;
  }

   /**
   * Address, not longer than 100 characters.
   * @return address
  **/
  @ApiModelProperty(value = "Address, not longer than 100 characters.")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public UserInfoReq email(String email) {
    this.email = email;
    return this;
  }

   /**
   * The email address, not longer than 254 characters.
   * @return email
  **/
  @ApiModelProperty(required = true, value = "The email address, not longer than 254 characters.")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserInfoReq fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

   /**
   * The full name of the user, not longer than 100 characters.
   * @return fullName
  **/
  @ApiModelProperty(value = "The full name of the user, not longer than 100 characters.")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserInfoReq groups(List<String> groups) {
    this.groups = groups;
    return this;
  }

  public UserInfoReq addGroupsItem(String groupsItem) {
    if (this.groups == null) {
      this.groups = new ArrayList<String>();
    }
    this.groups.add(groupsItem);
    return this;
  }

   /**
   * A list of IDs of the groups this user belongs to.
   * @return groups
  **/
  @ApiModelProperty(value = "A list of IDs of the groups this user belongs to.")
  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  public UserInfoReq isGtcAccepted(Boolean isGtcAccepted) {
    this.isGtcAccepted = isGtcAccepted;
    return this;
  }

   /**
   * A flag indicating that the General Terms and Conditions has been accepted.
   * @return isGtcAccepted
  **/
  @ApiModelProperty(value = "A flag indicating that the General Terms and Conditions has been accepted.")
  public Boolean isIsGtcAccepted() {
    return isGtcAccepted;
  }

  public void setIsGtcAccepted(Boolean isGtcAccepted) {
    this.isGtcAccepted = isGtcAccepted;
  }

  public UserInfoReq isMarketingAccepted(Boolean isMarketingAccepted) {
    this.isMarketingAccepted = isMarketingAccepted;
    return this;
  }

   /**
   * A flag indicating that receiving marketing information has been accepted.
   * @return isMarketingAccepted
  **/
  @ApiModelProperty(value = "A flag indicating that receiving marketing information has been accepted.")
  public Boolean isIsMarketingAccepted() {
    return isMarketingAccepted;
  }

  public void setIsMarketingAccepted(Boolean isMarketingAccepted) {
    this.isMarketingAccepted = isMarketingAccepted;
  }

  public UserInfoReq loginProfiles(List<String> loginProfiles) {
    this.loginProfiles = loginProfiles;
    return this;
  }

  public UserInfoReq addLoginProfilesItem(String loginProfilesItem) {
    if (this.loginProfiles == null) {
      this.loginProfiles = new ArrayList<String>();
    }
    this.loginProfiles.add(loginProfilesItem);
    return this;
  }

   /**
   * A list of login profiles for the user. Specified as IDs of the identity providers the user should be associated with. The list cannot be empty. A limit of 100 profiles.
   * @return loginProfiles
  **/
  @ApiModelProperty(value = "A list of login profiles for the user. Specified as IDs of the identity providers the user should be associated with. The list cannot be empty. A limit of 100 profiles.")
  public List<String> getLoginProfiles() {
    return loginProfiles;
  }

  public void setLoginProfiles(List<String> loginProfiles) {
    this.loginProfiles = loginProfiles;
  }

  public UserInfoReq password(String password) {
    this.password = password;
    return this;
  }

   /**
   * The password when creating a new user. It will be generated when not present in the request.
   * @return password
  **/
  @ApiModelProperty(value = "The password when creating a new user. It will be generated when not present in the request.")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserInfoReq phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

   /**
   * Phone number, not longer than 100 characters.
   * @return phoneNumber
  **/
  @ApiModelProperty(value = "Phone number, not longer than 100 characters.")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public UserInfoReq username(String username) {
    this.username = username;
    return this;
  }

   /**
   * A username containing alphanumerical letters and -,._@+&#x3D; characters. It must be at least 4 but not more than 30 character long.
   * @return username
  **/
  @ApiModelProperty(value = "A username containing alphanumerical letters and -,._@+= characters. It must be at least 4 but not more than 30 character long.")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfoReq userInfoReq = (UserInfoReq) o;
    return Objects.equals(this.address, userInfoReq.address) &&
        Objects.equals(this.email, userInfoReq.email) &&
        Objects.equals(this.fullName, userInfoReq.fullName) &&
        Objects.equals(this.groups, userInfoReq.groups) &&
        Objects.equals(this.isGtcAccepted, userInfoReq.isGtcAccepted) &&
        Objects.equals(this.isMarketingAccepted, userInfoReq.isMarketingAccepted) &&
        Objects.equals(this.loginProfiles, userInfoReq.loginProfiles) &&
        Objects.equals(this.password, userInfoReq.password) &&
        Objects.equals(this.phoneNumber, userInfoReq.phoneNumber) &&
        Objects.equals(this.username, userInfoReq.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, email, fullName, groups, isGtcAccepted, isMarketingAccepted, loginProfiles, password, phoneNumber, username);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoReq {\n");
    
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    isGtcAccepted: ").append(toIndentedString(isGtcAccepted)).append("\n");
    sb.append("    isMarketingAccepted: ").append(toIndentedString(isMarketingAccepted)).append("\n");
    sb.append("    loginProfiles: ").append(toIndentedString(loginProfiles)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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

