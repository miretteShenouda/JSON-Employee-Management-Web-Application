package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgrammingLanguage {
    @JsonProperty("LanguageName")
    private String languageName;
    @JsonProperty("ScoreOutof100")
    private int scoreOutof100;

    // Constructors

    public ProgrammingLanguage() {
        // Default constructor
    }

    public ProgrammingLanguage(String languageName, int scoreOutof100) {
        this.languageName = languageName;
        this.scoreOutof100 = scoreOutof100;
    }

    // Getters and Setters

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getScoreOutof100() {
        return scoreOutof100;
    }

    public void setScoreOutof100(int scoreOutof100) {
        this.scoreOutof100 = scoreOutof100;
    }
}
