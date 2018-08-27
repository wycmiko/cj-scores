package com.cj.scores.api;

import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.UserScores;

/**
 * scores
 */
public interface Scores {
    Result insertUserScore(UserScores scores);

}
