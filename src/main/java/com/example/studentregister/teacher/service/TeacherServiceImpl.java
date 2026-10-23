package com.example.studentregister.teacher.service;


import com.example.studentregister.teacher.controller.TeacherModel;
import com.example.studentregister.teacher.orm.Teacher;
import com.example.studentregister.teacher.orm.TeacherRepository;
import com.example.studentregister.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Qualifier("TeacherServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherModel create(TeacherModel teacherModel) {
        Teacher teacher = Teacher.builder()
                .firstName(teacherModel.getFirstName())
                .lastName(teacherModel.getLastName())
                .gender(teacherModel.getGender())
                .birthDate(teacherModel.getBirthDate())
                .nationalCode(teacherModel.getNationalCode())
                .phoneNumber(teacherModel.getPhoneNumber())
                .email(teacherModel.getEmail())
                .homeAddress(teacherModel.getHomeAddress())
                .educationLevel(teacherModel.getEducationLevel())
                .fieldOfStudy(teacherModel.getFieldOfStudy())
                .englishLevel(teacherModel.getEnglishLevel())
                .teachingExperienceYears(
                        teacherModel.getTeachingExperienceYears())
                .specialization(teacherModel.getSpecialization())
                .employmentDate(teacherModel.getEmploymentDate())
                .employmentStatus(teacherModel.getEmploymentStatus())
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        return TeacherModel.builder()
                .id(savedTeacher.getId())
                .firstName(savedTeacher.getFirstName())
                .lastName(savedTeacher.getLastName())
                .gender(savedTeacher.getGender())
                .birthDate(savedTeacher.getBirthDate())
                .nationalCode(savedTeacher.getNationalCode())
                .phoneNumber(savedTeacher.getPhoneNumber())
                .email(savedTeacher.getEmail())
                .homeAddress(savedTeacher.getHomeAddress())
                .educationLevel(savedTeacher.getEducationLevel())
                .fieldOfStudy(savedTeacher.getFieldOfStudy())
                .englishLevel(savedTeacher.getEnglishLevel())
                .teachingExperienceYears(
                        savedTeacher.getTeachingExperienceYears())
                .specialization(savedTeacher.getSpecialization())
                .employmentDate(savedTeacher.getEmploymentDate())
                .employmentStatus(savedTeacher.getEmploymentStatus())
                .build();
    }


    @Override
    public Boolean checkTeacherInfo(String nationalCode) throws Exception{
        Teacher student = teacherRepository.findByNationalCode(nationalCode);
        if (student != null) {
            return true;
        }
        return false;
    }
}
