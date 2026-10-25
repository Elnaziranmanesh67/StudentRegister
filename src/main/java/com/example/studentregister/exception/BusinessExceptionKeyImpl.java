package com.example.studentregister.exception;

public enum BusinessExceptionKeyImpl implements CustomExceptionKey {
    USER_NOT_FOUND("USER_NOT_FOUND"),
    NATIONAL_CODE_REGISTER_BEFORE("NATIONAL_CODE_REGISTER_BEFORE"),
    USER_NAME_REGISTER_BEFORE("USER_NAME_REGISTER_BEFORE"),
    WRONG_USERNAME_PASSWORD("WRONG_USERNAME_PASSWORD"),
    PASSWORD_IS_INVALID("PASSWORD_IS_INVALID"),
    REQUIRE_USERNAME_AND_NATIONAL_CODE("REQUIRE_USERNAME_AND_NATIONAL_CODE"),
    USER_REGISTER_BEFORE("USER_REGISTER_BEFORE"),
    ROLE_REQUIRE("ROLE_REQUIRE"),
    INVALID_ROLE("INVALID_ROLE"),
    PASSWORD_IS_MISMATCH("PASSWORD_IS_MISMATCH");

    private final String value;

    BusinessExceptionKeyImpl(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
