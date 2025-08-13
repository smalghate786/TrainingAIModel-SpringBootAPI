package com.ai.model.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.model.dto.TrainingRequest;
import com.ai.model.service.AiTrainerService;

@RestController
@RequestMapping("/ai")
public class AiTrainerController {

    private final AiTrainerService service;

    public AiTrainerController(AiTrainerService service) {
        this.service = service;
    }

    @PostMapping("/addData")
    public String addData(@RequestBody TrainingRequest req) {
        return service.addTrainingData(req);
    }

    @PostMapping("/train")
    public String train() {
        return service.trainModel();
    }

    @GetMapping("/predict")
    public String predict(@RequestParam String text) {
        return service.predict(text);
    }
}

