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
import org.joda.time.DateTime;
import java.io.Serializable;

/**
 * This object represents a trusted certificate in responses.
 */
@ApiModel(description = "This object represents a trusted certificate in responses.")

public class TrustedCertificateInternalResp implements Serializable {
  private static final long serialVersionUID = 1L;

  @SerializedName("account_id")
  private String accountId = null;

  @SerializedName("certificate")
  private String certificate = null;

  @SerializedName("created_at")
  private DateTime createdAt = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("device_execution_mode")
  private Integer deviceExecutionMode = null;

  @SerializedName("enrollment_mode")
  private Boolean enrollmentMode = null;

  @SerializedName("etag")
  private String etag = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("issuer")
  private String issuer = null;

  @SerializedName("name")
  private String name = null;

  /**
   * Entity name: always &#39;trusted-cert&#39;
   */
  @JsonAdapter(ObjectEnum.Adapter.class)
  public enum ObjectEnum {
    CERT("trusted-cert");

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

  @SerializedName("owner_id")
  private String ownerId = null;

  @SerializedName("private_key")
  private String privateKey = null;

  /**
   * Service name where the certificate is to be used.
   */
  @JsonAdapter(ServiceEnum.Adapter.class)
  public enum ServiceEnum {
    LWM2M("lwm2m"),
    
    BOOTSTRAP("bootstrap");

    private String value;

    ServiceEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ServiceEnum fromValue(String text) {
      for (ServiceEnum b : ServiceEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ServiceEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ServiceEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ServiceEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ServiceEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("service")
  private ServiceEnum service = null;

  /**
   * Status of the certificate.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    INACTIVE("INACTIVE");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("subject")
  private String subject = null;

  @SerializedName("updated_at")
  private DateTime updatedAt = null;

  @SerializedName("validity")
  private DateTime validity = null;

  public TrustedCertificateInternalResp accountId(String accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * The UUID of the account.
   * @return accountId
  **/
  @ApiModelProperty(example = "01619571e2e90242ac12000600000000", required = true, value = "The UUID of the account.")
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public TrustedCertificateInternalResp certificate(String certificate) {
    this.certificate = certificate;
    return this;
  }

   /**
   * X509.v3 trusted certificate in PEM format.
   * @return certificate
  **/
  @ApiModelProperty(example = "-----BEGIN CERTIFICATE----- ... -----END CERTIFICATE-----", required = true, value = "X509.v3 trusted certificate in PEM format.")
  public String getCertificate() {
    return certificate;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  public TrustedCertificateInternalResp createdAt(DateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Creation UTC time RFC3339.
   * @return createdAt
  **/
  @ApiModelProperty(example = "2018-02-13T09:35:20Z", value = "Creation UTC time RFC3339.")
  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public TrustedCertificateInternalResp description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Human readable description of this certificate.
   * @return description
  **/
  @ApiModelProperty(example = "Certificate created by me.", value = "Human readable description of this certificate.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TrustedCertificateInternalResp deviceExecutionMode(Integer deviceExecutionMode) {
    this.deviceExecutionMode = deviceExecutionMode;
    return this;
  }

   /**
   * Device execution mode where 1 means a developer certificate.
   * @return deviceExecutionMode
  **/
  @ApiModelProperty(example = "1", value = "Device execution mode where 1 means a developer certificate.")
  public Integer getDeviceExecutionMode() {
    return deviceExecutionMode;
  }

  public void setDeviceExecutionMode(Integer deviceExecutionMode) {
    this.deviceExecutionMode = deviceExecutionMode;
  }

  public TrustedCertificateInternalResp enrollmentMode(Boolean enrollmentMode) {
    this.enrollmentMode = enrollmentMode;
    return this;
  }

   /**
   * If true, signature is not required. Default value false.
   * @return enrollmentMode
  **/
  @ApiModelProperty(example = "false", value = "If true, signature is not required. Default value false.")
  public Boolean isEnrollmentMode() {
    return enrollmentMode;
  }

  public void setEnrollmentMode(Boolean enrollmentMode) {
    this.enrollmentMode = enrollmentMode;
  }

  public TrustedCertificateInternalResp etag(String etag) {
    this.etag = etag;
    return this;
  }

   /**
   * API resource entity version.
   * @return etag
  **/
  @ApiModelProperty(example = "1", required = true, value = "API resource entity version.")
  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public TrustedCertificateInternalResp id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Entity ID.
   * @return id
  **/
  @ApiModelProperty(example = "01619571d01d0242ac12000600000000", required = true, value = "Entity ID.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TrustedCertificateInternalResp issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }

   /**
   * Issuer of the certificate.
   * @return issuer
  **/
  @ApiModelProperty(example = "CN=issuer", required = true, value = "Issuer of the certificate.")
  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public TrustedCertificateInternalResp name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Certificate name.
   * @return name
  **/
  @ApiModelProperty(example = "My certificate", required = true, value = "Certificate name.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TrustedCertificateInternalResp object(ObjectEnum object) {
    this.object = object;
    return this;
  }

   /**
   * Entity name: always &#39;trusted-cert&#39;
   * @return object
  **/
  @ApiModelProperty(required = true, value = "Entity name: always 'trusted-cert'")
  public ObjectEnum getObject() {
    return object;
  }

  public void setObject(ObjectEnum object) {
    this.object = object;
  }

  public TrustedCertificateInternalResp ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

   /**
   * The UUID of the owner.
   * @return ownerId
  **/
  @ApiModelProperty(example = "01619571dad80242ac12000600000000", value = "The UUID of the owner.")
  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public TrustedCertificateInternalResp privateKey(String privateKey) {
    this.privateKey = privateKey;
    return this;
  }

   /**
   * Private key of the certificate in PEM or base64 encoded DER format.
   * @return privateKey
  **/
  @ApiModelProperty(example = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmE...", required = true, value = "Private key of the certificate in PEM or base64 encoded DER format.")
  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public TrustedCertificateInternalResp service(ServiceEnum service) {
    this.service = service;
    return this;
  }

   /**
   * Service name where the certificate is to be used.
   * @return service
  **/
  @ApiModelProperty(required = true, value = "Service name where the certificate is to be used.")
  public ServiceEnum getService() {
    return service;
  }

  public void setService(ServiceEnum service) {
    this.service = service;
  }

  public TrustedCertificateInternalResp status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the certificate.
   * @return status
  **/
  @ApiModelProperty(example = "ACTIVE", value = "Status of the certificate.")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public TrustedCertificateInternalResp subject(String subject) {
    this.subject = subject;
    return this;
  }

   /**
   * Subject of the certificate.
   * @return subject
  **/
  @ApiModelProperty(example = "CN=subject", required = true, value = "Subject of the certificate.")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public TrustedCertificateInternalResp updatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Last update UTC time RFC3339.
   * @return updatedAt
  **/
  @ApiModelProperty(example = "2018-02-14T15:24:14Z", value = "Last update UTC time RFC3339.")
  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public TrustedCertificateInternalResp validity(DateTime validity) {
    this.validity = validity;
    return this;
  }

   /**
   * Expiration time in UTC formatted as RFC3339.
   * @return validity
  **/
  @ApiModelProperty(example = "2038-02-14T15:24:14Z", required = true, value = "Expiration time in UTC formatted as RFC3339.")
  public DateTime getValidity() {
    return validity;
  }

  public void setValidity(DateTime validity) {
    this.validity = validity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrustedCertificateInternalResp trustedCertificateInternalResp = (TrustedCertificateInternalResp) o;
    return Objects.equals(this.accountId, trustedCertificateInternalResp.accountId) &&
        Objects.equals(this.certificate, trustedCertificateInternalResp.certificate) &&
        Objects.equals(this.createdAt, trustedCertificateInternalResp.createdAt) &&
        Objects.equals(this.description, trustedCertificateInternalResp.description) &&
        Objects.equals(this.deviceExecutionMode, trustedCertificateInternalResp.deviceExecutionMode) &&
        Objects.equals(this.enrollmentMode, trustedCertificateInternalResp.enrollmentMode) &&
        Objects.equals(this.etag, trustedCertificateInternalResp.etag) &&
        Objects.equals(this.id, trustedCertificateInternalResp.id) &&
        Objects.equals(this.issuer, trustedCertificateInternalResp.issuer) &&
        Objects.equals(this.name, trustedCertificateInternalResp.name) &&
        Objects.equals(this.object, trustedCertificateInternalResp.object) &&
        Objects.equals(this.ownerId, trustedCertificateInternalResp.ownerId) &&
        Objects.equals(this.privateKey, trustedCertificateInternalResp.privateKey) &&
        Objects.equals(this.service, trustedCertificateInternalResp.service) &&
        Objects.equals(this.status, trustedCertificateInternalResp.status) &&
        Objects.equals(this.subject, trustedCertificateInternalResp.subject) &&
        Objects.equals(this.updatedAt, trustedCertificateInternalResp.updatedAt) &&
        Objects.equals(this.validity, trustedCertificateInternalResp.validity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, certificate, createdAt, description, deviceExecutionMode, enrollmentMode, etag, id, issuer, name, object, ownerId, privateKey, service, status, subject, updatedAt, validity);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrustedCertificateInternalResp {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    certificate: ").append(toIndentedString(certificate)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    deviceExecutionMode: ").append(toIndentedString(deviceExecutionMode)).append("\n");
    sb.append("    enrollmentMode: ").append(toIndentedString(enrollmentMode)).append("\n");
    sb.append("    etag: ").append(toIndentedString(etag)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    object: ").append(toIndentedString(object)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    privateKey: ").append(toIndentedString(privateKey)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    validity: ").append(toIndentedString(validity)).append("\n");
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

