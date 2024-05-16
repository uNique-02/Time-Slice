package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CorrectGuess extends JFrame{

	private static final long serialVersionUID = -8367721205294907100L;
	final static int WIDTH = 983, HEIGHT = 622;
	final static Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
	JPanel readyingWords;
	JLabel txtField, txtField1, txtField2, forDelay;
	gamePlayGUI gamePlay;
	Timer timer;
	int index, index1 = 0; 
	int points, instanceCount;
	Stack stack;
	String message1 = "Congratulations!!", message2 = "Process is now terminated", message3 = "Loading next word", message4 = "...";
	String[] array;
	TimeOut timeOut;
	Clip clip;
	

	String[] name;
    int[] scores;
    int dataCount;
		
	CorrectGuess(int instanceCount, Stack stack, int points, String[] array, String[] name, int[] scores, int dataCount) throws IOException{
		this.name = name;
    	this.scores = scores;
    	this.dataCount = dataCount;
		
		this.instanceCount = instanceCount;
		this.stack = stack;
		this.points = points;
		this.array = array;
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
		
		delay(message4, forDelay);
		
	}
	
	public void checkArray() throws URISyntaxException {
		System.out.println("ARRAY LENGTH: " + array.length);
		if (array.length == instanceCount) {
			try {
				timeOut = new TimeOut(points, name, scores, dataCount);
				this.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			gotoGamePlay();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void gotoGamePlay() throws IOException, URISyntaxException {
		gamePlay = new gamePlayGUI(instanceCount, stack, points, array, name, scores, dataCount);
		System.out.println("Dumaan");
		this.dispose();
	}
	
	
	public void initComponent(JPanel panel) throws IOException{
		panel.setFocusable(true); 
		panel.setPreferredSize(gameDimension); 
		panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		 
		forDelay = new JLabel();
		forDelay.setText("");
		forDelay.setFont(new Font("Bebas Neue", 0, 80));
		forDelay.setForeground(new Color(255, 255, 255));
		forDelay.setBounds(20, 300, 900, 90);
		forDelay.setHorizontalAlignment(JTextField.CENTER);
		panel.add(forDelay);
		
		txtField = new JLabel();
		txtField.setText(message1);
		txtField.setFont(new Font("Bebas Neue", 0, 50));
		txtField.setForeground(new Color(255, 255, 255));
		txtField.setBounds(20, 110, 900, 110);
		txtField.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txtField);
		
		txtField1 = new JLabel();
		txtField1.setText(message2);
		txtField1.setFont(new Font("Bebas Neue", 0, 50));
		txtField1.setForeground(new Color(255, 255, 255));
		txtField1.setBounds(25, 180, 900, 110);
		txtField1.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txtField1);
		
		txtField2 = new JLabel();
		txtField2.setText(message3);
		txtField2.setFont(new Font("Bebas Neue", 0, 50));
		txtField2.setForeground(new Color(255, 255, 255));
		txtField2.setBounds(25, 240, 900, 110);
		txtField2.setHorizontalAlignment(JTextField.CENTER);
		panel.add(txtField2);
		
		ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/2.png"));
		JLabel screenImage = new JLabel(splashImage);
		panel.add(screenImage, BorderLayout.CENTER);
	}
	
	public void delay(String message, JLabel label) {
		index = 0; 
		
		if(timer != null && timer.isRunning()) return;
		
		timer = new Timer(500,new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(label.getText() + String.valueOf(message.charAt(index)));
				System.out.println("Index" +index);
				index++;
				
				if(index >= message.length()) { 
					try {
						checkArray();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					timer.stop();
				}
			}
			
		});
		timer.start();
	}
	
}
