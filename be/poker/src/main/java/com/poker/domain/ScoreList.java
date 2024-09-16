package com.poker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreList {
    List<Score> ScoreList;

    public List<Score> getScoreList() {
        return ScoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        ScoreList = scoreList;
    }
}
