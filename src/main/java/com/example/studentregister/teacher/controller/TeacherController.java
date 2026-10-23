package com.example.studentregister.teacher.controller;

import com.example.studentregister.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("")
    public TeacherModel create(TeacherModel teacherModel) throws Exception {
        return teacherService.create(teacherModel);
    }


    @GetMapping("/{nationalCode}")
    Boolean checkTeacherInfo(@PathVariable String nationalCode)throws Exception {
        return teacherService.checkTeacherInfo(nationalCode);
    }


}
