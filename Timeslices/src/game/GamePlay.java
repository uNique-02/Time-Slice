package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import game.characterBtn;
import game.gamePlayGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;


public class GamePlay {

    int count=0;
    String[] name;
    int[] scores;
    int dataCount;
    
    GamePlay(String[] name, int[] scores, int dataCount) throws IOException, URISyntaxException{
    	this.name = name;
    	this.scores = scores;
    	this.dataCount = dataCount;
        new gamePlayGUI(name, scores, dataCount);
        }
    }


class gamePlayGUI implements KeyListener{
    JFrame gamePlayFrame = new JFrame();
    TimeOut timeout;
    int count, guessesArrayIndex=0;
    int arrayCount;
    String[] array;
    char[] guessWord;
    char clickedValue;
    int index, points=0, characCount;
    JLabel[] labelArray = new JLabel[20];
    char userGuess;
    int correctCount, instanceCount, errorCount;
    Stack<String> stack;
    int[] indexList;
    JLabel time_slice = new JLabel();
    char[] guessesArray = new char[20];
    Clip clip, clip1;
    CorrectGuess correctPage;
    Timer timer;
    //AudioInputStream stream;
    
    characterBtn[] btnArray = new characterBtn[26];
    
    String[] name;
    int[] scores;
    int dataCount;

    gamePlayGUI(String[] name, int[] scores, int dataCount) throws URISyntaxException {
    	this.name = name;
    	this.scores = scores;
    	this.dataCount = dataCount;
        retrieveArrayOfCharac(0);
        initComponents();
    }
    gamePlayGUI(int instanceCount, Stack<String> stack, int points, String[] array, String[] name, int[] scores, int dataCount) throws URISyntaxException {
    	this.name = name;
    	this.scores = scores;
    	this.dataCount = dataCount;
    	errorCount=0;
        this.stack = stack; 
        this.points = points;
        this.instanceCount=instanceCount;
        this.array = array;
        retrieveArrayOfCharac(instanceCount);
        initComponents();
    }
    
    public void gotoTimeOut() throws IOException {
    	clip1.stop();
    	timeout = new TimeOut(points, name, scores, dataCount);
    	gamePlayFrame.dispose();
    	gamePlayFrame.setResizable(false);
    }
    
    void retrieveArrayOfCharac(int instanceCount) throws URISyntaxException{
        
        if(instanceCount==0){
            array = shuffleArray(createArray());
            stack = new Stack<String>();
            for(int i=0; i<array.length; i++){
            stack.push(array[i]);
            System.out.println(array[i]);
        }
        }
        
        System.out.println("Instance count: " + instanceCount);
        System.out.println("Stack length: " + stack.size());
       
        guessWord = toCharArray(stack.pop().toString());
        arrayCount=guessWord.length;
        characCount = guessWord.length;
        for(int i=0; i<guessWord.length; i++){
            count++;
            System.out.println("KIM: " + guessWord[i]);
        }
    }
    
    static char[] toCharArray(String str){
        char[] ch = str.toCharArray();
        return ch;
    }
    
