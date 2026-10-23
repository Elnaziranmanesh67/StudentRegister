package com.example.studentregister.teacher.orm;

import com.example.studentregister.enumRegister.EnglishLevelEnum;
import com.example.studentregister.enumRegister.GenderEnum;
import com.example.studentregister.enumRegister.TeacherEmploymentStatusEnum;
import com.example.studentregister.score.orm.Score;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TEACHER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // نام
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    // نام خانوادگی
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    // جنسیت
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private GenderEnum gender;

    // تاریخ تولد
    @Column(name = "birth_date")
    private Date birthDate;

    // کد ملی
    @Column(name = "national_code", unique = true, length = 10)
    private String nationalCode;

    // شماره موبایل
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    // ایمیل
    @Column(name = "email", length = 150)
    private String email;

    // آدرس منزل
    @Column(name = "home_address", length = 500)
    private String homeAddress;

    // مدرک تحصیلی
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", length = 30)
    private EnglishLevelEnum educationLevel;

    // رشته تحصیلی
    @Column(name = "field_of_study", length = 100)
    private String fieldOfStudy;

    // سطح زبان انگلیسی
    @Enumerated(EnumType.STRING)
    @Column(name = "english_level", length = 10)
    private EnglishLevelEnum englishLevel;

    // سابقه تدریس به سال
    @Column(name = "teaching_experience_years")
    private Integer teachingExperienceYears;
    // تخصص تدریس
    @Column(name = "specialization", length = 200)
    private String specialization;

    // تاریخ شروع همکاری
    @Column(name = "employment_date")
    private Date employmentDate;

    // وضعیت همکاری
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", length = 20)
    private TeacherEmploymentStatusEnum employmentStatus;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Collection<Score> scoreCollection = new ArrayList<>();

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName) && gender == teacher.gender && Objects.equals(birthDate, teacher.birthDate) && Objects.equals(nationalCode, teacher.nationalCode) && Objects.equals(phoneNumber, teacher.phoneNumber) && Objects.equals(email, teacher.email) && Objects.equals(homeAddress, teacher.homeAddress) && Objects.equals(educationLevel, teacher.educationLevel) && Objects.equals(fieldOfStudy, teacher.fieldOfStudy) && englishLevel == teacher.englishLevel && Objects.equals(teachingExperienceYears, teacher.teachingExperienceYears) && Objects.equals(specialization, teacher.specialization) && Objects.equals(employmentDate, teacher.employmentDate) && employmentStatus == teacher.employmentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, gender, birthDate, nationalCode, phoneNumber, email, homeAddress, educationLevel, fieldOfStudy, englishLevel, teachingExperienceYears, specialization, employmentDate, employmentStatus);
    }
}
