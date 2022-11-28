//note the count variable is not reset when a new game is pressed

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.io.IOException;
import java.util.*;
//btn1.setBackground(colors[index]
public class GameM implements ActionListener {
    JFrame frame = new JFrame("Memory Game");

    JPanel field = new JPanel();
    JLabel fieldLabel = new JLabel();
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct_screen = new JPanel();

    JButton[] btn = new JButton[20];
    JButton start = new JButton("Start");
    JButton over = new JButton("Exit");
    JButton easy = new JButton("Easy");
    JButton hard = new JButton("Hard");
    JButton inst = new JButton("Instructions");
    //JButton redo = new JButton("Play Again");
    JButton goBack = new JButton("Main Menu");

    Random randomGenerator = new Random();
    private boolean purgatory = false;
    int level=8;
    int score=0;

    String[] board;
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String[] a=new String[10];
    boolean eh=true;

    private final JTextField text = new JTextField(10);
    private final JTextArea instructM = new JTextArea("""
            When the game begins, the screen will be filled
            with pairs of buttons.
             Memorize their placement.
            Once you press any button, they will all clear.\s
             Your goal is to click the matching buttons in a row.
            When you finish that, you win.
            Every incorrect click gives you a point (those are bad).
             GOOD LUCK!\s
            for a single level: enter a level between 1 and 10,
            select easy or hard, then press start.""");
    //instructM.setEditable(false);
    //instructW.setEditable(false);
    //instructM.setLineWrap(true);
    //instructW.setWrapStyleWord(true);
    Icon questionIcon = new ImageIcon("question.png");
    public GameM(){
        frame.setSize(500,550);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        //start_screen.add(menu, BorderLayout.NORTH);
        //start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
       // menu3.add(mini, BorderLayout.CENTER);
        JLabel label = new JLabel("Enter level from 1 to 10");
        menu.add(label);
        menu.add(text);
        mini.add(easy, BorderLayout.NORTH);
        mini.add(hard, BorderLayout.NORTH);
        mini.add(inst, BorderLayout.SOUTH);


        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);
        easy.addActionListener(this);
        easy.setEnabled(true);
        hard.addActionListener(this);
        hard.setEnabled(true);
        inst.addActionListener(this);
        inst.setEnabled(true);


        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void setUpGame(Boolean what) throws IOException {
        int x = 8;
        level= x;
        clearMain();

        board = new String[2* x];
        for(int i = 0; i<(x *2); i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            btn[i].setSize(80,100);
            field.add(btn[i]);

        }

        String[] b = {":-D","X","O","-(*.*)-","<>","<(^-^)>","=>",";^P","ABC","123"};//harder version
        String[] c = {"7.png","8.png","9.png","10.png","J.png","Q.png","K.png","A.png"};//easier version

        if(what) a=c;//if what is true, make the game easy and use c
        else a=b;//otherwise make it hard and use b

        for(int i = 0; i< x; i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(x *2);
                    if(board[y]==null){
                        //btn[y].setText(a[i]);
                        btn[y].setIcon(new ImageIcon(a[i]));

                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    public void hideField(int x){//this sets all the boxes blank
        for(int i=0;i<(x*2);i++){
            /*if(boardQ[i]==0)*/ btn[i].setText("");
            btn[i].setIcon(questionIcon);
        }
        shown=false;
    }
    public void switchSpot(int i){//this will switch the current spot to either blank or what it should have
        if(!Objects.equals(board[i], "done")){//when a match is correctly chosen, it will no longer switch when pressed
            if(btn[i].getIcon()==questionIcon){//getText()==""){
                btn[i].setIcon(new ImageIcon(board[i]));
                //btn[i].setText(board[i]);
                //btn[i].setPressedIcon(catIcon);
                //shown=true;
            } else {
                //btn[i].setText("");
                btn[i].setIcon(questionIcon);
                //shown=false;
            }
        }
    }

    public void checkWin(){//checks if every spot is labeled as done
        for(int i=0;i<(level*2);i++){
            if (!Objects.equals(board[i], "done"))return;
        }
        winner();
    }
    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField("You Win"), BorderLayout.NORTH);
        end_screen.add(new TextField("Score: " + score), BorderLayout.SOUTH);
        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);

    }
    public void goToMainScreen(){
        new GameM();
    }
    public void createBoard(){//this is just gui stuff to show the board
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.white);
        field.requestFocus();
        fieldLabel.setText("Score: "+ score);
        field.add(fieldLabel);
    }
    public void clearMain(){//clears the main menu, so I can add the board or instructions
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }
    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            score++;
            fieldLabel.setText("Score: "+score);
            temp=(level*2);
            temp2=30;
            purgatory=false;
        }
        if(source==start){ //start sets level and difficulty and calls method to set up game
            try{
                level = Integer.parseInt(text.getText());
            } catch (Exception e){
                level=8;
            }

            try {
                setUpGame(eh);//level between 1 and 2, eh is true or false
            } catch (IOException e) {
                System.out.println("lalala");
            }
        }
        if(source==over){//quits
            System.exit(0);
        }
        if(source==inst){//this just sets the instruction screen
            clearMain();

            start_screen.add(instruct_screen, BorderLayout.NORTH);

            JPanel one = new JPanel();
            one.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel two = new JPanel();
            two.setLayout(new FlowLayout(FlowLayout.CENTER));
            instruct_screen.setLayout(new BorderLayout());
            instruct_screen.add(one, BorderLayout.NORTH);
            instruct_screen.add(two, BorderLayout.SOUTH);

            one.add(instructM);
            two.add(goBack);
            goBack.addActionListener(this);
            goBack.setEnabled(true);
        }
        if(source==goBack){//back to main screen
            frame.dispose();
            goToMainScreen();
        }
        if(source==easy){//sets the type. ex. if easy is clicked it turns blue and hard remains black
            eh=true;
            easy.setForeground(Color.BLUE);
            hard.setForeground(Color.BLACK);
        } else if(source==hard){
            eh=false;
            hard.setForeground(Color.BLUE);
            easy.setForeground(Color.BLACK);
        }

        for(int i =0;i<(level*2);i++){//gameplay when a button is pressed
            if(source==btn[i]){
                if(shown){
                    hideField(level);//if first time, hides field
                }else{//otherwise play
                    switchSpot(i);
                    if(temp>=(level*2)){
                        temp=i;
                    } else {
                        if((!Objects.equals(board[temp], board[i]))||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
                        }

                    }
                }

            }


        }


    }
    public static void main(String[] args) {
        new GameM();// Calling the class constructor.
    }

}