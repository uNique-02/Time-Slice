package game;

import java.awt.BorderLayout;

import javax.sound.sampled.Clip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;

public class MainMenu extends JFrame{

	
	private static final long serialVersionUID = -398402896525103433L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	final static Dimension panel1Dimension = new Dimension(WIDTH - 200, HEIGHT - 200);
	static JPanel mainPanel;
	private HighScore hghscore;
	private HowToPlay hwtoplay;
	private BeforePlay befplay;
	Clip clip;
	
	String[] name;
	int[] scores;
	int dataCount;
	
	MainMenu(String[] name, int[] scores, int dataCount) throws IOException{
		this.name=name;
		this.scores=scores;
		this.dataCount = dataCount;
		this.setPreferredSize(gameDimension);
		mainPanel = new JPanel();
		initComponents(mainPanel);
		
		this.add(mainPanel);
		//mainPanel.add(initComponentPanel1(forGIFPanel));
		//mainPanel.add(initComponentPanel2(forMenuPanel));
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true); 
		
	}
	
	public void playPage() throws IOException {
		befplay = new BeforePlay(name, scores, dataCount);
		this.dispose();
	}
	public void highscorePage() throws IOException {
		hghscore = new HighScore(name, scores, dataCount);
		this.dispose();
	}
	
	public void howtoplayPage() throws IOException {
		
		hwtoplay = new HowToPlay(name, scores, dataCount);
		this.dispose();
	}
	
	public void exit() {
		this.dispose();
	}
	
	public void initComponents(JPanel mainPanel) throws IOException{
		System.out.println("PASSED ON " + name[0] + " " + scores[0] + " " + dataCount);
		
		
		mainPanel.setFocusable(true); 
		mainPanel.setPreferredSize(gameDimension);
		mainPanel.setLayout(new BorderLayout());
		
		
		ImageIcon logoGIF = new ImageIcon(this.getClass().getResource("resources/MenuGIF.gif"));
		Image logo = logoGIF.getImage().getScaledInstance(350, 280, Image.SCALE_DEFAULT);
		ImageIcon LogoGif = new ImageIcon(logo);
		JLabel ForLogo = new JLabel(LogoGif);
		ForLogo.setBounds(324, 20, 350, 260);
		mainPanel.add(ForLogo); 
		 
		JLabel play = new JLabel();
        play.setSize(500, 400);
        ImageIcon playIcon = new ImageIcon(this.getClass().getResource("resources/PlayButton.png"));
        Image playIconCopy = playIcon.getImage().getScaledInstance(play.getWidth(), play.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon tempPlay = new ImageIcon(playIconCopy);
        play.setIcon(tempPlay);
        play.setBounds(250, 280, 500, 60);
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
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
        
        play.addMouseListener(new MouseAdapter(){
        		public void mouseClicked(MouseEvent e) {
        			try {
        				clip.stop();
						playPage();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        			@Override
        			public void mouseEntered(MouseEvent me) {
        				play.setSize(play.getWidth()+10, play.getHeight()+10);
        		}
        			@Override 
        			public void mouseExited (MouseEvent e){
        				play.setSize(play.getWidth()-10, play.getHeight()-10);
        		}
        });
        
        JLabel howToBtn = new JLabel();
        howToBtn.setSize(500, 400);
        ImageIcon howToBtnIcon = new ImageIcon(this.getClass().getResource("resources/HowToPlayButton.png"));
        Image howToBtnIconCopy = howToBtnIcon.getImage().getScaledInstance(howToBtn.getWidth(), howToBtn.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon temphowToBtn = new ImageIcon(howToBtnIconCopy);
        howToBtn.setIcon(temphowToBtn);
        howToBtn.setBounds(250, 340, 500, 60);
        howToBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        howToBtn.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		try {
        			clip.stop();
        			howtoplayPage();
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
                howToBtn.setSize(howToBtn.getWidth()+10, howToBtn.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		howToBtn.setSize(howToBtn.getWidth()-10, howToBtn.getHeight()-10);
        	}
        });
        
        
        JLabel scoreBtn = new JLabel();
        scoreBtn.setSize(500, 400);
        ImageIcon scoreBtnIcon = new ImageIcon(this.getClass().getResource("resources/HighScoresButton.png"));
        Image scoreBtnIconCopy = scoreBtnIcon.getImage().getScaledInstance(scoreBtn.getWidth(), scoreBtn.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon tempscoreBtn = new ImageIcon(scoreBtnIconCopy);
        scoreBtn.setIcon(tempscoreBtn);
        scoreBtn.setBounds(250, 400, 500, 60);
        scoreBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        scoreBtn.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		try {
        			clip.stop();
					highscorePage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
                scoreBtn.setSize(scoreBtn.getWidth()+10, scoreBtn.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		scoreBtn.setSize(scoreBtn.getWidth()-10, scoreBtn.getHeight()-10);
        	}
        });
        
        
        JLabel exitBtn = new JLabel();
        exitBtn.setSize(500, 400);
        ImageIcon exitBtnIcon = new ImageIcon(this.getClass().getResource("resources/ExitButton.png"));
        Image exitBtnIconCopy = exitBtnIcon.getImage().getScaledInstance(exitBtn.getWidth(), exitBtn.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon tempexitBtn = new ImageIcon(exitBtnIconCopy);
        exitBtn.setIcon(tempexitBtn);
        exitBtn.setBounds(250, 460, 500, 60);
        exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        exitBtn.addMouseListener(new MouseAdapter(){
        	

			public void mouseClicked(MouseEvent e) {
                    exit();
        	}
        	@Override
            public void mouseEntered(MouseEvent me) {
                exitBtn.setSize(exitBtn.getWidth()+10, exitBtn.getHeight()+10);
            }
            
        	@Override 
        	public void mouseExited (MouseEvent e){
        		exitBtn.setSize(exitBtn.getWidth()-10, exitBtn.getHeight()-10);
        	}
        });
		
        
        
        mainPanel.add(play);
    	mainPanel.add(howToBtn);
    	mainPanel.add(scoreBtn);
    	mainPanel.add(exitBtn);
    	
    	
		ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/BlueBG.png"));
		JLabel screenImage1 = new JLabel(splashImage);
		screenImage1.setBounds(0, 0, WIDTH, HEIGHT);
		mainPanel.add(screenImage1, BorderLayout.CENTER);
			
	}

	public static URI getResource(String string) {
		// TODO Auto-generated method stub
	
		return null;
	}
	
	
}



