package com.arm.mbed.cloud.sdk.security.model;

import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.listing.ListOptions;

@Preamble(description = "Options to use when listing certificate enrollments")
public class CertificateEnrollmentListOptions extends ListOptions {
    public static final String DEVICE_ID_FILTER = "deviceId";
    public static final String CERTIFICATE_NAME_FILTER = "certificateName";
    public static final String ENROLL_STATUS_FILTER = "enrollStatus";
    public static final String ENROLL_RESULT_FILTER = "enrollResult";
    public static final String CREATED_AT_FILTER = "createdAt";
    public static final String UPDATED_AT_FILTER = "updatedAt";

    /**
     * Constructor.
     */
    public CertificateEnrollmentListOptions() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.arm.mbed.cloud.sdk.common.listing.ListOptions#clone()
     */
    @Override
    public CertificateEnrollmentListOptions clone() {
        final CertificateEnrollmentListOptions opt = new CertificateEnrollmentListOptions();
        opt.setOptions(this);
        return opt;
    }
}
