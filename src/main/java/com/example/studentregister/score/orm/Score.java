package com.example.studentregister.score.orm;

import com.example.studentregister.enumRegister.AcademicTermEnum;
import com.example.studentregister.enumRegister.GradeStatusEnum;
import com.example.studentregister.enumRegister.PassStatusEnum;
import com.example.studentregister.student.orm.Student;
import com.example.studentregister.teacher.orm.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SCORE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // نمره کلاسی
    @Column(name = "class_score", precision = 4, scale = 2)
    private Integer classScore;

    // نمره میان‌ترم
    @Column(name = "midterm_score", precision = 4, scale = 2)
    private Integer midtermScore;

    // نمره پایان‌ترم
    @Column(name = "final_exam_score", precision = 4, scale = 2)
    private Integer finalExamScore;

    // حد نصاب قبولی
    @Column(name = "passing_score", precision = 4, scale = 2)
    private Integer passingScore;

    // وضعیت ثبت نمرات
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_status", length = 20)
    private GradeStatusEnum gradeStatus;

    // وضعیت قبولی
    @Enumerated(EnumType.STRING)
    @Column(name = "pass_status", length = 20)
    private PassStatusEnum passStatus;

    // تاریخ شروع ترم
    @Column(name = "term_start_date")
    private Date termStartDate;

    // ترم تحصیلی
    @Enumerated(EnumType.STRING)
    @Column(name = "academic_term", length = 20)
    private AcademicTermEnum academicTerm;

    // نام دوره
    @Column(name = "course_name", length = 150)
    private String courseName;

    // استاد
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    // دانشجو
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Objects.equals(id, score.id) && Objects.equals(classScore, score.classScore) && Objects.equals(midtermScore, score.midtermScore) && Objects.equals(finalExamScore, score.finalExamScore) && Objects.equals(passingScore, score.passingScore) && Objects.equals(gradeStatus, score.gradeStatus) && Objects.equals(passStatus, score.passStatus) && Objects.equals(termStartDate, score.termStartDate) && Objects.equals(academicTerm, score.academicTerm) && Objects.equals(courseName, score.courseName) && Objects.equals(teacher, score.teacher) && Objects.equals(student, score.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classScore, midtermScore, finalExamScore, passingScore, gradeStatus, passStatus, termStartDate, academicTerm, courseName, teacher, student);
    }
}