    public String[] createArray() throws URISyntaxException{
        java.util.List<String> listOfStrings
            = new ArrayList<String>();
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResource("resources/source.txt").openStream()));
        	String line;
        	while((line = reader.readLine()) != null) {
        	    listOfStrings.add(line);
        	    System.out.println(line);
        	}
            reader.close();
        } catch (FileNotFoundException ex) {
        	System.out.print("hello");
            //Logger.getLogger(gamePlayGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String[] array1
            = listOfStrings.toArray(new String[0]);
        return array1;
    }
    static String[] shuffleArray(String[] ar)
  {
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      String a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
    return ar;
  }
    
    void createBlankSpaceArray(){
        int x_odd_center=461;
        int x_even_center=491;
        int y=280;
        
        int x_left;
        int x_right;
        
        int index_odd_center = (int) count/2;
        int index_even_center = (count/2)-1;
        int index_left = index_odd_center-1;
        int index_right=index_odd_center+1;
        
        if(count%2==0){
            x_left=x_even_center-65;
            x_right=x_even_center+5;
            index_left=index_even_center;
            index_right=index_even_center+1;
           
            for(int i=0; i<count; i++){
                if(i%2==0){
                    gamePlayFrame.add(new blankspaceIcon(x_left-20, y+20));
                    labelArray[index_left] = new blankspace(x_left, y, Character.toString(guessWord[index_left]).toUpperCase());
                    labelArray[index_left].setVisible(false);
                    gamePlayFrame.add(labelArray[index_left]);
                    
                    System.out.println("Even left: " + index_left);
                    x_left-=70;
                    index_left-=1;
                }
                else{
                    gamePlayFrame.add(new blankspaceIcon(x_right-20, y+20));
                    labelArray[index_right] = new blankspace(x_right, y, Character.toString(guessWord[index_right]).toUpperCase());
                    labelArray[index_right].setVisible(false);
                    gamePlayFrame.add(labelArray[index_right]);
                    
                    System.out.println("Even right: " + index_right);
                    x_right+=70;
                    index_right+=1;
                }
            }
        }else{
            x_left=x_odd_center-70;
            x_right=x_odd_center+70;
            for(int i=0; i<count; i++){
                if(i==0){
                    gamePlayFrame.add(new blankspaceIcon(x_odd_center-20, y+20));
                    labelArray[index_odd_center] = new blankspace(x_odd_center, y, Character.toString(guessWord[index_odd_center]).toUpperCase());
                    
                    labelArray[index_odd_center].setVisible(false);
                    gamePlayFrame.add(labelArray[index_odd_center]);
                    
                    System.out.println("Odd center: " + index_odd_center);
                    System.out.println("GUESS: " + guessWord[index_odd_center]);
                }
                else if(i%2==0){
                    gamePlayFrame.add(new blankspaceIcon(x_left-20, y+20));
                    labelArray[index_left] = new blankspace(x_left, y, Character.toString(guessWord[index_left]).toUpperCase());
                    
                    labelArray[index_left].setVisible(false);
                    gamePlayFrame.add(labelArray[index_left]);
                    x_left-=70;
                    System.out.println("Odd left: " + index_left);
                    System.out.println("GUESS: " + guessWord[index_left]);
                    
                    index_left-=1;
                    
                    
                }
                else{
                    gamePlayFrame.add(new blankspaceIcon(x_right-20, y+20));
                    labelArray[index_right] = new blankspace(x_right, y, Character.toString(guessWord[index_right]).toUpperCase());
                    
                    labelArray[index_right].setVisible(false);
                    gamePlayFrame.add(labelArray[index_right]);
                    
                    System.out.println("Odd right: " + index_right);
                    System.out.println("GUESS: " + guessWord[index_right]);
                    x_right+=70;
                    index_right+=1;
                }
            }
        }
        
    }
    
    void initButtons(){
       for(int i=0, j=1; i<btnArray.length; i++, j++){
           System.out.println("Index: " + i + " button string: " + btnArray[i].getX());
            ImageIcon buttonIcon = new ImageIcon(this.getClass().getResource("Buttons/"+j+".png"));
            Image buttonIconCopy = buttonIcon.getImage().getScaledInstance(btnArray[i].getWidth(), btnArray[i].getHeight(), Image.SCALE_SMOOTH);
            ImageIcon buttonImage = new ImageIcon(buttonIconCopy);
            btnArray[i].setIcon(buttonImage);
        }
            
    }
    
    void initComponents(){
        int i=0;
        gamePlayFrame.setSize(983, 622);
        gamePlayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePlayFrame.setTitle("Time Slice");
        gamePlayFrame.setLayout(null);
        
        gamePanel gamePlayPanel = new gamePanel();
        gamePlayPanel.setLayout(null);
        gamePlayFrame.setContentPane(gamePlayPanel);
        gamePlayFrame.addKeyListener(this);
        
        JLabel point = new JLabel();
        point.setText("Points: " + points);
        point.setSize(40, 30);
        point.setFont(new Font("Bebas Neue", Font.BOLD, 25));
        point.setBounds(800, 30, 150, 40);
        point.setForeground(Color.WHITE);
        gamePlayFrame.add(point);
        gamePlayFrame.setLocationRelativeTo(null);
        
        createBlankSpaceArray();
        
        characterBtn btnA = new characterBtn();
        btnArray[i] = btnA;
        //btnA.setText("A");
        btnA.setBounds(171, 345, btnA.getWidth(), btnA.getHeight());
        
        btnA.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnA, 'A');
        		}
        });
        
        characterBtn btnB = new characterBtn();
        btnArray[++i] = btnB;
        //btnB.setText("B");
        btnB.setBounds(236, 345, btnA.getWidth(), btnA.getHeight());
        
        btnB.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnB, 'B');
        		}
        });
        
        characterBtn btnC = new characterBtn();
        btnArray[++i] = btnC;
        //btnC.setText("C");
        btnC.setBounds(301, 345, btnA.getWidth(), btnA.getHeight());
        btnC.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnC, 'C');
        		}
        });
        
        characterBtn btnD = new characterBtn();
        btnArray[++i] = btnD;
        //btnD.setText("D");
        btnD.setBounds(366, 345, btnA.getWidth(), btnA.getHeight());
        btnD.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnD, 'D');
        		}
        });
        
        characterBtn btnE = new characterBtn();
        btnArray[++i] = btnE;
        //btnE.setText("E");
        btnE.setBounds(431, 345, btnA.getWidth(), btnA.getHeight());
        btnE.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnE, 'E');
        		}
        });
        
        characterBtn btnF = new characterBtn();
        btnArray[++i] = btnF;
        //btnF.setText("F");
        btnF.setBounds(496, 345, btnA.getWidth(), btnA.getHeight());
        btnF.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnF, 'F');
        		}
        });
        
        characterBtn btnG = new characterBtn();
        btnArray[++i] = btnG;
        //btnG.setText("G");
        btnG.setBounds(561, 345, btnA.getWidth(), btnA.getHeight());
        btnG.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnG, 'G');
        		}
        });
        
        characterBtn btnH = new characterBtn();
        btnArray[++i] = btnH;
        //btnH.setText("H");
        btnH.setBounds(626, 345, btnA.getWidth(), btnA.getHeight());
        btnH.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnH, 'H');
        		}
        });
        
        characterBtn btnI = new characterBtn();
        btnArray[++i] = btnI;
        //btnI.setText("I");
        btnI.setBounds(691, 345, btnA.getWidth(), btnA.getHeight());
        btnI.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnI, 'I');
        		}
        });
        
        characterBtn btnJ = new characterBtn();
        btnArray[++i] = btnJ;
        //btnJ.setText("J");
        btnJ.setBounds(756, 345, btnA.getWidth(), btnA.getHeight());
        btnJ.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnJ, 'J');
        		}
        });

        characterBtn btnK = new characterBtn();
        btnArray[++i] = btnK;
        //btnK.setText("K");
        btnK.setBounds(171, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnK);
        btnK.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnK, 'K');
        		}
        });
        
        characterBtn btnL = new characterBtn();
        btnArray[++i] = btnL;
        //btnL.setText("L");
        btnL.setBounds(236, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnL);
        btnL.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnL, 'L');
        		}
        });
        
        characterBtn btnM = new characterBtn();
        btnArray[++i] = btnM;
        //btnM.setText("M");
        btnM.setBounds(301, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnM);
        btnM.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnM, 'M');
        		}
        });
        
        characterBtn btnN = new characterBtn();
        btnArray[++i] = btnN;
        //btnN.setText("N");
        btnN.setBounds(366, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnN);
        btnN.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnN, 'N');
        		}
        });
        
        characterBtn btnO = new characterBtn();
        btnArray[++i] = btnO;
        //btnO.setText("O");
        btnO.setBounds(431, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnO);
        btnO.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnO, 'O');
        		}
        });
        
        characterBtn btnP = new characterBtn();
        btnArray[++i] = btnP;
        //btnP.setText("P");
        btnP.setBounds(496, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnP);
        btnP.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnP, 'P');
        		}
        });
        
        characterBtn btnQ = new characterBtn();
        btnArray[++i] = btnQ;
        //btnQ.setText("Q");
        btnQ.setBounds(561, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnQ);
        btnQ.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnQ, 'Q');
        		}
        });
        
        characterBtn btnR = new characterBtn();
        btnArray[++i] = btnR;
        //btnR.setText("R");
        btnR.setBounds(626, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnR);
        btnR.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnR, 'R');
        		}
        });
        
        characterBtn btnS = new characterBtn();
        btnArray[++i] = btnS;
        //btnS.setText("S");
        btnS.setBounds(691, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnS);
        btnS.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnS, 'S');
        		}
        });
        
        characterBtn btnT = new characterBtn();
        btnArray[++i] = btnT;
        //btnT.setText("T");
        btnT.setBounds(756, 400, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnT);
        btnT.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnT, 'T');
        		}
        });
        
        characterBtn btnU = new characterBtn();
        btnArray[++i] = btnU;
        //btnU.setText("U");
        btnU.setBounds(296, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnU);
        btnU.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnU, 'U');
        		}
        });
        
        characterBtn btnV = new characterBtn();
        btnArray[++i] = btnV;
        //btnV.setText("V");
        btnV.setBounds(361, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnV);
        btnV.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnV, 'V');
        		}
        });
        
        characterBtn btnW = new characterBtn();
        btnArray[++i] = btnW;
        //btnW.setText("W");
        btnW.setBounds(426, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnW);
        btnW.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnW, 'W');
        		}
        });
        
        characterBtn btnX = new characterBtn();
        btnArray[++i] = btnX;
        //btnX.setText("X");
        btnX.setBounds(491, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnX);
        btnX.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnX, 'X');
        		}
        });
        
        characterBtn btnY = new characterBtn();
        btnArray[++i] = btnY;
        //btnY.setText("Y");
        btnY.setBounds(556, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnY);
        btnY.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnY, 'Y');
        		}
        });
        
        characterBtn btnZ = new characterBtn();
        btnArray[++i] = btnZ;
        //btnZ.setText("Z");
        btnZ.setBounds(621, 455, btnA.getWidth(), btnA.getHeight());
        //gamePlayPanel.add(btnZ);
        btnZ.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                                mouseEvent(btnZ, 'Z');
        		}
        });
        
        
        ImageIcon slice = new ImageIcon(this.getClass().getResource("resources/Whole.png"));
        time_slice.setSize(500, 350);
        
        Image imageCopy = slice.getImage().getScaledInstance(570, 450, Image.SCALE_DEFAULT);
		ImageIcon image = new ImageIcon(imageCopy);
		time_slice.setIcon(image);
		time_slice.setBounds(200, 50, 500, 350);
        
        
   
        gamePlayPanel.add(time_slice);
        initButtons();

        
        gamePlayPanel.add(btnA);
        gamePlayPanel.add(btnB);
        gamePlayPanel.add(btnC);
        gamePlayPanel.add(btnD);
        gamePlayPanel.add(btnE);
        gamePlayPanel.add(btnF);
        gamePlayPanel.add(btnG);
        gamePlayPanel.add(btnH);
        gamePlayPanel.add(btnI);
        gamePlayPanel.add(btnJ);
        gamePlayPanel.add(btnK);
        gamePlayPanel.add(btnL);
        gamePlayPanel.add(btnM);
        gamePlayPanel.add(btnN);
        gamePlayPanel.add(btnO);
        gamePlayPanel.add(btnP);
        gamePlayPanel.add(btnQ);
        gamePlayPanel.add(btnR);
        gamePlayPanel.add(btnS);
        gamePlayPanel.add(btnT);
        gamePlayPanel.add(btnU);
        gamePlayPanel.add(btnV);
        gamePlayPanel.add(btnW);
        gamePlayPanel.add(btnX);
        gamePlayPanel.add(btnY);
        gamePlayPanel.add(btnZ);
        
        JLabel CPU = new JLabel();
        JLabel queue = new JLabel();
        JLabel flo = new JLabel();
        
        ImageIcon CPUIcon = new ImageIcon(this.getClass().getResource("resources/CPU.png"));
        CPU.setSize(160, 170);
        Image CPUIconCopy = CPUIcon.getImage().getScaledInstance(CPU.getWidth(), CPU.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon CPUImage = new ImageIcon(CPUIconCopy);
        CPU.setIcon(CPUImage);
        CPU.setBounds(650, 60, 160, 170);
        
        ImageIcon queueIcon = new ImageIcon(this.getClass().getResource("resources/ReadyQueue.png"));
        queue.setSize(200, 150);
        Image queueIconCopy = queueIcon.getImage().getScaledInstance(queue.getWidth(), queue.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon queueImage = new ImageIcon(queueIconCopy);
        queue.setIcon(queueImage);
        queue.setBounds(120, 80, 200, 150);
        
        
        ImageIcon flogo = new ImageIcon(this.getClass().getResource("resources/flogoo.png"));
        flo.setSize(100, 100);
        Image flogoCopy = flogo.getImage().getScaledInstance(flo.getWidth(), flo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon flogoImage = new ImageIcon(flogoCopy);
        flo.setIcon(flogoImage);
        flo.setBounds(850, 480, 100, 100);
        
        try{
        AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/Wallpaper.wav"));
        clip1 = AudioSystem.getClip();
        clip1.open(stream);
        //clip.start();
        clip1.loop(Clip.LOOP_CONTINUOUSLY);
        //Thread.sleep(10000); // looping as long as this thread is alive
        }catch(Exception evt){
            System.out.println(evt);
        }

        gamePlayFrame.add(CPU);
        gamePlayFrame.add(queue);
        gamePlayFrame.add(flo);
        
        btnZ.setVisible(true);
        gamePlayFrame.setVisible(true);
       
    }
    
    boolean ifExists(char ch) {
        int j=0;
	for(int i=0; i<guessWord.length; i++) {
            
		if(Character.toUpperCase(guessWord[i])== Character.toUpperCase(ch)) {
                    System.out.println("K: " + Character.toUpperCase(guessWord[i]) + " M:" + Character.toUpperCase(ch));
			//indexList[j]=i;
                        j++;
		}
	}
        indexList = new int[j];
        j=0;
        for(int i=0; i<guessWord.length; i++) {
		if(Character.toUpperCase(guessWord[i])== Character.toUpperCase(ch)) {
                    System.out.println("K: " + Character.toUpperCase(guessWord[i]) + " M:" + Character.toUpperCase(ch));
			indexList[j]=i;
                        j++;
		}
	}
        if(j>1) {
        	arrayCount-=(j-1);
        }
        if(j!=0){ 
            return true;
        }
	return false;
}
    
    boolean ifExistsinGuessArray(char ch){
        int j=0;
	for(int i=0; i<guessesArray.length; i++) {
            
		if(Character.toUpperCase(guessesArray[i])== Character.toUpperCase(ch)) {
                    System.out.println("K: " + Character.toUpperCase(guessesArray[i]) + " M:" + Character.toUpperCase(ch));
                        j++;
		}
	}
        j=0;
        for(int i=0; i<guessesArray.length; i++) {
		if(Character.toUpperCase(guessesArray[i])== Character.toUpperCase(ch)) {
                    System.out.println("K: " + Character.toUpperCase(guessesArray[i]) + " M:" + Character.toUpperCase(ch));
                        j++;
		}
	}
        
        if(j!=0){ 
            return true;
        }
	return false;
    }

    void mouseEvent(JButton btn, char userGuess){
    	System.out.println("KIM COUNT: " + arrayCount);
        int x = (int) userGuess;

        			if(ifExists(userGuess)!=false) {
                                        guessesArray[guessesArrayIndex]=userGuess;
                                        guessesArrayIndex++;
                                        characCount-=indexList.length-1;
                                        
                                        
                                        int temp  = x-12;
                                        ImageIcon buttonIcon = new ImageIcon(this.getClass().getResource("Buttons/"+temp+".png"));
                                        Image buttonIconCopy = buttonIcon.getImage().getScaledInstance(btnArray[x-65].getWidth(), btnArray[x-65].getHeight(), Image.SCALE_SMOOTH);
                                        ImageIcon buttonImage = new ImageIcon(buttonIconCopy);
                                        btnArray[x-65].setIcon(buttonImage);
                                    try{
                                    	for(int j=0; j<indexList.length; j++) {
                                         System.out.println("UserGuess: " + userGuess + "index: " + indexList[j]);
                                        labelArray[indexList[j]].setVisible(true);
                                        System.out.println("Bool: " + ifExists(userGuess)); 
                                        guessWord[indexList[j]]=0;
                                        correctCount++;
                             
                                        try{
                                        AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/button2.wav"));
                                        clip = AudioSystem.getClip();
                                        clip.open(stream);
                                        clip.start();
                                        }catch(Exception evt){
                                            System.out.println(evt);
                                        }
                                        
                                        for(int i=0; i<btn.getMouseListeners().length; i++){
                                            btn.removeMouseListener(btn.getMouseListeners()[i]);
                                        }
                                        
                                        System.out.println("Charac Count: " + characCount);
                                        System.out.println("Correct Count: " + correctCount);
                                        
                                        if(correctCount==characCount){
                                        	timer = new Timer(500, new AbstractAction() {
                                        		
												private static final long serialVersionUID = 1L;

												@Override 
                                        		public void actionPerformed(ActionEvent e) {
                                        			try {
                                        				clip1.stop();
                                        				gotoCorrectGuess();
                                        				timer.stop();
                                        			}catch(Exception e1) {
                                        				e1.printStackTrace();
                                        			}
                                        		}
                                        	});
                                            /*SwingUtilities.getRoot(btn).setVisible(false);
                                            new gamePlayGUI(++instanceCount, stack, ++points);*/
                                        	timer.start();
                                        }
                                        
                                    	}
                                    }catch(Exception e){
                                        
                                    } 
                                       
        			}else {
                                    if(ifExistsinGuessArray(userGuess)== false){
                                        guessesArray[guessesArrayIndex]=userGuess;
                                        guessesArrayIndex++;
                                        System.out.println(ifExists(userGuess));
                                        int temp  = x-38;
                                        ImageIcon buttonIcon = new ImageIcon(this.getClass().getResource("Buttons/"+temp+".png"));
                                        Image buttonIconCopy = buttonIcon.getImage().getScaledInstance(btnArray[x-65].getWidth(), btnArray[x-65].getHeight(), Image.SCALE_SMOOTH);
                                        ImageIcon buttonImage = new ImageIcon(buttonIconCopy);
                                        btnArray[x-65].setIcon(buttonImage);
                                        
                                        try{
                                            
                                        AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/button3.wav"));
                                        clip = AudioSystem.getClip();
                                        clip.open(stream);
                                        clip.start();
                                        }catch(Exception evt){
                                            System.out.println(evt);
                                        }
                                        
                                        for(int i=0; i<btn.getMouseListeners().length; i++){
                                            btn.removeMouseListener(btn.getMouseListeners()[i]);
                                        }
                                        btn.setForeground(Color.red);
                                        errorCount++;
                                        if(errorCount>=6){
                                            
                                        	try {
                                        		clip1.stop();
												gotoTimeOut();
												clip1.stop();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
                                            System.out.println("ERROR COUNT REACHED 6");
                                            
                                            
                                        }else{
                                        ImageIcon slice = new ImageIcon(this.getClass().getResource("sliceElements/"+errorCount+"_slice.png"));
                                        Image imageCopy = slice.getImage().getScaledInstance(570, 450, Image.SCALE_DEFAULT);
                        				ImageIcon image = new ImageIcon(imageCopy);
                        				time_slice.setIcon(image);
                        				time_slice.setBounds(200, 50, 400, 350);
                                        }
                                    }
        			}
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char pressed = e.getKeyChar();
        int temp = (int) Character.toUpperCase(pressed);
        int btnIndex = temp-65;
        mouseEvent(btnArray[btnIndex], Character.toUpperCase(pressed));
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
}
    public void gotoCorrectGuess() throws IOException{
    	correctPage = new CorrectGuess(++instanceCount, stack, ++points, array, name, scores, dataCount);
    	gamePlayFrame.dispose();
    }
}
    class characterBtn extends JButton{
        characterBtn(){
            this.setSize(55, 45);
            this.setFont(new Font("Bebas Neue", Font.BOLD, 25));
            this.setBorder(null);
            this.setFocusPainted(false);
            this.setFocusable(false);
            
            this.addMouseListener(new MouseAdapter(){
            	
            	public void mouseClicked(MouseEvent e) {
        			String charGuess = ((JButton)e.getSource()).getText();
        			System.out.println(charGuess);
        			
        		}
        		
        			@Override 
        			public void mouseEntered(MouseEvent e) {
        				//this.setBackground(Color.gray);
        				e.getComponent().setBackground(Color.gray);
        		}
        			@Override 
        			public void mouseExited (MouseEvent e){
        				e.getComponent().setBackground(null);
        }
    });}}

    //Label to contain characters of the word to be guessed.
    class blankspace extends JLabel{
		private static final long serialVersionUID = 1L;

		blankspace(int x, int y, String str){
            this.setFont(new Font("Bebas Neue", Font.BOLD, 40));
            this.setText(str);
            this.setForeground(Color.WHITE);
            this.setBounds(x, y, 60, 40);
        }
    }
    //Label to contain blankspaces using icon
    class blankspaceIcon extends JLabel{
		private static final long serialVersionUID = 1L;
                
		blankspaceIcon(int x, int y){
            
        ImageIcon bsIcon = new ImageIcon(this.getClass().getResource("resources/blankspace.png"));
        this.setBounds(x, y, 80, 40);
        Image bsIconCopy = bsIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon bsImage = new ImageIcon(bsIconCopy);
        this.setIcon(bsImage);
            
        }
    }
    
    class gamePanel extends JPanel{
        
    	//private static final long serialVersionUID = 1L;

    	public void paintComponent(Graphics g){
            ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/background.png"));
            this.setLayout(new BorderLayout());
            g.drawImage(icon.getImage(), 0, 0, 1000, 600, null);
        }
    	
    }
    
    
    
    