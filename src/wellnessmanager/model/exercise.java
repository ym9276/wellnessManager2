package model;
import model.csvRW;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.List;


public class exercise {
   private String calBurned;
   private String exerciseName;
   //double calorieExpended = 0;
   double weight;

   String exerciseFile = "exercise.csv";
   String logFile = "log.csv";
//    String exerciseFile = "src/wellnessmanager/data/exercise.csv";
//    String logFile = "src/wellnessmanager/data/log.csv";
   HashMap<String, String> exerciseMap = new HashMap<String, String>();
   csvRW writer = new csvRW();
   csvRW logReader = new csvRW();
   csvRW exerciseReader = new csvRW();




   public void setExercise(String calBurned, String exerciseName){
       this.calBurned = calBurned;
       this.exerciseName = exerciseName;
   }

   //adds an exercise to the exercise.csv file
   public void addExercise(String _exerciseEntry){
       String exerciseAttribute[] = _exerciseEntry.split(",");
       String exerciseEntry = "\n"+"e," + _exerciseEntry ;

       exerciseName = exerciseAttribute[0];
       calBurned = exerciseAttribute[1];
       exerciseMap.put(exerciseName,calBurned);

       try{
           writer.csvWriter(exerciseFile, exerciseEntry);
       }catch (Exception e){}
       //exerciseMap.put(exerciseName,calBurned);
   }

   //calculates the total amount of calories burned from exercises in a given day
   public double calculateBurnedCalories(String date) throws IOException{
       List<List<String>> logList = new ArrayList<>();
       List<List<String>> exerciseList = new ArrayList<>();
       logList = logReader.csvReader(logFile);
       exerciseList = exerciseReader.csvReader(exerciseFile);
       double total=0;


       for(int i=0; i<logList.size(); i++){
           List<String> logInstance = new ArrayList<String>();
           logInstance = logList.get(i);
           String InstanceDate = logInstance.get(0)+","+logInstance.get(1)+","+logInstance.get(2);
           if (logInstance.get(3).equals("w")){
               weight = Double.parseDouble(logInstance.get(4));
           }
       }

       for(int j = 0; j < logList.size(); j++){
           List<String> logInstance = new ArrayList<String>();
           logInstance = logList.get(j);
           String InstanceDate = logInstance.get(0)+","+logInstance.get(1)+","+logInstance.get(2);
           if(InstanceDate.equals(date)){
               if(logInstance.get(3).equals("e")){
                   String exerciseName = logInstance.get(4);
                   double exerciseTime = Double.parseDouble(logInstance.get(5));
                   for(int i=0; i<exerciseList.size(); i++){
                       List<String> exerciseInstance = new ArrayList<>();
                       exerciseInstance = exerciseList.get(i);
                       String exerciseName2 = exerciseInstance.get(1);
                       double exerciseCalorie = Double.parseDouble(exerciseInstance.get(2));
                       if(exerciseName.equals(exerciseName2)){
                           double calorieExpended = exerciseCalorie * (weight/100) * (exerciseTime/60);
                           total = total +calorieExpended;

                       }
                   }
               }
           }
       }//for

       logList.clear();
       exerciseList.clear();
       return total;
   }
}
