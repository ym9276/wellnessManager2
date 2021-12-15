package model;
import model.food;

import java.util.*;
import java.io.*;
import model.csvRW;


// - create new recipes using food items
// - name, store, and update recipe entries in the database
public class recipe extends food {
    String recipeEntry;
    String recipeName;
    double totalCalories = 0;
    double totalCarb;
    double totalFat;
    double totalProtein;
    HashMap<String, String> recipeMap = new HashMap<String, String>();
    csvRW cs = new csvRW();
    csvRW writer = new csvRW();

//    String file = "src/wellnessmanager/data/recipefoods.csv";
    String file = "recipefoods.csv";

//writes a recipe to recipefoods.csv
    public void addRecipe(String _recipeEntry)throws IOException{
        
        String addChoice = "\n"+"r," + _recipeEntry;
        //System.out.println(recipeEntry);
        writer.csvWriter(file,addChoice);
        String recipeAttribute[] = _recipeEntry.split(",");
        recipeName = recipeAttribute[0];
        //System.out.println(recipeName);
        for(int i = 1; i < recipeAttribute.length; i++){
            recipeMap.put(recipeAttribute[i],recipeAttribute[i+1]);
            i++;
        }
    }

}
