package com.example.studentregister.user.orm;

public enum RoleEnum {

        TEACHER("استاد"),
        STUDENT("زبان‌آموز");

        private final String value;

        RoleEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

}
