package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelOne extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private int score = 0;
	static boolean moveOn = false;
	static JFrame jf = new JFrame();
	Timer tm = new Timer(3,this);
	int charX = 150, charY = 640, charsideways = 0, charUPDOWN = 0; // If velocities are set to non-zero they act on their own
	int bulletX = charX; int bulletY = charY; int bulletUP = 0; int bulletsideways = charsideways;
	BufferedImage img = null;
	BufferedImage enem = null;
	Enemy[] enemyONE = new Enemy[8];
	
	/**
	 * Constructor - loads the background image and populates the enemy array list
	 */
	public LevelOne() {
		try {
			img = ImageIO.read(new File("Spaceback.jpg"));
		} catch (IOException e) {
	       }
		try {
			enem = ImageIO.read(new File("enemyship.jpg"));
		} catch (IOException e2) {
		}
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		////////////////////////////////////////////
		///////// ENEMY ARRAYLIST, LEVEL 1 //////////////////
		int ex = 50;     // Enemy spawn location
		int ey = 80;
		for(int i =0; i< enemyONE.length; i++) {
			enemyONE[i] = new Enemy(ex,ey);
			ex+=50;
			if(i == 2) {
				ex = 50;     // Space between each enemy
				ey += 40;
			}
		}
	}
	
	/**
	 * @param A Graphics object, g
	 * Paints the characters and the level. Also detects collision and whether or not the level has been won
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(img,0,0,null);
		//////// Shot /////////
		g.setColor(Color.RED);
		g.fillRect(bulletX,bulletY, 10,20); // Size
		//////// Character ////////
		g.setColor(Color.MAGENTA);
		g.fillRect(charX, charY, 30, 20); // Size
		///////// Level Border ///////////
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.setColor(Color.RED);
		g.drawString("L E V E L       1", 100, 60);
		g.drawRect(20, 20, 350, 60);

		//////////// LEVEL ONE ////////////////
		for(int i = 0; i < enemyONE.length; i++) {
			if(enemyONE[i].isShot == false) {
				g.setColor(Color.GREEN);
				g.drawImage(enem, (int) enemyONE[i].x, (int) enemyONE[i].y, null);
				if (enemyONE[i].contains(bulletX, bulletY))	{
					//enemyONE[i].getsShot();
					enemyONE[i].isShot = true;
					bulletUP = 0;
					bulletY = charY;
					bulletX = charX;
				System.out.println("Enemy Shot");
				score += 10;
				System.out.println("SCORE: "+ score);
				//check if all enemies are shot
				int kills = 0;
				for (int j = 0; j < enemyONE.length; j++) {
					if (enemyONE[j].isShot) {
						kills += 1;
						}
					if (kills == enemyONE.length) {
						//move on to level two
						System.out.println("Level 1 complete");
						jf.dispose();
						jf.setVisible(false);
						LevelTwo t = new LevelTwo();
						JFrame jft = new JFrame();
						jft.setTitle("Game Level 2");
						jft.setSize(400, 700);
						jft.setVisible(true);
						jft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jft.add(t);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param An ActionEvent, e
	 * Animates the characters' movement and prevents any off-screening. 
	 */
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < enemyONE.length; i++) {
			if(enemyONE[i].moveLeft == true) {
				enemyONE[i].x -=3;
				e.getSource();
				}
			if(enemyONE[i].moveRight == true) {
				enemyONE[i].x +=1;
				}
			}
			for(int i = 0; i < enemyONE.length; i++) {
				if(enemyONE[i].x>370) {
					for(int j = 0; j < enemyONE.length; j++) {
						enemyONE[j].moveLeft = true;
						enemyONE[j].moveRight = false;
						enemyONE[j].y +=6;
					}
				}
				if(enemyONE[i].x<0) {
					for(int j = 0; j < enemyONE.length; j++) {
						enemyONE[j].moveRight = true;
						enemyONE[j].moveLeft = false;
						enemyONE[j].y +=6;
					}
				}
			}
		if (charX < 0) {
			charsideways = 0; // if the charx was set to 50 for ex. the character would stop when he reached 
								// that value however you can still control the bullet
			charX =  0; // if char
		}
		if (charX > 360) {
			charsideways = 0;
			charX = 360;
		}
		if (charY < 640) {
			charUPDOWN = 0;
			charY = 640;
		}
		if (charY > 640) {
			charUPDOWN = 0;
			charY = 640;
		}
		if (bulletY < 640) {
			bulletsideways = 0;
		}
		if (bulletX < 0) {
			bulletsideways = 0;
			bulletX=  0;
		}
		if (bulletX > 360) {
			bulletsideways = 0;
			bulletX = 360;
		}
		if (bulletY < 85) {
			bulletUP = 0;
			bulletY = charY;
			bulletX = charX;
		}
		charX = charX + charsideways; // (LEFT AND RIGHT) x is initially 0, but we tell Java that if the user presses a button
									  //on the keyboard it adds a value to x
		charY = charY + charUPDOWN;   // (UP AND DOWN)
		bulletY = bulletY + bulletUP; 
		bulletX = bulletX + bulletsideways;
		repaint();
	}
	
	/**
	 * @param A KeyEvent, e
	 * Gets code from the keyboard and controls what the character does if left/right/space/enter/o/tab is pressed
	 */
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode(); // gets code from keyboard
	
		if (c == KeyEvent.VK_LEFT) {
			charsideways = -1; // Going Left = negative value
			charUPDOWN = 0;
			bulletsideways = -1;
		}
		if (c == KeyEvent.VK_UP) {
			charsideways = 0;
			charUPDOWN = -1; // up is negative value
		}
		if (c == KeyEvent.VK_RIGHT) {
			charsideways = 1;
			charUPDOWN = 0;
			bulletsideways = 1;
		}
		if (c == KeyEvent.VK_SPACE) {
			//shoots the bullet
			bulletUP = -1;
		}
		if (c == KeyEvent.VK_TAB) {
			//reloads bullet
			bulletUP = 0;
			bulletY = charY;
			bulletX = charX;
		}
		if (c == KeyEvent.VK_DOWN) {
			charsideways = 0;
			charUPDOWN = 1;
		}
		if (c == KeyEvent.VK_ENTER) {
			//saves a file with the level number in it
			try {
				FileWriter fw = new FileWriter("src/game/savefile.txt");
				fw.write("1");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (c == KeyEvent.VK_O) {
			//opens a save file to check what level it's on
			JFileChooser chooser = new JFileChooser(".");
			int choice = chooser.showOpenDialog(getParent());
			if (choice == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try {
					Scanner scan = new Scanner(file);
					String lvl = scan.next();
					if (lvl.equals("2")) {
						jf.dispose();
						jf.setVisible(false);
						LevelTwo t = new LevelTwo();
						JFrame jft = new JFrame();
						jft.setTitle("Game Level 2");
						jft.setSize(400, 700);
						jft.setVisible(true);
						jft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jft.add(t);
					}
					if (lvl.equals("3")) {
						jf.dispose();
						jf.setVisible(false);
						LevelThree b = new LevelThree();
						JFrame jfb = new JFrame();
						jfb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jfb.setTitle("Level 3");
						jfb.setSize(400, 700);
						jfb.setVisible(true);
						jfb.add(b);
					}
					scan.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		charsideways = 0;
		charUPDOWN = 0;
		bulletsideways = 0;
	}
	
	/**
	 * @param args
	 * Main method, calls the constructor and builds the JFrame
	 */
	public static void main(String[] args) {
		LevelOne one = new LevelOne();
		jf.setTitle("Level One");
		jf.setSize(400,700); // size of JFrame
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(one);
	}
}