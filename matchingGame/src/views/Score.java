package views;
import java.awt.*;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Date;
import javax.swing.*;

public class Score extends JFrame{
    public JFrame secondFrame;
    public JLabel secondLabel;
    double highScore=0;
    public Score(){
        try {
            RandomAccessFile r = new RandomAccessFile("HighScore.txt", "r");
            r.seek(0);
            this.highScore = r.readDouble();

            String s = String.format("                 HighScore: "+String.valueOf(highScore)+ " Seconds");
            JOptionPane.showMessageDialog(null,new JTextArea(s),"High Score",JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"NaN","High Score",JOptionPane.PLAIN_MESSAGE);
        }
    }
}
