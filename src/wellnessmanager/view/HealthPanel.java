package view;

import model.dailyLog;
import javax.swing.*;
import java.awt.*;

/* HealthPanel displays Calorie counts,
 * current weight, and levels of carbs,
 * fats, and proteins as shown in the graph */
public class HealthPanel extends JPanel {

    //constructor

    private dailyLog log;

    private JLabel calorieConsumed2;
    private JLabel calorieExpended2;
    private JLabel NetCalorieLabel;
    private JLabel GoalcalorieLabel;
    private JLabel fatLabel2;
    private JLabel proteinLabel2;
    private JLabel carbLabel2;

    public HealthPanel(){

        // create Layout
        setLayout(new GridLayout(3,1));

        // panel 1 of HealthPanel
        JPanel panel1 = new JPanel();
        JLabel calorieLabel1 = new JLabel("Calorie Goal: ");
        JLabel calorieLabel2 = new JLabel("/");
        NetCalorieLabel = new JLabel("##");
        GoalcalorieLabel = new JLabel("##");
        JLabel fatLabel1 = new JLabel("Percentage of fats(g): ");
        fatLabel2 = new JLabel("##");

        panel1.add(calorieLabel1);
        panel1.add(NetCalorieLabel);
        panel1.add(calorieLabel2);
        panel1.add(GoalcalorieLabel);
        panel1.add(fatLabel1);
        panel1.add(fatLabel2);

        // panel 2 of HealthPanel
        JPanel panel2 = new JPanel();
        JLabel calorieConsumed1 = new JLabel("Calorie Consumed: ");
        calorieConsumed2 = new JLabel("##");
        JLabel calorieExpended1 = new JLabel("Calorie Expended: ");
        calorieExpended2 = new JLabel("##");

        panel2.add(calorieConsumed1);
        panel2.add(calorieConsumed2);
        panel2.add(calorieExpended1);
        panel2.add(calorieExpended2);

        // panel 3 of HealthPanel
        JPanel panel3 = new JPanel();
        JLabel proteinLabel1 = new JLabel("Percentage of Protein(g): ");
        proteinLabel2 = new JLabel("##");
        JLabel carbLabel1 = new JLabel("Percentage of Carb(g): ");
        carbLabel2 = new JLabel("##");

        panel3.add(proteinLabel1);
        panel3.add(proteinLabel2);
        panel3.add(carbLabel1);
        panel3.add(carbLabel2);

        this.add(panel2);
        this.add(panel1);
        this.add(panel3);


    }

    /* Testing main */
    public static void main(String[] args) {
        JFrame frame = new JFrame("test") ;

        frame.add(new HealthPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame.pack() ;
        frame.setVisible(true) ;
    }

    // setting label text
    public void setcalorieConsumed2(String input){
        calorieConsumed2.setText(input);
    }

    public void setcalorieExpended2(String input){
        calorieExpended2.setText(input);
    }

    public void setGoalcalorieLabel(String input){
        GoalcalorieLabel.setText(input);
    }

    public void setproteinLabel2(String input){
        proteinLabel2.setText(input);
    }

    public void setfatLabel2(String input){
        fatLabel2.setText(input);
    }

    public void setcarbLabel2(String input){
        carbLabel2.setText(input);
    }

    public void setNetCalorieLabel(String input){
        NetCalorieLabel.setText(input);
    }


}
