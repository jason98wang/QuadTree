import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class BouncingBall {

	private int x;
	private int y;
	private int speed;
	private int directionX; // 1 for left, 2 for right
	private int directionY; // 1 for up, 2 for down
	private int radius = 5; // constant

	Random rand = new Random();
	public Rectangle box;
	public Rectangle boundingBox;

	BouncingBall() {
		x = rand.nextInt(BallAssignment.screenX);
		y = rand.nextInt(BallAssignment.screenY);
		speed = rand.nextInt(2) + 1;
		directionX = rand.nextInt(1) - 1;
		directionY = rand.nextInt(1) - 1;
		box = new Rectangle(x,y,radius*2,radius*2);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDirectonX() {
		return directionX;
	}

	public int getDirectonY() {
		return directionY;
	}

	public void moveHorizental() {
		directionX = directionX * -1;
	}

	public void moveVertical() {
		directionY = directionY * -1;
	}

	public void move() {
		this.x = x + (directionX * speed);
		this.box.x = x + (directionX * speed);
		
		this.y = y + (directionY * speed);	
		this.box.y = y + (directionX * speed);
	}

	public int getRadius() {
		return radius;
	}
	
}
