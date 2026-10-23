package com.example.studentregister.score.service;


import com.example.studentregister.score.controller.ScoreModel;
import com.example.studentregister.score.orm.Score;
import com.example.studentregister.score.orm.ScoreRepository;
import com.example.studentregister.student.orm.Student;
import com.example.studentregister.student.orm.StudentRepository;
import com.example.studentregister.teacher.orm.Teacher;
import com.example.studentregister.teacher.orm.TeacherRepository;
import com.example.studentregister.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Component
@Service
@Qualifier("ScoreServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ScoreModel create(ScoreModel model) {

        Teacher teacher = teacherRepository.findById(model.getTeacherId())
                .orElseThrow(() ->
                        new RuntimeException("استاد پیدا نشد"));

        Student student = studentRepository.findById(model.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("دانشجو پیدا نشد"));

        Score score = Score.builder()
                .classScore(model.getClassScore())
                .midtermScore(model.getMidtermScore())
                .finalExamScore(model.getFinalExamScore())
                .passingScore(model.getPassingScore())
                .gradeStatus(model.getGradeStatus())
                .passStatus(model.getPassStatus())
                .termStartDate(model.getTermStartDate())
                .academicTerm(model.getAcademicTerm())
                .courseName(model.getCourseName())
                .teacher(teacher)
                .student(student)
                .build();

        Score savedScore = scoreRepository.save(score);

        return ScoreModel.builder()
                .id(savedScore.getId())
                .classScore(savedScore.getClassScore())
                .midtermScore(savedScore.getMidtermScore())
                .finalExamScore(savedScore.getFinalExamScore())
                .passingScore(savedScore.getPassingScore())
                .gradeStatus(savedScore.getGradeStatus())
                .passStatus(savedScore.getPassStatus())
                .termStartDate(savedScore.getTermStartDate())
                .academicTerm(savedScore.getAcademicTerm())
                .courseName(savedScore.getCourseName())
                .teacherId(teacher.getId())
                .studentId(student.getId())
                .build();
    }

}
