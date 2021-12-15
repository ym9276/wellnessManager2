//package wellnessmanager;

//import controller.GUIController;
import controller.ButtonListener;
import model.dailyLog;
import view.ButtonPanel;
import view.InteractionPanel;
import view.HealthPanel;
import view.NutritionawtCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class wellnessManager  {
    static dailyLog log;
    static ButtonListener var1;
    static InteractionPanel interactionPanel;
    static ButtonPanel buttonPanel;
    static HealthPanel healthPanel;
    static NutritionawtCanvas canvas;

    public static void main(String[] args){
        JFrame frame;
        interactionPanel = new InteractionPanel();
        healthPanel = new HealthPanel();
        canvas = new NutritionawtCanvas();
        buttonPanel = new ButtonPanel(interactionPanel, healthPanel, canvas);

        frame = new JFrame("WellnessManger") ;
        frame.setPreferredSize(new Dimension(1400,500));

        frame.getContentPane().add(canvas);
        frame.add(canvas,BorderLayout.LINE_END );

        frame.add(buttonPanel,BorderLayout.LINE_START);
        frame.add(interactionPanel,BorderLayout.PAGE_START);
        frame.add(healthPanel,BorderLayout.CENTER);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame.pack() ;
        frame.setLocationRelativeTo(null);
        frame.setVisible(true) ;
    }
}
