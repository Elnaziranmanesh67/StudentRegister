package com.example.studentregister.score.service;

import com.example.studentregister.score.controller.ScoreModel;
import org.springframework.stereotype.Component;


@Component
public interface ScoreService {
    ScoreModel create(ScoreModel model);
}
