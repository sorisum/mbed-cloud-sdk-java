package com.arm.mbed.cloud.sdk.accountmanagement.model;

import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.SdkEnum;

@Preamble(description = "The status of the user")
public enum UserStatus implements SdkEnum {
    ENROLLING("ENROLLING"), INVITED("INVITED"), ACTIVE("ACTIVE"), RESET("RESET"), INACTIVE("INACTIVE"), UNKNOWN_ENUM(
            SDK_UNKNOWN_ENUM_VALUE);

    private final String value;

    /**
     * Constructor.
     * 
     * @param value
     *            string representation.
     */
    private UserStatus(String string) {
        this.value = string;
    }

    /**
     * States whether it is the default value.
     * 
     * @see SdkEnum#isDefault()
     */
    @Override
    public boolean isDefault() {
        return this == getDefault();
    }

    /**
     * States whether the value is unknown and an error happened during parsing.
     * 
     * @see SdkEnum#isUnknownValue()
     */
    @Override
    public boolean isUnknownValue() {
        return this == getUnknownEnum();
    }

    /**
     * Gets default user status.
     * 
     * @return default status.
     */
    public static final UserStatus getDefault() {
        return UserStatus.INACTIVE;
    }

    /**
     * Gets Unknown state value.
     * 
     * @return unknown state.
     */
    public static UserStatus getUnknownEnum() {
        return UNKNOWN_ENUM;
    }

    /**
     * Gets the string representation of the user status.
     * 
     * @see SdkEnum#getString()
     */
    @Override
    public String getString() {
        return value;
    }

    /**
     * Gets string representation.
     * 
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getString();
    }

    /**
     * Merges two statuses.
     * 
     * @see SdkEnum#merge(SdkEnum, SdkEnum)
     */
    @Override
    public <T extends SdkEnum> T merge(T obj1, T obj2) {
        if (obj1 == null) {
            return obj2;
        }
        if (obj2 == null) {
            return obj1;
        }
        if (obj1.isDefault()) {
            return obj2;
        }
        if (obj2.isDefault()) {
            return obj1;
        }
        return obj2;
    }
}
