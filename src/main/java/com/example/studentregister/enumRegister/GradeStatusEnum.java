package com.example.studentregister.enumRegister;

public enum GradeStatusEnum  {

    NOT_ENTERED("NOT_ENTERED"), // ثبت نشده
    DRAFT("DRAFT"),             // پیش‌نویس
    COMPLETED("COMPLETED"),     // ثبت کامل
    PUBLISHED("PUBLISHED");    // اعلام‌شده به دانشجو

    private final String value;

    GradeStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
