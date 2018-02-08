/*
 * Connect CA API
 * Connect CA API provides methods to create and get Developer certificate. Also Connect CA provides server-credentials for Bootstarp and LWM2M Server.
 *
 * OpenAPI spec version: 3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.arm.mbed.cloud.sdk.internal.connectorca.model;

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
 * DeveloperCertificateResponseData
 */

public class DeveloperCertificateResponseData implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("security_file_content")
  private String securityFileContent = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("developer_certificate")
  private String developerCertificate = null;

  @SerializedName("server_uri")
  private String serverUri = null;

  @SerializedName("created_at")
  private String createdAt = null;

  @SerializedName("object")
  private String object = null;

  @SerializedName("developer_private_key")
  private String developerPrivateKey = null;

  @SerializedName("server_certificate")
  private String serverCertificate = null;

  @SerializedName("etag")
  private String etag = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("account_id")
  private String accountId = null;

  public DeveloperCertificateResponseData securityFileContent(String securityFileContent) {
    this.securityFileContent = securityFileContent;
    return this;
  }

   /**
   * The content of the &#x60;security.c&#x60; file that is flashed into the device to provide the security credentials
   * @return securityFileContent
  **/
  @ApiModelProperty(value = "The content of the `security.c` file that is flashed into the device to provide the security credentials")
  public String getSecurityFileContent() {
    return securityFileContent;
  }

  public void setSecurityFileContent(String securityFileContent) {
    this.securityFileContent = securityFileContent;
  }

  public DeveloperCertificateResponseData description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description for the developer certificate.
   * @return description
  **/
  @ApiModelProperty(value = "Description for the developer certificate.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DeveloperCertificateResponseData name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the developer certificate.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the developer certificate.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeveloperCertificateResponseData developerCertificate(String developerCertificate) {
    this.developerCertificate = developerCertificate;
    return this;
  }

   /**
   * The PEM format X.509 developer certificate.
   * @return developerCertificate
  **/
  @ApiModelProperty(value = "The PEM format X.509 developer certificate.")
  public String getDeveloperCertificate() {
    return developerCertificate;
  }

  public void setDeveloperCertificate(String developerCertificate) {
    this.developerCertificate = developerCertificate;
  }

  public DeveloperCertificateResponseData serverUri(String serverUri) {
    this.serverUri = serverUri;
    return this;
  }

   /**
   * The URI to which the client needs to connect to.
   * @return serverUri
  **/
  @ApiModelProperty(value = "The URI to which the client needs to connect to.")
  public String getServerUri() {
    return serverUri;
  }

  public void setServerUri(String serverUri) {
    this.serverUri = serverUri;
  }

  public DeveloperCertificateResponseData createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Creation UTC time RFC3339.
   * @return createdAt
  **/
  @ApiModelProperty(value = "Creation UTC time RFC3339.")
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public DeveloperCertificateResponseData object(String object) {
    this.object = object;
    return this;
  }

   /**
   * Entity name, always &#x60;trusted-cert&#x60;.
   * @return object
  **/
  @ApiModelProperty(value = "Entity name, always `trusted-cert`.")
  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public DeveloperCertificateResponseData developerPrivateKey(String developerPrivateKey) {
    this.developerPrivateKey = developerPrivateKey;
    return this;
  }

   /**
   * The PEM format developer private key associated to the certificate.
   * @return developerPrivateKey
  **/
  @ApiModelProperty(value = "The PEM format developer private key associated to the certificate.")
  public String getDeveloperPrivateKey() {
    return developerPrivateKey;
  }

  public void setDeveloperPrivateKey(String developerPrivateKey) {
    this.developerPrivateKey = developerPrivateKey;
  }

  public DeveloperCertificateResponseData serverCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
    return this;
  }

   /**
   * The PEM format X.509 server certificate that is used to validate the server certificate that is received during the TLS/DTLS handshake.
   * @return serverCertificate
  **/
  @ApiModelProperty(value = "The PEM format X.509 server certificate that is used to validate the server certificate that is received during the TLS/DTLS handshake.")
  public String getServerCertificate() {
    return serverCertificate;
  }

  public void setServerCertificate(String serverCertificate) {
    this.serverCertificate = serverCertificate;
  }

  public DeveloperCertificateResponseData etag(String etag) {
    this.etag = etag;
    return this;
  }

   /**
   * API resource entity version.
   * @return etag
  **/
  @ApiModelProperty(value = "API resource entity version.")
  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public DeveloperCertificateResponseData id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The mUUID that uniquely identifies the developer certificate.
   * @return id
  **/
  @ApiModelProperty(value = "The mUUID that uniquely identifies the developer certificate.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DeveloperCertificateResponseData accountId(String accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * The account to which the developer certificate belongs.
   * @return accountId
  **/
  @ApiModelProperty(value = "The account to which the developer certificate belongs.")
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeveloperCertificateResponseData developerCertificateResponseData = (DeveloperCertificateResponseData) o;
    return Objects.equals(this.securityFileContent, developerCertificateResponseData.securityFileContent) &&
        Objects.equals(this.description, developerCertificateResponseData.description) &&
        Objects.equals(this.name, developerCertificateResponseData.name) &&
        Objects.equals(this.developerCertificate, developerCertificateResponseData.developerCertificate) &&
        Objects.equals(this.serverUri, developerCertificateResponseData.serverUri) &&
        Objects.equals(this.createdAt, developerCertificateResponseData.createdAt) &&
        Objects.equals(this.object, developerCertificateResponseData.object) &&
        Objects.equals(this.developerPrivateKey, developerCertificateResponseData.developerPrivateKey) &&
        Objects.equals(this.serverCertificate, developerCertificateResponseData.serverCertificate) &&
        Objects.equals(this.etag, developerCertificateResponseData.etag) &&
        Objects.equals(this.id, developerCertificateResponseData.id) &&
        Objects.equals(this.accountId, developerCertificateResponseData.accountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(securityFileContent, description, name, developerCertificate, serverUri, createdAt, object, developerPrivateKey, serverCertificate, etag, id, accountId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeveloperCertificateResponseData {\n");
    
    sb.append("    securityFileContent: ").append(toIndentedString(securityFileContent)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    developerCertificate: ").append(toIndentedString(developerCertificate)).append("\n");
    sb.append("    serverUri: ").append(toIndentedString(serverUri)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    developerPrivateKey: ").append(toIndentedString(developerPrivateKey)).append("\n");
    sb.append("    serverCertificate: ").append(toIndentedString(serverCertificate)).append("\n");
    sb.append("    etag: ").append(toIndentedString(etag)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
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

