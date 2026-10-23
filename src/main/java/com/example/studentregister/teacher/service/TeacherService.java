package com.example.studentregister.teacher.service;

import com.example.studentregister.teacher.controller.TeacherModel;
import org.springframework.stereotype.Service;

@Service
public interface TeacherService {
    TeacherModel create(TeacherModel teacherModel);
    Boolean checkTeacherInfo(String nationalCode) throws Exception;
}

