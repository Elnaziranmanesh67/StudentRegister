package com.example.studentregister.enumRegister;

public enum EnglishLevelEnum {

    A1("A1"),             // مبتدی
    A2("A2"),             // مقدماتی
    B1("B1"),             // متوسط
    B2("B2"),             // متوسط رو به بالا
    C1("C1"),             // پیشرفته
    C2("C2"),             // تسلط کامل
    NATIVE("NATIVE");     // زبان مادری

    private final String value;

    EnglishLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
