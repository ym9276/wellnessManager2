package model;
import java.io.*;

public class food  {
    private String name;
    private double calories;
    private double fat;
    private double carb;
    private double protein;
    private char type;
    csvRW writer = new csvRW();

//    String file = "src/wellnessmanager/data/recipefoods.csv";
    String file = "recipefoods.csv";

    public food(){
    }

    //sets the food and writes it to recipeFoods.csv
    public void setFood(String var1) throws IOException {
        String foodAttribute [] = var1.split(",");
        String addChoice = "\n"+"b,"+ var1;
        Boolean condition = true;
        while(condition) {
            if(foodAttribute.length == 5) {
                this.name = foodAttribute[0];
                this.calories = Double.parseDouble(foodAttribute[1]);
                this.fat = Double.parseDouble(foodAttribute[2]);
                this.carb = Double.parseDouble(foodAttribute[3]);
                this.protein = Double.parseDouble(foodAttribute[4]);
                writer.csvWriter(file,addChoice);
                condition = false;
                break;
            }
            else {
                System.out.println("Please use following format: \"Name\",\"Calories\",\"Fat\",\"Carb\",\"Protein\"");
                break;
            }
        }

    }


}
