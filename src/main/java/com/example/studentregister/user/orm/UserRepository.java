package com.example.studentregister.user.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
    User findByNationalCodeAndUsername(String nationalCode, String username);
    User findByNationalCode(String nationalCode);
}
