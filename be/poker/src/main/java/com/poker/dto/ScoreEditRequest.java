// src/main/java/com/poker/dto/ScoreEditRequest.java
package com.poker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class ScoreEditRequest {

    @NotNull(message = "Score ID is required")
    private Long scoreId;

    @NotNull(message = "Score is required")
    @Positive(message = "Score must be positive")
    private Integer score;

    @NotNull(message = "Game date is required")
    private LocalDate gameDate;

    @NotNull(message = "UpdatedBy is required")
    private String updatedBy;

    // ゲッターとセッター

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
