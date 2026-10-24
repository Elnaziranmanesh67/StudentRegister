package com.example.studentregister.acl.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AclRepository extends JpaRepository<Acl,Integer> {
}
