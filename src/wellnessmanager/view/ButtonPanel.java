package view;

import model.dailyLog;
import model.recipe;
import view.InteractionPanel;


import java.awt.GridLayout ;
import javax.swing.* ;
import controller.ButtonListener;

/* ButtonPanel contains the buttons for:
* |  -Add Food           |  -Remove Food       |
* |  -Edit Food          |  -Add Recipe        |
* |  -Remove Recipe      |  -Edit Recipe       |
* |  -Add Log            |  -Remove Log        |
* |  -Edit Log           |  -Add Exercise      |
* |  -Edit Exercise      |  -Set Weight Limit  |
* |  -Set Calorie Limit  |  -Select Day        |
* |  -Get Daily Data     |  -Calorie Expended  |*/
public class ButtonPanel extends JPanel {
    private InteractionPanel interactionPanel;
    private HealthPanel healthPanel;
    private NutritionawtCanvas canvas;

    public ButtonPanel(InteractionPanel _interactionPanel, HealthPanel _healthPanel, NutritionawtCanvas _canvas) {
        setLayout(new GridLayout( 5,3 ));

        interactionPanel = _interactionPanel;
        healthPanel = _healthPanel;
        canvas = _canvas;

        //this.add(changeButton("example1",/*constructed*/));
        JButton button;

        button = addButton("Add Food");
        this.add(button);

        button = addButton("Add Recipe");
        this.add(button);

        button = addButton("Add Log");
        this.add(button);

        button = addButton("Add Exercise");
        this.add(button);

        button = addButton("Remove Food");
        this.add(button);

        button = addButton("Remove Recipe");
        this.add(button);

        button = addButton("Remove Log");
        this.add(button);

        button = addButton("Remove Exercise");
        this.add(button);

        button = addButton("Edit Food");
        this.add(button);

        button = addButton("Edit Recipe");
        this.add(button);

        button = addButton("Edit Log");
        this.add(button);

        button = addButton("Edit Exercise");
        this.add(button);

        button = addButton("Set Weight Limit");
        this.add(button);

        button = addButton("Set Calorie Limit");
        this.add(button);

        button = addButton("Select day");
        this.add(button);

        button = addButton("Get Daily Data");
        this.add(button);

        button = addButton("Calorie Expended");
        this.add(button);

    }
    /* Testing main */


    //add button method
    private JButton addButton(String label){
        JButton button = new JButton(label);
        button.addActionListener(new ButtonListener(interactionPanel, healthPanel,canvas));

        return button;
    }
}