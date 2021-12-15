package view;

import javax.swing.*;
import java.awt.*;
import controller.ButtonListener;
import model.dailyLog;

/* InteractionPanel is where textfield inputs,
 * such as names of exercises, display to prompt
 * the user for input. The data input by the user
 * is then sent to GUIController for delegation. */
public class InteractionPanel extends JPanel {
    // creating textArea and textField
    public JTextArea textArea;
    private JTextField textField1;
    dailyLog log = new dailyLog();

    // constructor
    public InteractionPanel() {
        setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        textArea = new JTextArea(15,80); //ADD A SCROLLPANE?
        //textArea.setBorder(BorderFactory.createEmptyBorder(10,110,10,10));
        textArea.setText("Welcome to Wellness Manager"+"\n"+"Current date: "+log.getDate());
        textArea.setEditable(false);
        Font font = new Font("",Font.PLAIN, 14);
        textArea.setFont(font);
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
        panel1.add(scrollPaneTextArea);

        JPanel panel2 = new JPanel();
        textField1 = new JTextField(20);
        JLabel textFieldLabel = new JLabel("Enter here:");
        panel2.add(textFieldLabel);
        panel2.add(textField1);

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.SOUTH);
    }

    /* Testing main */
    public static void main(String[] args) {
        JFrame frame = new JFrame("test") ;
        frame.add(new InteractionPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame.pack() ;
        frame.setVisible(true) ;
    }

    public String getTextFieldInput() {
        return textField1.getText();
    }

    public void setTextFieldInput(){
        textField1.setText("");
    }

    public void emptyTextArea() {
        textArea.setText("");
    }

    public void setTextPanel(String promptMsg) {
        textArea.setText(textArea.getText()+"\n"+promptMsg);
    }
}


