package com.example.studentregister.enumRegister;

public enum TeacherEmploymentStatusEnum  {

    ACTIVE("ACTIVE"),           // مشغول به همکاری
    INACTIVE("INACTIVE"),       // غیرفعال
    ON_LEAVE("ON_LEAVE"),       // مرخصی
    TERMINATED("TERMINATED");   // پایان همکاری

    private final String value;

    TeacherEmploymentStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
