package com.example.studentregister.student.service;

import com.example.studentregister.exception.BusinessException;
import com.example.studentregister.exception.BusinessExceptionKeyImpl;
import com.example.studentregister.score.controller.FindInfoModel;
import com.example.studentregister.student.controller.StudentModel;
import com.example.studentregister.student.orm.Student;
import com.example.studentregister.student.orm.StudentRepository;
import com.example.studentregister.user.orm.User;
import com.example.studentregister.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Component
@Service
@Qualifier("StudentServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentModel create(StudentModel studentModel) throws Exception {

        Student findStudent=studentRepository.findByNationalCode(studentModel.getNationalCode());
        if(findStudent!=null){
            throw new BusinessException(BusinessExceptionKeyImpl.USER_REGISTER_BEFORE);
        }
        Student student = Student.builder()
                .firstName(studentModel.getFirstName())
                .lastName(studentModel.getLastName())
                .gender(studentModel.getGender())
                .birthDate(studentModel.getBirthDate())
                .nationalCode(studentModel.getNationalCode())
                .phoneNumber(studentModel.getPhoneNumber())
                .address(studentModel.getAddress())
                .email(studentModel.getEmail())
                .parentPhoneNumber(studentModel.getParentPhoneNumber())
                .schoolGrade(studentModel.getSchoolGrade())
                .build();
        Student savedStudent = studentRepository.save(student);
        return StudentModel.builder()
                .id(savedStudent.getId())
                .firstName(savedStudent.getFirstName())
                .lastName(savedStudent.getLastName())
                .gender(savedStudent.getGender())
                .birthDate(savedStudent.getBirthDate())
                .nationalCode(savedStudent.getNationalCode())
                .phoneNumber(savedStudent.getPhoneNumber())
                .address(savedStudent.getAddress())
                .email(savedStudent.getEmail())
                .parentPhoneNumber(savedStudent.getParentPhoneNumber())
                .schoolGrade(savedStudent.getSchoolGrade())
                .build();
    }


    @Override
    public FindInfoModel findInfo(String nationalCode) throws Exception {
        User user=userRepository.findByNationalCode(nationalCode);
        FindInfoModel findInfoModel =new FindInfoModel();
        if(user!=null){
            findInfoModel.setFirstName(user.getFirstName());
            findInfoModel.setLastName(user.getLastName());
            findInfoModel.setNationalCode(nationalCode);

        }else{
            throw new Exception("کاربر یافت نشد");
        }

        return findInfoModel;
    }

    @Override
    public Boolean checkStudentInfo(String nationalCode) throws Exception {
        Student student = studentRepository.findByNationalCode(nationalCode);
        if (student != null) {
            return true;

        }
        return false;
    }
}
