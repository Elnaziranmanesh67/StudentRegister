package com.example.studentregister.student.orm;

import com.example.studentregister.student.orm.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByNationalCode(String nationalCode);
}
