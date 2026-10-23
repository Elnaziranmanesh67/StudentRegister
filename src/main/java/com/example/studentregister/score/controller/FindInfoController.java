package com.example.studentregister.score.controller;


import com.example.studentregister.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/find-info")
public class FindInfoController {

    private final StudentService studentService;

    @GetMapping("/{nationalCode}")
    public FindInfoModel findInfo(@PathVariable String nationalCode) throws Exception {
        return studentService.findInfo(nationalCode);
    }
}
