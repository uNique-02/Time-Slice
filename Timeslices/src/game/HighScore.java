package game;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import com.game.Scores;

public class HighScore extends JFrame{
	
	private static final long serialVersionUID = 8523730927971119547L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel hghScore;
	MainMenu backtoMenu;
	List<Players> list;
	TimeOut timeout;
	Clip clip;
	
	String name[];
	int scores[];
	int dataCount;
	
	
	HighScore(String name[], int scores[], int dataCount) throws IOException{
		System.out.println("PASSED ON " + name[0] + " " + scores[0] + " " + dataCount);
		this.name = name;
		this.scores = scores;
		this.dataCount = dataCount;
		hghScore = new JPanel();
		initComponent(hghScore);
		this.setContentPane(hghScore);
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	} 
	
	public void backToMenu() throws IOException {
		backtoMenu = new MainMenu(name, scores, dataCount);
		this.dispose();
	}
	
	
	public void initComponent(JPanel hghPanel) throws IOException {
		hghPanel.setFocusable(true); 
		hghPanel.setPreferredSize(gameDimension);  
		hghPanel.setLayout(new BorderLayout());
		hghPanel.setVisible(true);
		
		try{
        AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/Wallpaper.wav"));
        clip = AudioSystem.getClip();
        clip.open(stream);
        //clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        //Thread.sleep(10000); // looping as long as this thread is alive
        }catch(Exception evt){
            System.out.println(evt);
        }
		
		JLabel menuButton = new JLabel();
		menuButton.setSize(450, 400);
        ImageIcon scoreBtnIcon = new ImageIcon(this.getClass().getResource("resources/Back.png"));
        Image scoreBtnIconCopy = scoreBtnIcon.getImage().getScaledInstance(menuButton.getWidth(), menuButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon tempscoreBtn = new ImageIcon(scoreBtnIconCopy);
        menuButton.setIcon(tempscoreBtn);
        menuButton.setBounds(-200, 560, 450, 60);
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        menuButton.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		try {
        			clip.stop();
					backToMenu();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
        		menuButton.setSize(menuButton.getWidth()+10, menuButton.getHeight()+10);
            }
        	@Override 
        	public void mouseExited (MouseEvent e){
        		menuButton.setSize(menuButton.getWidth()-10, menuButton.getHeight()-10);
        	}
        });
        hghPanel.add(menuButton);
      
		ImageIcon highScore = new ImageIcon(this.getClass().getResource("resources/highicon.png"));
		JLabel high = new JLabel(highScore);
	    high.setBounds(340, 40, 300, 100);
	    hghPanel.add(high); 
	    
	    JLabel flo = new JLabel();
	    ImageIcon flogo = new ImageIcon(this.getClass().getResource("resources/flogoo.png"));
        flo.setSize(100, 100);
        Image flogoCopy = flogo.getImage().getScaledInstance(flo.getWidth(), flo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon flogoImage = new ImageIcon(flogoCopy);
        flo.setIcon(flogoImage);
        flo.setBounds(870, 520, 100, 100);
        hghPanel.add(flo);
	    
		
		JLabel nameScore = new JLabel("  Name                                          Score");
		nameScore.setFont(new Font("Bebas Neue", Font.BOLD, 30));
		nameScore.setBounds(200, 160, 700, 50);
		hghPanel.add(nameScore);
		
		getHighScoreList(hghPanel);
		
		ImageIcon hsIcon = new ImageIcon(this.getClass().getResource("resources/BackGroundForLabels.png"));
        JLabel highScoreBG = new JLabel(hsIcon);
        highScoreBG.setBounds(190, 150, 600, 350);
        hghPanel.add(highScoreBG);
      
                
		ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/2.png"));
		JLabel screenImage = new JLabel(splashImage);
		hghPanel.add(screenImage, BorderLayout.CENTER);
		
		
		
	} 
	
	public void getHighScoreList(JPanel panel) throws IOException {
		int count = 6;
		/*File file = new File(this.getClass().getResource("resources/Scores.txt").getFile());
		list = new ArrayList<Players>();
		
		try (Scanner fileScanner = new Scanner(this.getClass().getResource("resources/Scores.txt").openStream())) {
			while(fileScanner.hasNextLine()) {
				String dataFromFile = fileScanner.nextLine();
				String[] tokens = dataFromFile.split(" ");
				int score = Integer.parseInt(tokens[0]);
				String name = tokens[1];
				Players player = new Players(score, name);
				list.add(player);
				++count;
			}
			fileScanner.close();
		}
		
		
		Collections.sort(list, new Comparator<Players>() {

			@Override
			public int compare(Players o1, Players o2) {
				return o1.getScore() - o2.getScore();
			}
			
		});
		
		Collections.reverse(list);
		*/
int i = dataCount, j, locx = 200, locy = 60, wid = 200, hght = 50, onlyTop6 = 6, temp=0; //i=3
		sortArray();
		while(i != 0 && onlyTop6 > 0) {
			j = dataCount - i;
			Scores first = new Scores(locx,locy,wid,hght,name[temp], j+4);
			Scores first1 = new Scores(locx*4 - 90,locy,wid,hght, j+4, scores[temp]+"");
			//System.out.println("" +list.get(j).getName()+ " " +list.get(j).getScore());
			panel.add(first);
			panel.add(first1);
			i--;
			temp++;
			--onlyTop6;
		}
	}
	void sortArray() {
		int tmpScore;
		String tmpName;
		for (int i = 0; i < dataCount; i++)   
		{  
		for (int j = i + 1; j < dataCount; j++)   
		{  
		int tmp = 0;  
		if (scores[i] < scores[j])   
		{  
		tmpScore = scores[i]; 
		tmpName = name[i];
		scores[i] = scores[j]; 
		name[i] = name[j];
		scores[j] = tmpScore; 
		name[j] = tmpName;
		}  
		}  
	}
}



class Scores extends JLabel{
	int locationx, locationy, width, height;
	String LeftText, RightText;
	int incrementY = 40, incrementX = 100;
	
	Scores(int locationx, int locationy, int width, int height, String LeftText, int left){
		locationx += 25;
		this.incrementY *= left;
		locationy += incrementY;
		this.setBounds(locationx, locationy, width, height);
		this.setText(" "+LeftText);
		this.setFont(new Font("Bebas Neue", Font.BOLD, 30));
	}
	Scores(int locationx, int locationy, int width, int height, int right, String RightText){
		this.incrementY *= right;
		locationy += incrementY;
		this.setBounds(locationx, locationy, width, height);
		this.setText(" "+RightText);
		this.setFont(new Font("Bebas Neue", Font.BOLD, 30));
		
	}
	
}

class Players{
	private int score;
	private String name;
	
	Players(int ranking, String name){
		this.score = ranking;
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}
	
}
}
