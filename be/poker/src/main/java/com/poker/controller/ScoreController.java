package com.poker.controller;

import com.poker.domain.ScoreList;
import com.poker.service.ScoreService;
import com.poker.service.ScoreServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scores")
public class ScoreController {

    private final ScoreServiceImpl scoreService;

    public ScoreController(ScoreServiceImpl scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScoreList findAll(){ return scoreService.findAll();}
}
