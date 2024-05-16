package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TimeOut extends JFrame{
	
	
	private static final long serialVersionUID = -2657143663685417860L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel timeOut;
	JLabel txt, txt1, txt2, txt3;
	JTextField playerName;
	String NameOfPlayer;
	MainMenu mainMenu;
	int score;
	String[] name = new String[100];
	int[] scores = new int[100];
	int dataCount=0;

	
	TimeOut(int score, String[] name, int[] scores, int dataCount) throws IOException{
		this.name = name;
		this.scores = scores;
		this.dataCount = dataCount;
		this.score = score;
		timeOut = new JPanel();
		initComponent(timeOut);
		this.setPreferredSize(gameDimension);
		this.setContentPane(timeOut);
		
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void gotoMenuPage() throws IOException {
		mainMenu = new MainMenu(name, scores, dataCount);
		this.dispose();
	}
	
	public void getScore() {
		Random rand = new Random();
		int upperBound = 10;
		int points = rand.nextInt(upperBound);
		score = points;
		
	} 
	
	
	public void initComponent(JPanel panel) throws IOException{
		panel.setFocusable(true); 
		panel.setPreferredSize(gameDimension); 
		panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		
		
		txt = new JLabel();
		txt.setText("TIME IS OUT!!");
		txt.setFont(new Font("Bebas Neue", Font.BOLD, 80));
		txt.setForeground(new Color(255, 255, 255));
		txt.setBounds(30, 30, 900, 90);
		txt.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txt);
		
		
		txt1 = new JLabel();
		txt1.setText("WORDS ARE NOW BEING");
		txt1.setFont(new Font("Bebas Neue", Font.BOLD, 40));
		txt1.setForeground(new Color(255, 255, 255));
		txt1.setBounds(30, 120, 900, 90);
		txt1.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txt1);
		
		
		txt2 = new JLabel();
		txt2.setText("TRANSFERRED BACK TO THE READY QUEUE");
		txt2.setFont(new Font("Bebas Neue", Font.BOLD, 37));
		txt2.setForeground(new Color(255, 255, 255));
		txt2.setBounds(30, 180, 900, 90);
		txt2.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txt2);
		
		
		txt3 = new JLabel();
		txt3.setText("SCORED " +score+ " POINTS");
		txt3.setFont(new Font("Californian FB", Font.BOLD, 20));
		txt3.setForeground(new Color(255, 255, 255));
		txt3.setBounds(-30, 305, 900, 90);
		txt3.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txt3);
		
		
		JLabel medalLabel = new JLabel();
		medalLabel.setSize(300,300);
		ImageIcon medalIcon = new ImageIcon(this.getClass().getResource("resources/Medal.png"));
		Image medalIconCopy = medalIcon.getImage().getScaledInstance(medalLabel.getWidth(), medalLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon FinalMedal = new ImageIcon(medalIconCopy);
		medalLabel.setIcon(FinalMedal);
		medalLabel.setBounds(160, 165, 300,300);
		panel.add(medalLabel);
		
		JLabel menuButton = new JLabel();
		menuButton.setSize(600, 500);
        ImageIcon scoreBtnIcon = new ImageIcon(this.getClass().getResource("resources/MainMenuButton.png"));
        Image scoreBtnIconCopy = scoreBtnIcon.getImage().getScaledInstance(menuButton.getWidth(), menuButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon tempscoreBtn = new ImageIcon(scoreBtnIconCopy);
        menuButton.setIcon(tempscoreBtn);
        menuButton.setBounds(180, 410, 500, 60);
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        menuButton.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		try {
        			String userName = playerName.getText();
					if(userName.equals("") || userName.equals("ENTER NAME")) {
						
						System.out.println("Warning");
						JOptionPane.showMessageDialog(panel, "Don't be nameless.", "Eror", JOptionPane.WARNING_MESSAGE);
						
					} else {
						NameOfPlayer = userName;
						System.out.println("Dumaan ba?");
				 		playerName.setEnabled(false);
				 		ScoreToFile();
				 		//clip.stop();
						gotoMenuPage();
					}
					 
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
        panel.add(menuButton);
        
		
		
		playerName = new JTextField("ENTER NAME");
		playerName.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(51,0,51)), BorderFactory.createEmptyBorder(0,10,0,0)));
		playerName.setBackground(new Color(0, 0, 51));
		playerName.setFont(new Font("Californian FB", 1, 18)); 
		playerName.setForeground(new Color(204, 255, 255));
		playerName.setHorizontalAlignment(JTextField.LEFT);
		playerName.setBounds(327, 280, 330, 50);
		playerName.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				playerName.setText("");
			}
		});	
		panel.add(playerName);
		
		
		ImageIcon timeOutBack = new ImageIcon(this.getClass().getResource("resources/2.png"));
		JLabel screenImage = new JLabel(timeOutBack);
		panel.add(screenImage, BorderLayout.CENTER);
		
	}
	
		public void ScoreToFile() {
			
			/*File file = new File("resources/Scores.txt");
			
			//writing new high scores to a file
			if(NameOfPlayer != null) { 
			String entryToFile = "" +score+ " " +NameOfPlayer;
			try (FileWriter fwriter = new FileWriter(file, true);
				 BufferedWriter bwriter = new BufferedWriter(fwriter);
				 PrintWriter pwriter = new PrintWriter(bwriter);){
					pwriter.println();
					pwriter.print(entryToFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}*/
			if(NameOfPlayer!= null) {
				name[dataCount]=NameOfPlayer;
				scores[dataCount]=score;
				dataCount++;
			}
			
			int index = 0;
	
		}
}


