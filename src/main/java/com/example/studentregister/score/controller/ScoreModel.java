package com.example.studentregister.score.controller;

import java.util.Date;

import com.example.studentregister.enumRegister.AcademicTermEnum;
import com.example.studentregister.enumRegister.GradeStatusEnum;
import com.example.studentregister.enumRegister.PassStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreModel {
    private Integer id;

    private Integer classScore;

    private Integer midtermScore;

    private Integer finalExamScore;

    private Integer passingScore;

    private GradeStatusEnum gradeStatus;

    private PassStatusEnum passStatus;

    private Date termStartDate;

    private AcademicTermEnum academicTerm;

    private String courseName;

    private Integer teacherId;

    private Integer studentId;
}
