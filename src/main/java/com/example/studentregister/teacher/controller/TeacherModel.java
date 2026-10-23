package com.example.studentregister.teacher.controller;

import com.example.studentregister.enumRegister.EnglishLevelEnum;
import com.example.studentregister.enumRegister.GenderEnum;
import com.example.studentregister.enumRegister.TeacherEmploymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private GenderEnum gender;
    private Date birthDate;
    private String nationalCode;
    private String phoneNumber;
    private String email;
    private String homeAddress;
    private EnglishLevelEnum educationLevel;
    private String fieldOfStudy;
    private EnglishLevelEnum englishLevel;
    private Integer teachingExperienceYears;
    private String specialization;
    private Date employmentDate;
    private TeacherEmploymentStatusEnum employmentStatus;

}
