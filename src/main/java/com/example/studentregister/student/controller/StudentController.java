package com.example.studentregister.student.controller;

import com.example.studentregister.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    StudentModel create(@Valid @RequestBody StudentModel studentModel) throws Exception {
        return studentService.create(studentModel);
    }

    @GetMapping("/{nationalCode}")
    Boolean checkStudentInfo(@PathVariable String nationalCode)throws Exception {
        return studentService.checkStudentInfo(nationalCode);
    }


}
