package com.example.studentregister.student.controller;

import com.example.studentregister.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    StudentModel create(StudentModel studentModel) throws Exception {
        return studentService.create(studentModel);
    }

    @GetMapping("/{nationalCode}")
    Boolean checkStudentInfo(@PathVariable String nationalCode)throws Exception {
        return studentService.checkStudentInfo(nationalCode);
    }


}
