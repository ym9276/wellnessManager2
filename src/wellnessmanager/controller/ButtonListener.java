package controller;


import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import model.*;
import view.*;

public class ButtonListener implements ActionListener {
    InteractionPanel interactionPanel;
    ButtonPanel buttonpanel;
    HealthPanel healthPanel;
    NutritionawtCanvas canvas;

    csvRW writer = new csvRW();
    csvRW foodReader = new csvRW();
    csvRW logReader = new csvRW();
    csvRW exerciseReader = new csvRW();

    food food1 = new food();
    recipe recipe1 = new recipe();
    dailyLog log = new dailyLog();
    exercise exercise = new exercise();

    String foodFile = "recipefoods.csv";
    String logFile = "log.csv";
    String exerciseFile = "exercise.csv";

//    String foodFile = "src/wellnessmanager/data/recipefoods.csv";
//    String logFile = "src/wellnessmanager/data/log.csv";
//    String exerciseFile = "src/wellnessmanager/data/exercise.csv";


    public ButtonListener(InteractionPanel interactionPanel, HealthPanel healthPanel, NutritionawtCanvas canvas){
        this.interactionPanel = interactionPanel;
        this.canvas = canvas;
        this.healthPanel = healthPanel;
    }

    public ButtonListener(){
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //Object source = e.getSource();
        JButton button = (JButton) e.getSource();
        if(button.getText().equals("Add Food")){
            interactionPanel.setTextPanel("");
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            try {
                if(entry.equals("")){
                    interactionPanel.setTextPanel("Enter the name of food, calories, fat, carb, protein follow by commas");
                }else{
                    if(tempEntry.length == 5){
                        food1.setFood(entry);
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Success!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                    }else{
                        interactionPanel.setTextPanel("Please use following format: \"Name\",\"Calories\",\"Fat\",\"Carb\",\"Protein\"\"");
                    }
                }
            } catch (Exception A) {
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Remove Food")){
            interactionPanel.setTextPanel("");
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            try {
                List<List<String>> foodList = new ArrayList<>();
                foodList = foodReader.csvReader(foodFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<foodList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                    foodList.clear();
                }else{
                    if (tempEntry.length == 1) {
                        foodList.remove(Integer.parseInt(entry));
                        writer.csvOverWriter(foodFile, "");
                        for (int i = 0; i < foodList.size(); i++) {
                            String stringFormat = foodList.get(i).toString().replaceAll("\\[|\\]|\\s", "") + "\n";
                            writer.csvWriter(foodFile, stringFormat);
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Removed!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0) - exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1), le.get(2), le.get(3));
                        canvas.repaint();
                        foodList.clear();
                    }
                    else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<foodList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                        foodList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Edit Food")){
            String entry = interactionPanel.getTextFieldInput();
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> foodList = new ArrayList<>();
                foodList = foodReader.csvReader(foodFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<foodList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index to edit, follow by food name & attribute");
                    foodList.clear();
                }else{
                    String [] tempEntry = entry.split(",");
                    if(tempEntry.length == 7) {
                        //List<String> tempEntry1 = Arrays.asList(tempEntry);
                        List<String> tempEntry1 = new ArrayList<String>();
                        Collections.addAll(tempEntry1, tempEntry);
                        tempEntry1.remove(0);
                        foodList.set(Integer.parseInt(tempEntry[0]), tempEntry1);
                        writer.csvOverWriter(foodFile, "");
                        for (int i = 0; i < foodList.size(); i++) {
                            String stringFormat = foodList.get(i).toString().replaceAll("\\[|\\]|\\s", "") + "\n";
                            writer.csvWriter(foodFile, stringFormat);
                            interactionPanel.setTextPanel(i + ") " + foodList.get(i).toString());
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Updated!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        foodList.clear();
                    }else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<foodList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Please use following format: \"Index\",\"Type\",\"Name\",\"Calories\",\"Fat\",\"Carb\",\"Protein\"\"");
                        foodList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
       else if(button.getText().equals("Add Recipe")){
            String entry = interactionPanel.getTextFieldInput();
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> foodList = new ArrayList<>();
                foodList = foodReader.csvReader(foodFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<foodList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter the name of recipe, food, serving follow by commas");
                    foodList.clear();
                }else {
                    recipe1.addRecipe(entry);
                    interactionPanel.setTextPanel("Success!");
                    List<Double> le = new ArrayList<Double>();
                    le = log.getTotalCalories(log.getDate());
                    healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                    healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                    healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                    healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                    healthPanel.setfatLabel2(Double.toString(le.get(1)));
                    healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                    healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                    canvas.setValue(le.get(1),le.get(2),le.get(3));
                    canvas.repaint();
                    interactionPanel.setTextFieldInput();
                }
            }catch (Exception A) {
                System.out.println(A.toString());
            }
       }
        else if(button.getText().equals("Remove Recipe")){
            String entry = interactionPanel.getTextFieldInput();
            String [] tempEntry = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> foodList = new ArrayList<>();
                foodList = foodReader.csvReader(foodFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<foodList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                    foodList.clear();
                }else{
                    if(tempEntry.length == 1){
                        foodList.remove(Integer.parseInt(entry));
                        writer.csvOverWriter(foodFile, "");
                        for(int i=0; i<foodList.size(); i++){
                            String stringFormat = foodList.get(i).toString().replaceAll("\\[|\\]|\\s", "") +"\n";
                            writer.csvWriter(foodFile, stringFormat);
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Removed!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        foodList.clear();
                    }
                    else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<foodList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                        foodList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }

        else if(button.getText().equals("Edit Recipe")){
            String entry = interactionPanel.getTextFieldInput();
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> foodList = new ArrayList<>();
                foodList = foodReader.csvReader(foodFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<foodList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+foodList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index to edit, follow by recipe name & quantity");
                    foodList.clear();
                }else{
                    String [] tempEntry = entry.split(",");
                    List<String> tempEntry1 = new ArrayList<String>();
                    Collections.addAll(tempEntry1, tempEntry);
                    tempEntry1.remove(0);
                    foodList.set(Integer.parseInt(tempEntry[0]), tempEntry1);
                    writer.csvOverWriter(foodFile, "");
                    for (int i = 0; i < foodList.size(); i++) {
                        String stringFormat = foodList.get(i).toString().replaceAll("\\[|\\]|\\s", "") + "\n";
                        writer.csvWriter(foodFile, stringFormat);
                        interactionPanel.setTextPanel(i + ") " + foodList.get(i).toString());
                    }
                    interactionPanel.setTextFieldInput();
                    interactionPanel.setTextPanel("Updated!");
                    List<Double> le = new ArrayList<Double>();
                    le = log.getTotalCalories(log.getDate());
                    healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                    healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                    healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                    healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                    healthPanel.setfatLabel2(Double.toString(le.get(1)));
                    healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                    healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                    canvas.setValue(le.get(1),le.get(2),le.get(3));
                    canvas.repaint();
                    foodList.clear();
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
       else if(button.getText().equals("Add Log")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                if(entry.equals("")){
                    interactionPanel.setTextPanel("Enter index, yyyy, mm, dd, type, name, #,  follow by commas");
                    interactionPanel.setTextPanel("Type: f/e (food/exercise), name (food/exercise name), # (number of serving/calorie in min)");
                }else {
                    if(tempEntry.length == 6){
                        log.setFoodConsumed(entry);
                        interactionPanel.setTextPanel("Success!");
                        List<Double> le = new ArrayList<Double>();
                        //le = log.getTotalCalories(tempEntry[0]+","+tempEntry[1]+","+tempEntry[2]);
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        interactionPanel.setTextFieldInput();
                    }
                    else{
                        interactionPanel.setTextPanel("Enter index, yyyy, mm, dd, type, name, #,  follow by commas");
                        interactionPanel.setTextPanel("Type: f/e (food/exercise), name (food/exercise name), # (number of serving/calorie in min)");
                    }
                }
            }catch (Exception A) {
                System.out.println(A.toString());
            }
       }
        else if(button.getText().equals("Remove Log")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> logList = new ArrayList<>();
                logList = logReader.csvReader(logFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<logList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+logList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                    logList.clear();
                }else{
                    if(tempEntry.length == 1){
                        logList.remove(Integer.parseInt(entry));
                        writer.csvOverWriter(logFile, "");
                        for(int i=0; i<logList.size(); i++){
                            String stringFormat = logList.get(i).toString().replaceAll("\\[|\\]|\\s", "") +"\n";
                            writer.csvWriter(logFile, stringFormat);
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Removed!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        logList.clear();
                    }else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<logList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+logList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                        logList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Edit Log")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> logList = new ArrayList<>();
                logList = logReader.csvReader(logFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<logList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+logList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index, yyyy, mm, dd, type, name, #,  follow by commas");
                    interactionPanel.setTextPanel("Type: f/e (food/exercise), name (food/exercise name), # (number of serving/calorie in min)");

                    logList.clear();
                }else{
                    if(tempEntry.length == 7) {
                        //List<String> tempEntry1 = Arrays.asList(tempEntry);
                        List<String> tempEntry1 = new ArrayList<String>();
                        Collections.addAll(tempEntry1, tempEntry);
                        tempEntry1.remove(0);
                        logList.set(Integer.parseInt(tempEntry[0]), tempEntry1);
                        writer.csvOverWriter(logFile, "");
                        for (int i = 0; i < logList.size(); i++) {
                            String stringFormat = logList.get(i).toString().replaceAll("\\[|\\]|\\s", "") + "\n";
                            writer.csvWriter(logFile, stringFormat);
                            interactionPanel.setTextPanel(i + ") " + logList.get(i).toString());
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.setTextPanel("Updated!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        interactionPanel.setTextFieldInput();
                        logList.clear();
                    }else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<logList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+logList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index, yyyy, mm, dd, type, name, #,  follow by commas");
                        interactionPanel.setTextPanel("Type: f/e (food/exercise), name (food/exercise name), # (number of serving/calorie in min)");
                        logList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Add Exercise")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> exerciseList = new ArrayList<>();
                exerciseList = exerciseReader.csvReader(exerciseFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<exerciseList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+exerciseList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter name of exercise, calories expended");
                    exerciseList.clear();
                }else {
                    if(tempEntry.length == 2){
                                                exercise.addExercise(entry);
                        interactionPanel.setTextPanel("Success!");
                        interactionPanel.setTextFieldInput();
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        interactionPanel.setTextFieldInput();
                    }
                    else{
                        interactionPanel.setTextPanel("Please use the following format: name of exercise, calories");
                    }
                }

            }catch (Exception A) {
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Remove Exercise")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> exerciseList = new ArrayList<>();
                exerciseList = exerciseReader.csvReader(exerciseFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<exerciseList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+exerciseList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                    exerciseList.clear();
                }else{
                    if(tempEntry.length == 1){
                        exerciseList.remove(Integer.parseInt(entry));
                        writer.csvOverWriter(exerciseFile, "");
                        for(int i=0; i<exerciseList.size(); i++){
                            String stringFormat = exerciseList.get(i).toString().replaceAll("\\[|\\]|\\s", "") +"\n";
                            writer.csvWriter(exerciseFile, stringFormat);
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.emptyTextArea();
                        interactionPanel.setTextPanel("Removed!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        exerciseList.clear();
                    }else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<exerciseList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+exerciseList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index below and click remove button again to remove");
                        exerciseList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Edit Exercise")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                List<List<String>> exerciseList = new ArrayList<>();
                exerciseList = exerciseReader.csvReader(exerciseFile);
                if(entry.equals("")){
                    interactionPanel.emptyTextArea();
                    for(int i=0; i<exerciseList.size(); i++){
                        interactionPanel.setTextPanel( i+") "+exerciseList.get(i).toString());
                    }
                    interactionPanel.setTextPanel("Enter index, name, calories to edit");
                    exerciseList.clear();
                }else{
                    if(tempEntry.length == 3) {
                        List<String> tempEntry1 = new ArrayList<String>();
                        Collections.addAll(tempEntry1, tempEntry);
                        tempEntry1.remove(0);
                        exerciseList.set(Integer.parseInt(tempEntry[0]), tempEntry1);
                        writer.csvOverWriter(exerciseFile, "");
                        for (int i = 0; i < exerciseList.size(); i++) {
                            String stringFormat = exerciseList.get(i).toString().replaceAll("\\[|\\]|\\s", "") + "\n";
                            writer.csvWriter(exerciseFile, stringFormat);
                            interactionPanel.setTextPanel(i + ") " + exerciseList.get(i).toString());
                        }
                        interactionPanel.setTextFieldInput();
                        interactionPanel.emptyTextArea();
                        interactionPanel.setTextPanel("Updated!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        exerciseList.clear();
                    }else {
                        interactionPanel.emptyTextArea();
                        for(int i=0; i<exerciseList.size(); i++){
                            interactionPanel.setTextPanel( i+") "+exerciseList.get(i).toString());
                        }
                        interactionPanel.setTextPanel("Enter index, name, calories to edit");
                        exerciseList.clear();
                    }
                }
            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Set Weight Limit")) {
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                if(entry.equals("")){
                    interactionPanel.setTextPanel("Enter yyyy, mm, dd, w, current weight");
                }else {
                    if(tempEntry.length == 5){
                        log.setFoodConsumed(entry);
                        interactionPanel.setTextPanel("Success!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        interactionPanel.setTextFieldInput();
                    }
                    else{
                        interactionPanel.setTextPanel("Please Enter yyyy, mm, dd, w, current weight");
                    }
                }
            }catch (Exception A) {
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Set Calorie Limit")) {
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
                if(entry.equals("")){
                    interactionPanel.setTextPanel("Enter yyyy, mm, dd, c, calorie limit");
                }else {
                    if(tempEntry.length == 5){
                        log.setFoodConsumed(entry);
                        interactionPanel.setTextPanel("Success!");
                        List<Double> le = new ArrayList<Double>();
                        le = log.getTotalCalories(log.getDate());
                        healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                        healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                        healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                        healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                        healthPanel.setfatLabel2(Double.toString(le.get(1)));
                        healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                        healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                        canvas.setValue(le.get(1),le.get(2),le.get(3));
                        canvas.repaint();
                        interactionPanel.setTextFieldInput();
                    }
                    else{
                        interactionPanel.setTextPanel("Please Enter yyyy, mm, dd, c, calorie limit");
                    }
                }
            }catch (Exception A) {
                System.out.println(A.toString());
            }
        }
       else if(button.getText().equals("Get Daily Data")){
            String entry = interactionPanel.getTextFieldInput();
            interactionPanel.setTextPanel("");
            try {
                if(entry.equals("")){
                    List<Double> le = new ArrayList<Double>();
                    le = log.getTotalCalories(log.getDate());
                    interactionPanel.setTextPanel("Current Date: "+log.getDate());
                    interactionPanel.setTextPanel("Total Calories: "+ Double.toString(le.get(0)));
                    interactionPanel.setTextPanel("Fat: "+ Double.toString(le.get(1)));
                    interactionPanel.setTextPanel("Carb: "+Double.toString(le.get(2)));
                    interactionPanel.setTextPanel("Protein: "+Double.toString(le.get(3)));
                    healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                    healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                    healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(log.getDate()))));
                    healthPanel.setGoalcalorieLabel(log.getCalorieLimit(log.getDate()));
                    healthPanel.setfatLabel2(Double.toString(le.get(1)));
                    healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                    healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                    canvas.setValue(le.get(1),le.get(2),le.get(3));
                    canvas.repaint();
                }else {
                    List<Double> le = new ArrayList<Double>();
                    le = log.getTotalCalories(entry);
                    interactionPanel.setTextPanel("Current Date: "+log.getDate());
                    interactionPanel.setTextPanel("Total Calories: "+ Double.toString(le.get(0)));
                    interactionPanel.setTextPanel("Fat: "+ Double.toString(le.get(1)));
                    interactionPanel.setTextPanel("Carb: "+Double.toString(le.get(2)));
                    interactionPanel.setTextPanel("Protein: "+Double.toString(le.get(3)));
                    healthPanel.setcalorieConsumed2(Double.toString(le.get(0)));
                    healthPanel.setcalorieExpended2(Double.toString(exercise.calculateBurnedCalories(entry)));
                    healthPanel.setNetCalorieLabel(Double.toString((le.get(0)-exercise.calculateBurnedCalories(entry))));
                    healthPanel.setGoalcalorieLabel(log.getCalorieLimit(entry));
                    healthPanel.setfatLabel2(Double.toString(le.get(1)));
                    healthPanel.setcarbLabel2(Double.toString(le.get(2)));
                    healthPanel.setproteinLabel2(Double.toString(le.get(3)));
                    canvas.setValue(le.get(1),le.get(2),le.get(3));
                    canvas.repaint();
                }


            }catch (Exception A){
                System.out.println(A.toString());
            }

        }
        else if(button.getText().equals("Select day")){
            String entry = interactionPanel.getTextFieldInput();
            String tempEntry [] = entry.split(",");
            interactionPanel.setTextPanel("");
            try {
               if(entry.equals("")){
                   interactionPanel.setTextPanel("Please enter a date in the following format: yyyy, mm, dd");
               }
               else {
                   if(tempEntry.length==3){
                       log.setDate(entry);
                       interactionPanel.emptyTextArea();
                       interactionPanel.setTextPanel("Current Date: "+entry);
                   }
               }

            }catch (Exception A){
                System.out.println(A.toString());
            }
        }
        else if(button.getText().equals("Calorie Expended")){
            String entry = interactionPanel.getTextFieldInput();
            interactionPanel.setTextPanel("");
            try {
                if(entry.equals("")){
                    interactionPanel.setTextPanel("Calorie Expended: "+Double.toString(exercise.calculateBurnedCalories(log.getDate())));
                }else {
                    interactionPanel.setTextPanel("Calorie Expended: "+Double.toString(exercise.calculateBurnedCalories(entry)));
                }

            }catch (Exception A){
                System.out.println(A.toString());
            }
        }

    }//actionPerformed

}//buttonListener class
