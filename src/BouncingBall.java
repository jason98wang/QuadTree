import java.awt.Rectangle;
import java.util.Random;

/**
 * [BouncingBall.java] 
 * Class for the bouncing balls generated 
 * Author: Jason wang
 * October 22, 2018
 */
public class BouncingBall {

	// Declaring initial variables
	private double x;
	private double y;
	private double speedX;
	private double speedY;
	private int directionX; // 1 for right, -1 for left
	private int directionY; // 1 for up, -1 for down
	private int RADIUS = 5; // constant
	private static double deltaX;
	private static double deltaY;
	private static double moveDistanceX;
	private static double moveDistanceY;
	private static double distance;
	private static double angle1, angle2, s1, s2;
	private static double nvy1, nvx1, nvy2, nvx2;

	Random rand = new Random();
	public Rectangle boundingBox;

	BouncingBall() {
		x = rand.nextInt(BallAssignment.screenX - 10) + 2;
		y = rand.nextInt(BallAssignment.screenY - 30) + 2;

		// Randomizing initial speed
		speedX = rand.nextInt(5) + 1;
		speedY = rand.nextInt(5) + 1;

		// Randomizing initial direction
		directionX = rand.nextInt(2) - 1;
		directionY = rand.nextInt(2) - 1;

		if (directionX == 0) {
			directionX = 1;
		}
		if (directionY == 0) {
			directionY = 1;
		}

		//include a direction to speed(velocity) 
		speedX = speedX * directionX;
		speedY = speedY * directionY;

	}

	/**
	 * getX This method returns the ball's X value
	 * 
	 * @return X, the bouncing ball's X value
	 */
	public double getX() {
		return x;
	}

	/**
	 * setX This method sets the speed
	 * @param x, the speed to set it to 
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * getY This method returns the ball's Y value
	 * 
	 * @return y, the bouncing ball's y value
	 */
	public double getY() {
		return y;
	}

	/**
	 * setY This method sets the speed
	 * @param y, the speed to set it to 
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * moveHorizontal This method reverses the ball's x direction
	 */
	public void moveHorizontal() {
		speedX = speedX * -1;
	}

	/**
	 * moveVertical This method reverses the ball's y direction
	 */
	public void moveVertical() {
		speedY = speedY * -1;
	}

	/**
	 * getSpeedX This method is for getting the ball's x speed value
	 * 
	 * @return speedX, the ball's X speed
	 */
	public double getSpeedX() {
		return speedX;
	}

	/**
	 * setSpeedX This method is for setting the ball's x speed value
	 * 
	 * @param speedX, the ball's new x speed value
	 */
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	/**
	 * getSpeedY This method is for getting the ball's y speed value
	 * 
	 * @return speedX, the ball's Y speed
	 */
	public double getSpeedY() {
		return speedY;
	}

	/**
	 * setSpeedY This method is for setting the ball's y speed value
	 * 
	 * @param speedY, the ball's new y speed value
	 */
	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	/**
	 * move This method moves the ball(changes it's x and y values) based on its
	 * speed and direction
	 */
	public void move() {
			//move the balls so it doesn't leave the screen
		   this.x = this.x +  this.speedX;
		   this.x = Math.max(this.x, 1);
		   this.x = Math.min(this.x, BallAssignment.screenX - 2);

			//move the balls so it doesnt leave the screen
		   this.y = this.y  + this.speedY;
		   this.y = Math.max(this.y, 1);
		   this.y = Math.min(this.y, BallAssignment.screenY - 2);
	}

	/**
	 * bounce This method changes the location and moves the ball after colliding
	 * with another ball
	 * 
	 * @param a, one of the balls that is being collided
	 * @param b, the other ball that is colliding against a
	 */
	public static void bounce(BouncingBall a, BouncingBall b) {

		//calucate distance need to move to avoid double collision
		moveDistanceX = deltaX * (a.RADIUS + b.RADIUS - distance) / distance;
		moveDistanceY = deltaY * (a.RADIUS + b.RADIUS - distance) / distance;

		//move balls away a certain distance so they don't double collide
		a.x = a.x + moveDistanceX * 0.5;
		a.y = a.y + moveDistanceY * 0.5;
		b.x = b.x - moveDistanceX * 0.5;
		b.y = b.y - moveDistanceY * 0.5;

		//Calculate angle
		angle1 = Math.atan((a.speedY / a.speedX));
		angle2 = Math.atan((b.speedY / a.speedY));
		s1 = Math.sqrt(a.speedY * a.speedY + a.speedX * a.speedX);
		s2 = Math.sqrt(b.speedY * b.speedY + b.speedX * b.speedX);

		//Calculate new velocity based of angle
		nvy1 = s1 * Math.sin(angle2);
		nvx1 = s1 * Math.cos(angle2);
		nvy2 = s2 * Math.sin(angle1);
		nvx2 = s2 * Math.cos(angle1);

		if ((a.speedY > 0 && nvy2 < 0) || (a.speedY < 0 && nvy2 > 0)) {
			nvy2 = nvy2 * -1;
		}
		if ((a.speedX > 0 && nvx2 < 0) || (a.speedX < 0 && nvx2 > 0)) {
			nvx2 = nvx2 * -1;
		}
		if ((b.speedY > 0 && nvy1 < 0) || (b.speedY < 0 && nvy1 > 0)) {
			nvy1 = nvy1 * -1;
		}
		if ((b.getSpeedX() > 0 && nvx1 < 0) || (b.getSpeedX() < 0 && nvx1 > 0)) {
			nvx1 = nvx1 * -1;
		}

		//set new velocities(speed)
		a.speedX = nvx1;
		a.speedY = nvy1;
		b.speedX = nvx2;
		b.speedY = nvy2;
		
	

	}

	/**
	 * getRadius This method returns the radius of the ball
	 * 
	 * @return getRadius(), the radius of the ball
	 */
	public int getRadius() {
		return RADIUS;
	}

	/**
	 * collisionDetection This method is for checking if any of the balls are
	 * colliding against all of the other balls inside the same qudrant
	 * 
	 * @param node, the previous/initial node in the tree
	 */
	public static void collisionDetection(Node<BouncingBall> node) {
		// if we are in the smallest quadrant and there are no more children nodes
		if (node.getTL() == null) {
			for (int i = 0; i < node.ballList.size(); i++) {
				for (int j = i + 1; j < node.ballList.size(); j++) {
					BouncingBall a = (BouncingBall) node.ballList.get(i);
					BouncingBall b = (BouncingBall) node.ballList.get(j);
					//calucate the distance between the balls;
					deltaX = a.x - b.x;
					deltaY = a.y - b.y;
					distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
					// if the balls intersect bounce them
					if (distance <= a.RADIUS + b.RADIUS) {
						BouncingBall.bounce(a, b); 
					}
				}
			}

		} else {
			// recursively go to the children classes of the nodes
			collisionDetection(node.getTL());
			collisionDetection(node.getTR());
			collisionDetection(node.getBL());
			collisionDetection(node.getBR());
		}
	}

}
