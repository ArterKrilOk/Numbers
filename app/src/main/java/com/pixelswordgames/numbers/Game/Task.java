package com.pixelswordgames.numbers.Game;

import java.util.List;

public class Task {
    public String text;
    public List<String> solutions;
    public String correctSolution;

    public Task(String text, List<String> solutions, String correctSolution) {
        this.text = text;
        this.solutions = solutions;
        this.correctSolution = correctSolution;
    }
}