
package com.example.studentregister.student.controller;

import com.example.studentregister.enumRegister.GenderEnum;
import com.example.studentregister.enumRegister.SchoolGradeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private GenderEnum gender;
    private Date birthDate;
    private String nationalCode;
    private String phoneNumber;
    private String address;
    private String email;
    private String parentPhoneNumber;
    private SchoolGradeEnum schoolGrade;

}
