package com.example.studentregister.enumRegister;

public enum SchoolGradeEnum {

    PRESCHOOL("PRESCHOOL"),  // پیش‌دبستانی
    GRADE_1("GRADE_1"),      // اول ابتدایی
    GRADE_2("GRADE_2"),      // دوم ابتدایی
    GRADE_3("GRADE_3"),      // سوم ابتدایی
    GRADE_4("GRADE_4"),      // چهارم ابتدایی
    GRADE_5("GRADE_5"),      // پنجم ابتدایی
    GRADE_6("GRADE_6"),      // ششم ابتدایی
    GRADE_7("GRADE_7"),      // هفتم
    GRADE_8("GRADE_8"),      // هشتم
    GRADE_9("GRADE_9"),      // نهم
    GRADE_10("GRADE_10"),    // دهم
    GRADE_11("GRADE_11"),    // یازدهم
    GRADE_12("GRADE_12"),    // دوازدهم
    UNIVERSITY("UNIVERSITY"), // دانشجو
    GRADUATE("GRADUATE"),    // فارغ‌التحصیل
    OTHER("OTHER");          // سایر

    private final String value;

    SchoolGradeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
