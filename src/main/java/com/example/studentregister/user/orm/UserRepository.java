package com.example.studentregister.user.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {

    User findByUsername(String username);
    User findByUsernameAndNationalCode(String username, String nationalCode);
    User findByUsernameAndNationalCodeAndRole(String username, String nationalCode, RoleEnum role);
    User findByNationalCode(String nationalCode);
}
