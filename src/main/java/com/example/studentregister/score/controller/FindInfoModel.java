package com.example.studentregister.score.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindInfoModel {

    private String firstName;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;


}
