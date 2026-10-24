package com.example.studentregister.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessExceptionList extends Exception {
    private HashMap<BusinessException, String> businessExceptionList;
}
