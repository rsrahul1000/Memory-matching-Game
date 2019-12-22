package controllers;

import java.awt.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import models.Card;
import views.*;

public class CardController implements ActionListener{
    private Vector turnedCards;
    private Timer turnDownTimer;
    private int count=0;
    private final int turnDownDelaty=500; //in terms of millisec.
    public  CardController(){
        this.turnedCards=new Vector(2);
        this.turnDownTimer=new Timer(this.turnDownDelaty,this);
        this.turnDownTimer.setRepeats(false);
    }
    /*
    Consider Card Class is Created in another package(models)
    */
    public boolean turnUp(Card card){
        if(this.turnedCards.size()<2)return doAddCard(card);
        return false;
    }
    
    private boolean doAddCard(Card card)
    {
        this.turnedCards.add(card);
        if(this.turnedCards.size()==2){
            Card otherCard=(Card)this.turnedCards.get(0);
            if(otherCard.getNum()==card.getNum()){
                this.turnedCards.clear();
                count+=2;
                if(count==16){
                    try {
                        currentTimingWindow(Instant.now());
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            else this.turnDownTimer.start();
        }
        return true;
    }
    
    void currentTimingWindow(Instant end) throws Exception {
        int t;
        double check;
        RandomAccessFile r=null;
        //Calculating time
        Instant start = matchingGame.start;
        matchingGame.end = end;
        Duration timeElapsed = Duration.between(start, matchingGame.end);
        double timeInSeconds = (timeElapsed.toMillis())/1000.0;
        //System.out.println("Time taken: "+ timeInSeconds +" milliseconds");

        r = new RandomAccessFile("HighScore.txt", "rw");
        if(r.length()==0){
            r.writeDouble(timeInSeconds);

        }
        else{
            r.seek(0);
            check = r.readDouble();
            if(check>timeInSeconds) {
                r.seek(0);
                r.writeDouble(timeInSeconds);
            }
        }


        //Window to display Current Score
        String s = String.format("   Current Time: "+String.valueOf(timeInSeconds)+ " Seconds");
        JOptionPane.showConfirmDialog(null,s,"Current Score",JOptionPane.DEFAULT_OPTION);

    }

    @Override
    public void actionPerformed(ActionEvent arg0){
        for(int i=0;i<this.turnedCards.size();i++){
            Card card=(Card)this.turnedCards.get(i);
            card.turnDown();
        }
        this.turnedCards.clear();
    }
    //Code Snippetabout controller to the Card and matchingGame Classes.
} 