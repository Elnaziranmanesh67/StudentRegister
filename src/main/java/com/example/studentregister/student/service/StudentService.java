package com.example.studentregister.student.service;

import com.example.studentregister.score.controller.FindInfoModel;
import com.example.studentregister.student.controller.StudentModel;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Boolean checkStudentInfo(String nationalCode) throws Exception;
    StudentModel create(StudentModel studentModel) throws Exception;
    FindInfoModel findInfo(String nationalCode) throws Exception;
}
