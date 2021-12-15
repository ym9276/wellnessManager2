package view;

import java.awt.*;
import javax.swing.*;

/* NutritionPanel draws a live-updated
* bar chart, displaying the selected
* date's fats, carbs, and proteins,
* measured in grams on the Y-axis. */
public class NutritionawtCanvas extends JPanel {
    private double[] value;
    private String[] name;

    public NutritionawtCanvas() {
        this.add(new JLabel("                                                                               "));
        value = new double[]{0,0,0};
        name = new String[]{"Fat","Carb","Protein"};
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (value == null || value.length == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < value.length; i++) {
            if (minValue > value[i])
                minValue = value[i];
            if (maxValue < value[i])
                maxValue = value[i];
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = clientWidth / value.length;

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int y = titleFontMetrics.getAscent();
        //int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        //g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < value.length; i++) {
            int valueX = i * barWidth + 1;
            int valueY = top;
            int height = (int) (value[i] * scale);
            if (value[i] >= 0)
                valueY += (int) ((maxValue - value[i]) * scale);
            else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }
            if(name[i].equals("Fat")){
                g.setColor(Color.red);
            }else if(name[i].equals("Carb")){
                g.setColor(Color.green);
            }else if(name[i].equals("Protein")){
                g.setColor(Color.blue);
            }
            g.fillRect(valueX, valueY, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 2, height);
            int labelWidth = labelFontMetrics.stringWidth(name[i]);
            int x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(name[i]+":"+value[i], x, y);
        }
    }
    public void setValue(double fat, double carb, double protein){
        if(fat == 0 || carb == 0 || protein == 0){
            value[0] = 0;
            value[1] = 0;
            value[2] = 0;
        }else {
            value[0] = fat;
            value[1] = carb;
            value[2] = protein;
        }
    }

//    public void paint(Graphics g){
//        Rectangle bounds = g.getClipBounds() ;
//        int w = (int) bounds.getWidth() ;
//        int h = (int) bounds.getHeight() ;
//
//        int barWidth = w/value.length;
//
//        g.setColor(Color.red);
//        g.setPaintMode();
//
//        for(int i=0; i<value.length; i++){
//            g.fillRect(i*barWidth, h);
//        }
//    }

    public static void main(String[] argv) {
        JFrame f = new JFrame();
        f.setSize(800, 400);
        NutritionawtCanvas canvas = new NutritionawtCanvas();
        f.getContentPane().add(canvas);
        //canvas.setValue(80,70,25);
        f.setVisible(true);
    }
}