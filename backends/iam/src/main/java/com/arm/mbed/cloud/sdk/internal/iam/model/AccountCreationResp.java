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
 * This object represents an account creation response.
 */
@ApiModel(description = "This object represents an account creation response.")

public class AccountCreationResp implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("address_line1")
  private String addressLine1 = null;

  @SerializedName("address_line2")
  private String addressLine2 = null;

  @SerializedName("admin_email")
  private String adminEmail = null;

  @SerializedName("admin_full_name")
  private String adminFullName = null;

  @SerializedName("admin_id")
  private String adminId = null;

  @SerializedName("admin_key")
  private String adminKey = null;

  @SerializedName("admin_name")
  private String adminName = null;

  @SerializedName("admin_password")
  private String adminPassword = null;

  @SerializedName("aliases")
  private List<String> aliases = null;

  @SerializedName("city")
  private String city = null;

  @SerializedName("company")
  private String company = null;

  @SerializedName("contact")
  private String contact = null;

  @SerializedName("contract_number")
  private String contractNumber = null;

  @SerializedName("country")
  private String country = null;

  @SerializedName("customer_number")
  private String customerNumber = null;

  @SerializedName("display_name")
  private String displayName = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("end_market")
  private String endMarket = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("phone_number")
  private String phoneNumber = null;

  @SerializedName("postal_code")
  private String postalCode = null;

  @SerializedName("state")
  private String state = null;

  public AccountCreationResp addressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

   /**
   * Postal address line 1, not longer than 100 characters. Required for commercial accounts only.
   * @return addressLine1
  **/
  @ApiModelProperty(example = "110 Fulbourn Rd", value = "Postal address line 1, not longer than 100 characters. Required for commercial accounts only.")
  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public AccountCreationResp addressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

   /**
   * Postal address line 2, not longer than 100 characters.
   * @return addressLine2
  **/
  @ApiModelProperty(example = " ", value = "Postal address line 2, not longer than 100 characters.")
  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public AccountCreationResp adminEmail(String adminEmail) {
    this.adminEmail = adminEmail;
    return this;
  }

   /**
   * The email address of the account admin, not longer than 254 characters.
   * @return adminEmail
  **/
  @ApiModelProperty(example = "admin@arm.com", value = "The email address of the account admin, not longer than 254 characters.")
  public String getAdminEmail() {
    return adminEmail;
  }

  public void setAdminEmail(String adminEmail) {
    this.adminEmail = adminEmail;
  }

  public AccountCreationResp adminFullName(String adminFullName) {
    this.adminFullName = adminFullName;
    return this;
  }

   /**
   * The full name of the admin user to be created.
   * @return adminFullName
  **/
  @ApiModelProperty(example = "Admin Doe", value = "The full name of the admin user to be created.")
  public String getAdminFullName() {
    return adminFullName;
  }

  public void setAdminFullName(String adminFullName) {
    this.adminFullName = adminFullName;
  }

  public AccountCreationResp adminId(String adminId) {
    this.adminId = adminId;
    return this;
  }

   /**
   * The ID of the admin user created.
   * @return adminId
  **/
  @ApiModelProperty(example = "01619571e2e89242ac12000600000000", required = true, value = "The ID of the admin user created.")
  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public AccountCreationResp adminKey(String adminKey) {
    this.adminKey = adminKey;
    return this;
  }

   /**
   * The admin API key created for the account.
   * @return adminKey
  **/
  @ApiModelProperty(example = "ak_1MDE2MTk1NzFmNmU4MDI0MmFjMTIwMDA2MDAwMDAwMDA01619571f7020242ac12000600000000B40IkJADMANmAscAj0Ot0n2yeQnyt9tT", value = "The admin API key created for the account.")
  public String getAdminKey() {
    return adminKey;
  }

  public void setAdminKey(String adminKey) {
    this.adminKey = adminKey;
  }

  public AccountCreationResp adminName(String adminName) {
    this.adminName = adminName;
    return this;
  }

   /**
   * The username of the admin user to be created, containing alphanumerical letters and -,._@+&#x3D; characters. It must be at least 4 but not more than 30 character long.
   * @return adminName
  **/
  @ApiModelProperty(example = "admin", value = "The username of the admin user to be created, containing alphanumerical letters and -,._@+= characters. It must be at least 4 but not more than 30 character long.")
  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }

  public AccountCreationResp adminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
    return this;
  }

   /**
   * The password when creating a new user. It will be generated when not present in the request.
   * @return adminPassword
  **/
  @ApiModelProperty(example = "PZf9eEUH43DAPE9ULINFeuj", value = "The password when creating a new user. It will be generated when not present in the request.")
  public String getAdminPassword() {
    return adminPassword;
  }

  public void setAdminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
  }

  public AccountCreationResp aliases(List<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  public AccountCreationResp addAliasesItem(String aliasesItem) {
    if (this.aliases == null) {
      this.aliases = new ArrayList<String>();
    }
    this.aliases.add(aliasesItem);
    return this;
  }

   /**
   * An array of aliases, not more than 10. An alias is not shorter than 8 and not longer than 100 characters.
   * @return aliases
  **/
  @ApiModelProperty(value = "An array of aliases, not more than 10. An alias is not shorter than 8 and not longer than 100 characters.")
  public List<String> getAliases() {
    return aliases;
  }

  public void setAliases(List<String> aliases) {
    this.aliases = aliases;
  }

  public AccountCreationResp city(String city) {
    this.city = city;
    return this;
  }

   /**
   * The city part of the postal address, not longer than 100 characters. Required for commercial accounts only.
   * @return city
  **/
  @ApiModelProperty(example = "Cambridge", value = "The city part of the postal address, not longer than 100 characters. Required for commercial accounts only.")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AccountCreationResp company(String company) {
    this.company = company;
    return this;
  }

   /**
   * The name of the company, not longer than 100 characters. Required for commercial accounts only.
   * @return company
  **/
  @ApiModelProperty(example = "ARM Holdings Plc", value = "The name of the company, not longer than 100 characters. Required for commercial accounts only.")
  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public AccountCreationResp contact(String contact) {
    this.contact = contact;
    return this;
  }

   /**
   * The name of the contact person for this account, not longer than 100 characters. Required for commercial accounts only.
   * @return contact
  **/
  @ApiModelProperty(example = "J. Doe", value = "The name of the contact person for this account, not longer than 100 characters. Required for commercial accounts only.")
  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public AccountCreationResp contractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
    return this;
  }

   /**
   * Contract number of the customer.
   * @return contractNumber
  **/
  @ApiModelProperty(example = "1NX25_0001", value = "Contract number of the customer.")
  public String getContractNumber() {
    return contractNumber;
  }

  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }

  public AccountCreationResp country(String country) {
    this.country = country;
    return this;
  }

   /**
   * The country part of the postal address, not longer than 100 characters. Required for commercial accounts only.
   * @return country
  **/
  @ApiModelProperty(example = "United Kingdom", value = "The country part of the postal address, not longer than 100 characters. Required for commercial accounts only.")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public AccountCreationResp customerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
    return this;
  }

   /**
   * Customer number of the customer.
   * @return customerNumber
  **/
  @ApiModelProperty(example = "1NC25_0001", value = "Customer number of the customer.")
  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public AccountCreationResp displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * The display name for the account, not longer than 100 characters.
   * @return displayName
  **/
  @ApiModelProperty(example = "ARM", value = "The display name for the account, not longer than 100 characters.")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public AccountCreationResp email(String email) {
    this.email = email;
    return this;
  }

   /**
   * The company email address for this account, not longer than 254 characters. Required for commercial accounts only.
   * @return email
  **/
  @ApiModelProperty(example = "info@arm.com", value = "The company email address for this account, not longer than 254 characters. Required for commercial accounts only.")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public AccountCreationResp endMarket(String endMarket) {
    this.endMarket = endMarket;
    return this;
  }

   /**
   * The end market of the account to be created.
   * @return endMarket
  **/
  @ApiModelProperty(example = "IT", required = true, value = "The end market of the account to be created.")
  public String getEndMarket() {
    return endMarket;
  }

  public void setEndMarket(String endMarket) {
    this.endMarket = endMarket;
  }

  public AccountCreationResp id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Account ID.
   * @return id
  **/
  @ApiModelProperty(example = "01619571e2e90242ac12000600000000", required = true, value = "Account ID.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AccountCreationResp phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

   /**
   * The phone number of a representative of the company, not longer than 100 characters.
   * @return phoneNumber
  **/
  @ApiModelProperty(example = "+44 (1223) 400 400", value = "The phone number of a representative of the company, not longer than 100 characters.")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public AccountCreationResp postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * The postal code part of the postal address, not longer than 100 characters.
   * @return postalCode
  **/
  @ApiModelProperty(example = "CB1 9NJ", value = "The postal code part of the postal address, not longer than 100 characters.")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public AccountCreationResp state(String state) {
    this.state = state;
    return this;
  }

   /**
   * The state part of the postal address, not longer than 100 characters.
   * @return state
  **/
  @ApiModelProperty(example = " ", value = "The state part of the postal address, not longer than 100 characters.")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountCreationResp accountCreationResp = (AccountCreationResp) o;
    return Objects.equals(this.addressLine1, accountCreationResp.addressLine1) &&
        Objects.equals(this.addressLine2, accountCreationResp.addressLine2) &&
        Objects.equals(this.adminEmail, accountCreationResp.adminEmail) &&
        Objects.equals(this.adminFullName, accountCreationResp.adminFullName) &&
        Objects.equals(this.adminId, accountCreationResp.adminId) &&
        Objects.equals(this.adminKey, accountCreationResp.adminKey) &&
        Objects.equals(this.adminName, accountCreationResp.adminName) &&
        Objects.equals(this.adminPassword, accountCreationResp.adminPassword) &&
        Objects.equals(this.aliases, accountCreationResp.aliases) &&
        Objects.equals(this.city, accountCreationResp.city) &&
        Objects.equals(this.company, accountCreationResp.company) &&
        Objects.equals(this.contact, accountCreationResp.contact) &&
        Objects.equals(this.contractNumber, accountCreationResp.contractNumber) &&
        Objects.equals(this.country, accountCreationResp.country) &&
        Objects.equals(this.customerNumber, accountCreationResp.customerNumber) &&
        Objects.equals(this.displayName, accountCreationResp.displayName) &&
        Objects.equals(this.email, accountCreationResp.email) &&
        Objects.equals(this.endMarket, accountCreationResp.endMarket) &&
        Objects.equals(this.id, accountCreationResp.id) &&
        Objects.equals(this.phoneNumber, accountCreationResp.phoneNumber) &&
        Objects.equals(this.postalCode, accountCreationResp.postalCode) &&
        Objects.equals(this.state, accountCreationResp.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine1, addressLine2, adminEmail, adminFullName, adminId, adminKey, adminName, adminPassword, aliases, city, company, contact, contractNumber, country, customerNumber, displayName, email, endMarket, id, phoneNumber, postalCode, state);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountCreationResp {\n");
    
    sb.append("    addressLine1: ").append(toIndentedString(addressLine1)).append("\n");
    sb.append("    addressLine2: ").append(toIndentedString(addressLine2)).append("\n");
    sb.append("    adminEmail: ").append(toIndentedString(adminEmail)).append("\n");
    sb.append("    adminFullName: ").append(toIndentedString(adminFullName)).append("\n");
    sb.append("    adminId: ").append(toIndentedString(adminId)).append("\n");
    sb.append("    adminKey: ").append(toIndentedString(adminKey)).append("\n");
    sb.append("    adminName: ").append(toIndentedString(adminName)).append("\n");
    sb.append("    adminPassword: ").append(toIndentedString(adminPassword)).append("\n");
    sb.append("    aliases: ").append(toIndentedString(aliases)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    company: ").append(toIndentedString(company)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    contractNumber: ").append(toIndentedString(contractNumber)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    customerNumber: ").append(toIndentedString(customerNumber)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    endMarket: ").append(toIndentedString(endMarket)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

