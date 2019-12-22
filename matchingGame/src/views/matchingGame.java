package views;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Date;
import javax.swing.*;
import models.Card;
import controllers.CardController;
import java.awt.Color;
import java.time.Instant;

public class matchingGame implements ActionListener
{
    private JFrame mainFrame;
    private Container mainContentPane;
    private ImageIcon cardIcon[];
    //public static Date start, end;
    public static Instant start=null, end=null;
    
    public matchingGame()
    {
        this.mainFrame = new JFrame("Matching Game");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(525,700);
        this.mainFrame.setLocation(700,150);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane,BoxLayout.PAGE_AXIS));
        JMenuBar menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(menuBar);
        //Game menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        newMenuItem("New Game",gameMenu, this);
        newMenuItem("HighScore",gameMenu, this);
        
        newMenuItem("Exit",gameMenu, this);
        //about menu
        JMenu aboutMenu = new JMenu("More");
        menuBar.add(aboutMenu);
        //we need to create generic submenu creater
        newMenuItem("Help", aboutMenu, this);
        newMenuItem("About",aboutMenu, this);
        this.cardIcon = loadCardIcons();
                
    }
    
    private ImageIcon[] loadCardIcons(){
        ImageIcon icon[] = new ImageIcon[9];
        for(int i=0 ; i<9 ; i++){
            String fileName= "images/Card"+i+".jpeg";
            icon[i] = new ImageIcon(fileName);
        }
        return icon;
    }
    
    public JPanel makeCards(){
     
        JPanel panel = new JPanel(new GridLayout(4,4));
        panel.setBackground(Color.BLACK);
        //all card have same back side
        ImageIcon backIcon = this.cardIcon[8];
        CardController controller = new CardController();
        
        int cardsToAdd[] = new int[16];
        for (int i=0 ; i<8 ; i++){
            cardsToAdd[2*i] = i;
            cardsToAdd[2*i+1] = i;
        }
        //We need to randomize later
        randomizeCardArray(cardsToAdd);
        //make card object
        for(int i = 0 ; i<cardsToAdd.length ; i++){
            int num = cardsToAdd[i];
            Card newCard = new Card(controller, this.cardIcon[num], backIcon, num);
            panel.add(newCard);
        }
        return panel;
    }
    
    private void randomizeCardArray(int[] t) {
        Random randomizer = new Random();
        for (int i = 0 ; i<t.length ; i++){
            int d = randomizer.nextInt(t.length);
            //swap
            int s = t[d];
            t[d]=t[i];
            t[i]=s;
        }
    }
    
    private void newMenuItem(String string, JMenu menu, matchingGame listener)
    {
        JMenuItem newItem = new JMenuItem(string);
        newItem.setActionCommand(string);
        newItem.addActionListener((ActionListener)listener);
        menu.add(newItem);
    }
    public void newGame()
    {
        this.mainContentPane.removeAll();
        //make a new card set visible
        this.mainContentPane.add(makeCards());
        //show main window
        start = Instant.now();
        this.mainFrame.setVisible(true);
    }
    
   public Instant startTime(){
        return start;
    }
    public Instant endTime(){
        return end;
    }
    
    public void actionPerformed(ActionEvent arg0)
    {
        if(arg0.getActionCommand().equals("New Game")) 
            newGame();
        if(arg0.getActionCommand().equals("Exit"))
            System.exit(0);
        if(arg0.getActionCommand().equals("About")){
            JOptionPane.showMessageDialog(null,String.format(":::::::: Developers ::::::::"
                    + "\nDhirender Singh"
                    + "\nAnsh Pathania"
                    + "\nKartik Patkar"
                    + "\nRahul S Sharma"
                    + "\nEva Maria Rodrigues"
                    + "\nSafnu Samad"
                    + "\nPratap Shirsat"),"About Us",JOptionPane.PLAIN_MESSAGE);
        }
       if(arg0.getActionCommand().equals("Help")){
           JOptionPane.showMessageDialog(null,String.format("Tap on the tiles until you get a match for each Image."
                   + "\nTry to beat the ticking clock every time you start a new game!"),"Help",JOptionPane.PLAIN_MESSAGE);
       }
       if(arg0.getActionCommand().equals("HighScore")){
           Score s = new Score();
       }        
    }
}