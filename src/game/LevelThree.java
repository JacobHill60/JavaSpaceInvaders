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
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelThree extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private int score = 150;
	Timer tm = new Timer(5,this);
	int charX = 150, charY = 640, charsideways = 0, charUPDOWN = 0; // If velocities are set to non-zero they act on their own
	int bulletX = charX; int bulletY = charY; int bulletUP = 0; int bulletsideways = charsideways;
	BufferedImage img = null;
	BufferedImage enem = null;
	Enemy[] enemyTHREE = new Enemy[15];
	boolean gameWon = false;
	int kills = 0;
	
	/**
	 * Constructor - loads the background image and populates the enemy array list
	 */
	public LevelThree() {
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
		
		///////// ENEMY ARRAYLIST /////////
		int ex = 50;     //// Enemy spawn location
		int ey = 80;
		for(int i =0; i< enemyTHREE.length; i++) {
			enemyTHREE[i] = new Enemy(ex,ey);
			ex+=40;
			if(i == 7) {
				ex = 50; // Space between each enemy
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
		/////////////////////////////
		// Shot ////////////////////
		g.setColor(Color.RED);
		g.fillRect(bulletX,bulletY, 10,20); // Size
		////////////////////////////
		////// Character ///////////
		g.setColor(Color.MAGENTA);
		g.fillRect(charX, charY, 30, 20); // Size
		//////////////////////////////////
		//////////////////////////////////
		///////// Level Border ///////////
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.setColor(Color.RED);
		g.drawString("L E V E L       3", 100, 60);
		g.drawRect(20, 20, 350, 60);
		//////////////////////////////////////////
		//////////// LEVEL THREE /////////////////
		for(int i = 0; i < enemyTHREE.length; i++) {
			if(enemyTHREE[i].isShot == false) {
				g.setColor(Color.CYAN);
				g.drawImage(enem, (int) enemyTHREE[i].x, (int) enemyTHREE[i].y, null);
				if (enemyTHREE[i].contains(bulletX, bulletY)) {
					enemyTHREE[i].getsShot();
					bulletUP = 0;
					bulletY = charY;
					bulletX = charX;
				System.out.println("Enemy Shot");
				score += 10;
				System.out.println("SCORE: "+ score);
				//check if all enemies are shot
				for (int j = 0; j < enemyTHREE.length; j++) {
					if (enemyTHREE[j].isShot) {
						kills += 1;
						}
					if (kills == enemyTHREE.length) {
						gameWon = true;
						}
					}
				}
			}
		}
		if (gameWon = true) {
			FileWriter fw;
			try {
				fw = new FileWriter("highscore.txt");
				try {
					fw.write(score);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			System.out.println("Game over. You lose.");
		}
	}

	public void keyTyped(KeyEvent e) {}
	
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
			bulletUP = -1;	
		}
		if (c == KeyEvent.VK_TAB) {
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
				fw.write("3");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		charsideways = 0;
		charUPDOWN = 0;
		bulletsideways = 0;
	}

	/**
	 * @param An ActionEvent, e
	 * Animates the characters' movement and prevents any off-screening. 
	 */
	public void actionPerformed(ActionEvent e) {
		//controls the movement of the boss, player and bullets
		for(int i = 0; i < enemyTHREE.length; i++) {
			if(enemyTHREE[i].moveLeft == true) {
				enemyTHREE[i].x -=1;
				e.getSource();
				}
			if(enemyTHREE[i].moveRight == true) {
				enemyTHREE[i].x +=1;
				}
		}
			for(int i = 0; i < enemyTHREE.length; i++) {
			if(enemyTHREE[i].x>370) {
				for(int j = 0; j < enemyTHREE.length; j++) {
				enemyTHREE[j].moveLeft = true;
				enemyTHREE[j].moveRight = false;
				enemyTHREE[j].y +=6;
				}
			}
			if(enemyTHREE[i].x<0) {
				for(int j = 0; j < enemyTHREE.length; j++) {
					enemyTHREE[j].moveRight = true;
					enemyTHREE[j].moveLeft = false;
					enemyTHREE[j].y +=6;
				}
			}
		}
		if (charX < 0) {
			charsideways = 0;
			charX =  0;
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
		charY = charY + charUPDOWN; // (UP AND DOWN)
		bulletY = bulletY + bulletUP;
		bulletX = bulletX + bulletsideways;
		repaint();
	}	
}