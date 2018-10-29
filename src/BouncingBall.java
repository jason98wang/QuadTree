import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class BouncingBall {

	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int directionX; // 1 for left, 2 for right
	private int directionY; // 1 for up, 2 for down
	private int radius = 5; // constant

	Random rand = new Random();
	public Rectangle box;
	public Rectangle boundingBox;

	BouncingBall() {
		x = rand.nextInt(BallAssignment.screenX);
		y = rand.nextInt(BallAssignment.screenY);

		speedX = rand.nextInt(5) + 1;
		speedY = rand.nextInt(5) + 1;

		directionX = rand.nextInt(2) - 1;
		directionY = rand.nextInt(2) - 1;
		
		if (directionX == 0) {
			directionX = 1;
		} else if (directionY == 0) {
			directionY = 1;
		}

		box = new Rectangle(x, y, radius * 2, radius * 2);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDirectonX() {
		return directionX;
	}

	public int getDirectonY() {
		return directionY;
	}

	public void moveHorizontal() {
		directionX = directionX * -1;
	}

	public void moveVertical() {
		directionY = directionY * -1;
	}

	public void move() {
		this.x = x + (directionX * speedX);
		this.box.x = x + (directionX * speedX);

		this.y = y + (directionY * speedY);
		this.box.y = y + (directionX * speedY);
	}

	public void bounce() {

	}

	public int getRadius() {
		return radius;
	}
	


}
