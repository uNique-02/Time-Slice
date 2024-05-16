package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class BeforePlay extends JFrame{

	private static final long serialVersionUID = -8367721205294907100L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel readyingWords;
	JLabel txtField, txtField1;
	GamePlay gamePlay;
	Timer timer, timer1;
	int index = 0, index1 = 0;
	String message1 = "WORDS ARE GETTING"; 
	String message2 = "READY.....";
	
	String[] name;
    int[] scores;
    int dataCount;
	 
	
	BeforePlay(String[] name, int[] scores, int dataCount) throws IOException{
		this.name = name;
    	this.scores = scores;
    	this.dataCount = dataCount;
		readyingWords = new JPanel();
		initComponent(readyingWords);
		this.setPreferredSize(gameDimension);
		this.setContentPane(readyingWords);
		
		this.setTitle("Time Slice");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		slowPrint(message1, txtField);
		
	}

	public void gotoGamePlay() throws IOException, URISyntaxException {
		gamePlay = new GamePlay(name, scores, dataCount);
		this.dispose();
	}
	
	public void slowPrint(String message, JLabel label) {
		index = 0;
		
		if(timer != null && timer.isRunning()) return;
		timer = new Timer(250,new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(label.getText() + String.valueOf(message.charAt(index)));
				index++;
				if(index >= message.length()) {
					timer.stop();
					try {
						slowPrint1(message2, txtField1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		timer.start();
		
		
	}
	
	public void slowPrint1(String message, JLabel label) throws IOException {
		index = 0; 
		
		if(timer1 != null && timer.isRunning()) return;
		
		timer1 = new Timer(250,new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(label.getText() + String.valueOf(message.charAt(index)));
				index++;
				if(index >= message.length()) {
					timer1.stop();
					try {
						gotoGamePlay();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		timer1.start();
		
	}
	
	
	public void initComponent(JPanel panel) throws IOException{
		panel.setFocusable(true); 
		panel.setPreferredSize(gameDimension); 
		panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		JLabel flo = new JLabel();
		
		txtField = new JLabel();
		txtField.setText("");
		txtField.setFont(new Font("Bebas Neue", 0, 80));
		txtField.setForeground(new Color(255, 255, 255));
		txtField.setBounds(20, 130, 900, 90);
		txtField.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txtField);
		
		txtField1 = new JLabel();
		txtField1.setText("");
		txtField1.setFont(new Font("Bebas Neue", 0, 80));
		txtField1.setForeground(new Color(255, 255, 255));
		txtField1.setBounds(80, 250, 800, 90);
		txtField1.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txtField1);
		
		ImageIcon flogo = new ImageIcon(this.getClass().getResource("resources/flogoo.png"));
        flo.setSize(100, 100);
        Image flogoCopy = flogo.getImage().getScaledInstance(flo.getWidth(), flo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon flogoImage = new ImageIcon(flogoCopy);
        flo.setIcon(flogoImage);
        flo.setBounds(850, 480, 100, 100);
        panel.add(flo);
		
		ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/2.png"));
		JLabel screenImage = new JLabel(splashImage);
		panel.add(screenImage, BorderLayout.CENTER);
	}

	
}
	
	
	

