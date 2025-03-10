package com.poker.controller;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.domain.User;
import com.poker.dto.ScoreEditRequest;
import com.poker.dto.ScoreRequest;
import com.poker.service.ScoreService;
import com.poker.service.ScoreServiceImpl;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ScoreList findAll(){
        return scoreService.findAll();}

    // ユーザーIDでスコアを取得
    @GetMapping("/{id}")
    public ScoreList findByUserId(@PathVariable("id") Long userId) {
        return scoreService.findByUserId(userId);
    }

    @GetMapping("detail/{scoreId}")
    public  Score findByScoreId(@PathVariable("scoreId") Long scoreId) {return  scoreService.findByScoreId(scoreId);}
    // 新規スコア登録
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addScore(@RequestBody ScoreRequest scoreRequest) {

        Score score = new Score();
        score.setUserId(scoreRequest.getUserId());
        score.setScore(scoreRequest.getScore());
        System.out.println(scoreRequest.getUserId());

        // 現在の日時を設定
        score.setGameDate(LocalDate.from(LocalDateTime.now()));

        scoreService.insertScore(score);
        return "Score added successfully";
    }

    // スコア情報の更新
    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String editScore(@Valid @RequestBody ScoreEditRequest scoreEditRequest) {
        scoreService.updateScore(scoreEditRequest);
        return "Score updated successfully";
    }

}