/**
 * @author Jacob Hill
 * 
 * 
 * Game is now accessed by running the Title class
 * A GUI that displays the rules and controls has been added, and we fixed the collision
 * Also fixed bugs in LevelThree
 * Couldn't find a way to cut down on code using inheritance, but tried to clean it up as much as possible
 */


package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class Title extends JPanel implements KeyListener {
	BufferedImage title_background =null;
	private JFrame intro_screen = new JFrame();
	boolean showframe = true;
	public Title() {
		try {
			title_background = ImageIO.read(new File("Introscreen.jpg"));
		} catch (IOException e) {
	      }
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	/**
	 * Method to create the GUI for title screen
	 */
	public void create_title_frame() {
		Title titleGUI = new Title();
		intro_screen.setTitle("Level One");
		intro_screen.setSize(500,800); // size of JFrame
		intro_screen.setVisible(true);
		intro_screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		intro_screen.add(titleGUI);
	}
	
	/**
	 * Closes the GUI
	 */
	public void close_title_GUI() {
		intro_screen.dispose();
	}
	
	/**
	 * @param A Graphics object, g
	 * Paints the title screen with the title and controls/rules
	 */
	public void paintComponent(Graphics g) {
	//////////// TITLE ////////////////
	/////////// SCREEN ////////////////
		
		super.paintComponent(g);
		g.drawImage(title_background,0,0,null);
		g.setColor(Color.ORANGE);
		g.fillRect(40, 40, 400, 100);
		///////// Title /////////////////////////////////////
		g.setFont(new Font("TimesRoman", Font.BOLD, 30)); 
		g.setColor(Color.RED);
		g.drawString("NOT SPACE INVADERS", 70, 90);
		
		////////////////////////////////////////////////////
		//////// OPTIONS /////////////////////////
		g.setColor(Color.ORANGE);
		g.fillRect(40, 500, 400, 200);
		////////////////////////////////////////////////////
		g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
		g.setColor(Color.BLACK);
		g.drawString("START GAME (A)", 80, 530);
		g.drawString("INSTRUCTIONS (I)", 80, 580);
		g.drawString("OPEN A SAVE FILE (O)", 80, 620);
		g.drawString("CONTROLS (C)", 80, 670);
		
	}
	
	/**
	 * @param A KeyEvent, e
	 * Gets code from the keyboard and controls what the character does if left/right/space/enter/o/tab is pressed
	 */
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode(); // gets code from keyboard
		
		if (c == KeyEvent.VK_I) {
			JOptionPane.showMessageDialog(null, "[ This is a spaceshooter game ]"
					+ "\n[ Your goal is to destroy the ships before they reach you ]"
					+ "\n[ If the ships reach you it will be GAME OVER ]"
					+ "\n[ You can !!!!SAVE!!!!!! your progress by using the enter key ]"
					+ "\n[ Saving your progress will bring you back to the level you saved on ]"
					+ "\n[ Access your saved progress with (Ctrl O) from the Startup menu "
					+ "\n[ The save file is named savefile.txt "
					+ "\n[ savefile.txt is located in the src folder ]");
		}
		
		if(c == KeyEvent.VK_C) {
			JOptionPane.showMessageDialog(null, "[ ARROW KEYS control movement (Left-Right) ]"
					+ "\n[ SPACE BAR acts as your firing mechanism ]"
					+ "\n[ TAB KEY acts as a missle reloader (Sets missle back to your position) ]" );
		}
		
		if(c == KeyEvent.VK_A) {
			close_title_GUI();
			LevelOne lvlONE = new LevelOne();
			JFrame jft = new JFrame();
			jft.setTitle("Game Level 2");
			jft.setSize(400, 700);
			jft.setVisible(true);
			jft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jft.add(lvlONE);
			
		}
		if(c== KeyEvent.VK_O) {
			LevelOne op = new LevelOne();
			op.keyPressed(e);
		}
	}
	
	/**
	 * @param args
	 * Main method, calls the constructor and creates the title frame
	 */
	public static void main(String args[]) {
		
		Title callGUI = new Title();
		callGUI.create_title_frame();
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
