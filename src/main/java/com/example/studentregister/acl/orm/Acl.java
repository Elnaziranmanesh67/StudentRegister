package com.example.studentregister.acl.orm;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ACL_")
@Data
public class Acl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean enabled = true;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;


}
