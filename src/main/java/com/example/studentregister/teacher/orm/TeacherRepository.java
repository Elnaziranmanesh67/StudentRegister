package com.example.studentregister.teacher.orm;

import com.example.studentregister.teacher.orm.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByNationalCode(String nationalCode);
}
