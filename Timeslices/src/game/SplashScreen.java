package game;

import java.awt.BorderLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;



public class SplashScreen extends JFrame{

	private static final long serialVersionUID = 283064228933448675L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	static JPanel splashPanel;
	static JProgressBar progressBar;
	static MainMenu mainMenu;
	Clip clip;
	
	String[] name = new String[100];
    int[] scores = new int[100];
    int dataCount = 0;
	
	SplashScreen() throws IOException{ 
		name[0] = " ";
		scores[0] = 0;
		splashPanel = new JPanel();
		splashPanel.setFocusable(true);
		splashPanel.setPreferredSize(gameDimension); 
		splashPanel.setLayout(new BorderLayout());
	
		 
		ImageIcon splashLogo = new ImageIcon(this.getClass().getResource("resources/Logo.png"));
		Image scaleImage = splashLogo.getImage().getScaledInstance(450, 330, Image.SCALE_DEFAULT);
		ImageIcon splashLogo1 = new ImageIcon(scaleImage);
		JLabel screenImage1 = new JLabel(splashLogo1);
		screenImage1.setBounds(180, -5, 600, 600);
		splashPanel.add(screenImage1); 
		
		ImageIcon splashBackGround = new ImageIcon(this.getClass().getResource("resources/SplashScreenGIF.gif"));
		Image splashBG = splashBackGround.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
		ImageIcon splashBGgif = new ImageIcon(splashBG);
		JLabel screenImage = new JLabel(splashBGgif);
		//screenImage.setSize(gameDimension);
		splashPanel.add(screenImage, BorderLayout.CENTER); 
		 
		progressBar = new JProgressBar(); 
		progressBar.setForeground(new Color(0,128,128));
		progressBar.setStringPainted(true);
		//screenImage.add(progressBar); 
		splashPanel.add(progressBar, BorderLayout.SOUTH);
		splashPanel.setPreferredSize(gameDimension);
		
		this.setContentPane(splashPanel);
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true); 
		 
		int i = 0;
		try { 
			while(i <= 100) {
				progressBar.setValue(i); 
				Thread.sleep(50);
				i++;
			}  
		}	catch(Exception e) {
			e.printStackTrace();
		}
		
		MainMenu mainMenu = new MainMenu(name, scores, dataCount);
		this.dispose();
		
	}	
}
