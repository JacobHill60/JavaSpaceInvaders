package game;

import java.awt.geom.Rectangle2D;

public class Enemy extends Rectangle2D.Double {

	boolean visible;
	boolean moveLeft;
	boolean moveRight;
	boolean isShot;
	
	/**
	 * @param x
	 * @param y
	 * The constructor which creates & sets the variables
	 */
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;
		moveLeft = false;
		moveRight = true;
		visible = true;
		isShot = false;
	}
	
	/**
	 * changes the field isShot to True if the enemy touches a shot
	 */
	public void getsShot() {
		isShot = true;
		
	}
}
