package com.pixelswordgames.numbers.Game;


import android.content.Context;

import com.pixelswordgames.numbers.Utils.ExpressionsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.pixelswordgames.numbers.Utils.ExpressionsUtils.formatDouble;

public class TaskGenerator {

    private int lvl;
    private final Tasks tasks;

    public TaskGenerator(Context context){
        tasks = new Tasks(context);
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    private String expressionGenerator(String pattern, double topLimit, double bottomLimit){
        StringBuilder expression = new StringBuilder();

        for(char c : pattern.toCharArray())
            if(Character.isLetter(c) && !Character.isDigit(c))
                expression.append(generateValue(c, topLimit, bottomLimit));
            else if(Character.isDigit(c))
                expression.append(c);
            else expression.append(" ").append(c).append(" ");


        return expression.toString().replace(',', '.');
    }

    private String generateValue(char c, double topLimit, double bottomLimit) {
        switch (c){
            case 'i':
                return formatDouble(new Random().nextInt((int) (topLimit - bottomLimit)) + bottomLimit);
            case 'f':
                return formatDouble(
                        new Random().nextInt((int) (topLimit - bottomLimit)) + bottomLimit - 1 +
                              new Random().nextFloat()
                );
            default:
                return "0";
        }
    }

    public List<Task> generateAllTasks(){
        List<Task> taskList = new ArrayList<>();

        Level level = tasks.getLevel(lvl);
        while (taskList.size() < level.getCount()){
            try {
                String task = expressionGenerator(
                        level.getPatterns().get(new Random().nextInt(level.getPatterns().size())),
                        level.getValuesTopLimit(),
                        level.getValuesBottomLimit()
                );

                List<String> solution = generateSolutions(task, level);

                taskList.add(new Task(
                        ExpressionsUtils.formatExpression(task),
                        solution,
                        solution.get(0)
                ));
            } catch (RuntimeException e){
                e.printStackTrace();
            }
        }

        return taskList;
    }

    private List<String> generateSolutions(String task, Level level){
        String correctSolution = formatDouble(ExpressionsUtils.eval(task));
        List<String> solutions = new ArrayList<>();
        if(task.contains("("))
            solutions.add(
                    formatDouble(ExpressionsUtils.eval(task.replace('(',' ').replace(')',' ')))
            );
        if(task.contains("*"))
            solutions.add(
                    formatDouble(ExpressionsUtils.eval(task.replace('*','+')))
            );
        if(task.contains("-"))
            solutions.add(
                    formatDouble(ExpressionsUtils.eval(task.replace('-','/')))
            );
        if(task.contains("+"))
            solutions.add(
                    formatDouble(ExpressionsUtils.eval(task.replace('+','*')))
            );
        if(task.contains("/"))
            solutions.add(
                    formatDouble(ExpressionsUtils.eval(task.replace('/','-')))
            );

        for(int i = 0; i < 6; ++i)
            solutions.add(
                    formatDouble(Double.parseDouble(correctSolution) +
                            new Random().nextInt((int) (level.getValuesTopLimit() - level.getValuesBottomLimit()))
                            + level.getValuesBottomLimit())
            );

        Collections.shuffle(solutions);
        solutions = solutions.subList(0,5);
        solutions.add(0, correctSolution);

        return solutions;
    }


}
