package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Reads from CSV Files and Writes to CSV files
public class csvRW {
    private final String COMMA_DELIMITER = ",";
    List<List<String>> result = new ArrayList<>();

//Reads from a CSV File
    public List<List<String>> csvReader(String filename) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                result.add(Arrays.asList(values));
            }
        }
        return result;
    }
//Writes to a CSV Files
    public void csvWriter(String file, String input) throws IOException {
        BufferedWriter csvWrite = new BufferedWriter(new FileWriter(file, true));
        csvWrite.write(input);
        csvWrite.close();
    }

    //Removes Data and ReWrites it to a CSV file
    public void csvOverWriter(String file, String input) throws IOException {
        BufferedWriter csvWrite = new BufferedWriter(new FileWriter(file, false));
        csvWrite.write(input);
        csvWrite.close();
    }
}

