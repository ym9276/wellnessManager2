package model;
// dailyLog will display, store, and update a database of:
// - desired daily goals  (calories, weight)
// - daily values as compared to those goals
// - x-day "streak" for meeting goals for x days, maybe?
// dailyLog will contain methods for:
// - calling display classes
// - accessing recipeFoodDB to calculate daily values
// - updating logDB with new daily goals & values
// - comparing daily goals & daily values, adding to "streak"
// - tracking a "streak" (if we decide to do that)

import java.util.*;
import java.io.*;
import java.util.Date;

//Handles data from recipeFoods.csv and writes data to log.csv
public class dailyLog  {
    private String weight;
    private String cal_limit;
    private String servingCount;
    private String foodConsumed;
    private String date;
    //private double total = 0;
    private double foodCalories = 0;
//    String fileName = "src/wellnessmanager/data/log.csv";
//    String fileName_food = "src/wellnessmanager/data/recipefoods.csv";
    String fileName = "log.csv";
    String fileName_food = "recipefoods.csv";
    csvRW cs = new csvRW();
    csvRW cs_food = new csvRW();
    csvRW writer = new csvRW();
    public dailyLog(){
        String tempDate = java.time.LocalDate.now().toString();
        String dateAttribute[] = tempDate.split("-");
        date = dateAttribute[0]+","+dateAttribute[1]+","+dateAttribute[2];

    }


    //Sets the food consumed in log.csv
    public void setFoodConsumed(String _foodConsumed)throws IOException{
        double total = 0;
        String logAttribute[];
        String addChoice =  "\n"+ _foodConsumed;
        writer.csvWriter(fileName, addChoice);
        logAttribute = _foodConsumed.split(",");
        //date = logAttribute[0]+"," + logAttribute[1]+"," + logAttribute[2];




    }

    //returns the total calories, fat, carbs, and protien for the given date
    public List<Double> getTotalCalories(String temp)throws IOException{
        List<List<String>> result = new ArrayList<>();
        List<List<String>> resultFood = new ArrayList<>();
        result = cs.csvReader(fileName);;
        resultFood = cs_food.csvReader(fileName_food);
        double total = 0;
        double fat = 0;
        double carb = 0;
        double protein = 0;
        String logAttribute[];
        logAttribute = temp.split(",");
        date = logAttribute[0]+"," + logAttribute[1]+"," + logAttribute[2];

        for(int j = 0; j < result.size(); j++){
            List<String> l1 = new ArrayList<String>();
            l1 = result.get(j);
            String tempDate = l1.get(0) + "," + l1.get(1) + "," +l1.get(2);
            if(date.equals(tempDate)){
                if(l1.get(3).equals("f")){
                    String foodConsumedName ="";
                    String foodServing ="";
                    foodConsumedName = l1.get(4) ;
                    foodServing = l1.get(5);
                    for(int i = 0; i <resultFood.size(); i++){
                        List<String> l2 = new ArrayList<String>();
                        l2 = resultFood.get(i);
                        String single = l2.get(1);
                        String type = l2.get(0);
                        double foodCalories;
                        double foodFat;
                        double foodCarb;
                        double foodProtein;
                        if(type.equals("b")){
                            if(single.equals(foodConsumedName)){
                                foodCalories = Double.parseDouble(l2.get(2))*Double.parseDouble(foodServing);
                                foodFat = Double.parseDouble(l2.get(3))*Double.parseDouble(foodServing);
                                foodCarb = Double.parseDouble(l2.get(4))*Double.parseDouble(foodServing);
                                foodProtein = Double.parseDouble(l2.get(5))*Double.parseDouble(foodServing);
                                total =  total + foodCalories;
                                fat = fat + foodFat;
                                carb = carb + foodCarb;
                                protein = protein + foodProtein;

                            }
                        }
                        else{

                            if(single.equals(foodConsumedName)){
                                for(int a = 2; a < l2.size(); a++){
                                    String single2 = l2.get(a);
                                    String foodServing2 = l2.get(a+1);
                                    for(int b = 0; b < resultFood.size();b++){
                                        List<String> l3 = new ArrayList<String>();
                                        l3 = resultFood.get(b);
                                        String type2 = l3.get(0);
                                        double foodCalories2;
                                        double foodFat2;
                                        double foodCarb2;
                                        double foodProtein2;
                                        if(type2.equals("b")){


                                            if(single2.equals(l3.get(1))){
                                                foodCalories2 = Double.parseDouble(l3.get(2))*Double.parseDouble(foodServing2);
                                                foodCalories2 = Double.parseDouble(l3.get(2))*Double.parseDouble(foodServing2);
                                                foodFat2 = Double.parseDouble(l3.get(3))*Double.parseDouble(foodServing2);
                                                foodCarb2 = Double.parseDouble(l3.get(4))*Double.parseDouble(foodServing2);
                                                foodProtein2 = Double.parseDouble(l3.get(5))*Double.parseDouble(foodServing2);
                                                total =  total + foodCalories2;
                                                fat = fat + foodFat2;
                                                carb = carb + foodCarb2;
                                                protein = protein + foodProtein2;
                                            }
                                        }
                                    }
                                    a++;
                                }
                            }
                        }
                    }

                }
            }


        }
        result.clear();
        resultFood.clear();
        List<Double> all = new ArrayList<>();
        all.add(total);
        all.add(fat);
        all.add(carb);
        all.add(protein);
        return all;

    }

    //sets the date
    public void setDate(String _date)throws IOException{
        date = _date;
    }

    //returns the date
    public String getDate(){
        return date;
    }

    //returns the calorie limit for the given date
    public String getCalorieLimit(String date) throws Exception{
        List<List<String>> logList = new ArrayList<>();
        logList = cs.csvReader(fileName);
        for(int i=0; i<logList.size(); i++){
            List<String> logInstance = new ArrayList<String>();
            logInstance = logList.get(i);
            String InstanceDate = logInstance.get(0)+","+logInstance.get(1)+","+logInstance.get(2);
            if (logInstance.get(3).equals("c")){
                cal_limit = logInstance.get(4);
            }
        }
        return cal_limit;
    }

}//daily log
