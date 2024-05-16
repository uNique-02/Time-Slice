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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class HowToPlay extends JFrame{

	private static final long serialVersionUID = 996222085917938666L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel hwtoplay;
	MainMenu backtoMenu;
	Clip clip;
	
	String[] name;
    int[] scores;
    int dataCount;
	
	public HowToPlay(String[] name, int[] scores, int dataCount) throws IOException {
		hwtoplay = new JPanel();
		initComponent(hwtoplay);
		this.setContentPane(hwtoplay);
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
	
	public void initComponent(JPanel howPlay) throws IOException {
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
		
		howPlay.setFocusable(true); 
		howPlay.setPreferredSize(gameDimension); 
		howPlay.setLayout(new BorderLayout());
		howPlay.setVisible(true);
		
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
        howPlay.add(menuButton);
		ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/main.png"));
		JLabel screenImage = new JLabel(splashImage);
		howPlay.add(screenImage, BorderLayout.CENTER);
		
		
	}
}
