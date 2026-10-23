package com.example.studentregister.student.orm;

import com.example.studentregister.enumRegister.GenderEnum;
import com.example.studentregister.enumRegister.SchoolGradeEnum;
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
@Table(name = "STUDENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // نام
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    // نام خانوادگی
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    // جنسیت
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private GenderEnum gender;

    // تاریخ تولد
    @Column(name = "birth_date")
    private Date birthDate;

    // کد ملی
    @Column(name = "national_code", unique = true, length = 10)
    private String nationalCode;

    // شماره تماس دانش‌آموز
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    // آدرس منزل
    @Column(name = "address", length = 500)
    private String address;

    // ایمیل
    @Column(name = "email", length = 150)
    private String email;

    // شماره موبایل والدین
    @Column(name = "parent_phone_number", length = 15)
    private String parentPhoneNumber;

    // پایه تحصیلی
    @Enumerated(EnumType.STRING)
    @Column(name = "school_grade", length = 30)
    private SchoolGradeEnum schoolGrade;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Collection<Score> scoreCollection = new ArrayList<>();

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
