import java.awt.Rectangle;
import java.util.Random;

/**
 * [BouncingBall.java] 
 * Class for the bouncing balls generated 
 * Authors: Jason Wang
 * October 22, 2018
 */
public class BouncingBall {

	// Declaring initial variables
	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int directionX; // 1 for right, -1 for left
	private int directionY; // 1 for up, -1 for down
	private int RADIUS = 5; // constant
	private static double deltaX;
	private static double deltaY;
	private static double moveDistanceX;
	private static double moveDistanceY;
	private static double distance;
	private static int oldVelocityX1, oldVelocityY1, oldVelocityX2, oldVelocityY2;
	private static double angle1, angle2, s1, s2;
	private static double nvy1,nvx1,nvy2,nvx2;

	Random rand = new Random();
	public Rectangle box; // bouncing box for the ball
	public Rectangle boundingBox;

	BouncingBall() {
		x = rand.nextInt(BallAssignment.screenX);
		y = rand.nextInt(BallAssignment.screenY);

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

		speedX = speedX * directionX;
		speedY = speedY * directionY;
		
		System.out.println(speedY);

		box = new Rectangle(x, y, RADIUS * 2, RADIUS * 2);
	}

	/**
	 * getX 
	 * This method returns the ball's X value
	 * @return X, the bouncing ball's X value
	 */
	public int getX() {
		return x;
	}

	public int setX(int x) {
		return this.x = x;
	}

	/**
	 * getY 
	 * This method returns the ball's Y value
	 * @return y, the bouncing ball's y value
	 */
	public int getY() {
		return y;
	}

	public int setY(int y) {
		return this.y = y;
	}

	/**
	 * moveHorizontal 
	 * This method reverses the ball's x direction
	 */
	public void moveHorizontal() {
		speedX = speedX * -1;
	}

	/**
	 * moveVertical 
	 * This method reverses the ball's y direction
	 */
	public void moveVertical() {
		speedY = speedY * -1;
	}

	/**
	 * getSpeedX 
	 * This method is for getting the ball's x speed value
	 * @return speedX, the ball's X speed
	 */
	public int getSpeedX() {
		return speedX;
	}

	/**
	 * setSpeedX 
	 * This method is for setting the ball's x speed value
	 * @param speedX, the ball's new x speed value
	 */
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	/**
	 * getSpeedY 
	 * This method is for getting the ball's y speed value
	 * @return speedX, the ball's Y speed
	 */
	public int getSpeedY() {
		return speedY;
	}

	/**
	 * setSpeedY 
	 * This method is for setting the ball's y speed value
	 * @param speedY, the ball's new y speed value
	 */
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	/**
	 * move 
	 * This method moves the ball(changes it's x and y values) based on its
	 * speed and direction
	 */
	public void move() {
		this.x = x + speedX;
		this.box.x = x + speedX;

		this.y = y + speedY;
		this.box.y = y + speedY;
	}

	/**
	 * bounce 
	 * This method changes the location and moves the ball after colliding with
	 * another ball
	 * @param a, one of the balls that is being collided
	 * @param b, the other ball that is colliding against a
	 */
	public static void bounce(BouncingBall a, BouncingBall b) {
		
//		deltaX = b.getX() - a.getX();
//		deltaY = b.getY() - b.getY();
//
//		distance = deltaX * deltaX + deltaY * deltaY;
//
//		moveDistanceX = deltaX * (b.getRadius() + b.getRadius() - distance) / distance;
//		moveDistanceY = deltaY * (b.getRadius() + b.getRadius() - distance) / distance;
//
//		a.setX((int) (a.getX() + (moveDistanceX * 0.5)));
//		a.setX((int) (a.getY() + (moveDistanceY * 0.5)));
//		b.setX((int) (a.getX() - (moveDistanceX * 0.5)));
//		b.setY((int) (a.getY() - (moveDistanceY * 0.5)));
//
//		oldVelocityX1 = a.getSpeedX();
//		oldVelocityY1 = a.getSpeedY();
//		oldVelocityX1 = b.getSpeedX();
//		oldVelocityX2 = b.getSpeedY();
//
//		//angle1, angle2, s1, s2
//
//		angle1 = Math.atan((oldVelocityY1*1.00 / oldVelocityX1));
//		angle2 = Math.atan((oldVelocityY2*1.00 / oldVelocityX2));
//		s1 = Math.sqrt(((oldVelocityY1) * oldVelocityY1) + ((oldVelocityX1) * oldVelocityX1));
//		s2 = Math.sqrt(((oldVelocityY2) * oldVelocityY2) + ((oldVelocityX2) * oldVelocityX2));
//		
//		nvy1 =s1*Math.sin(angle2);
//		nvx1 =s1*Math.cos(angle2);
//		nvy2 =s2*Math.sin(angle1);
//		nvx2 =s2*Math.sin(angle1);
//		
//		
//		if(((oldVelocityY1 > 0) && (nvy2 < 0)) || ((oldVelocityY1 < 0) && (nvy2 > 0))){
//			nvy2 = nvy2 * -1;
//		}
//		
//		if(((oldVelocityX1 > 0) && (nvx2 < 0)) || ((oldVelocityX1 < 0) && (nvx2 > 0))){
//			nvx2 = nvx2 * -1;
//		}
//		
//		
//		if(((oldVelocityY2 > 0) && (nvy1 < 0)) || ((oldVelocityY2 < 0) && (nvy1 > 0))){
//			nvy1 = nvy1 * -1;
//		}
//		
//		
//		if(((oldVelocityX2 > 0) && (nvx1 < 0)) || ((oldVelocityX2 < 0) && (nvx1 > 0))){
//			nvx1 = nvx1 * -1;
//		}
//		
//		a.setSpeedX((int) nvx1);
//		a.setSpeedY((int) nvy1);
//		
//		b.setSpeedX((int) nvx2);
//		b.setSpeedY((int) nvy2);
//		
//		
	}

	/**
	 * getRadius 
	 * This method returns the radius of the ball
	 * @return radius, the radius of the ball
	 */
	public int getRadius() {
		return RADIUS;
	}

	/**
	 * collisionDetection
	 * This method is for checking if any of the balls are colliding against all of the other balls inside the same qudrant
	 * @param node, the previous/initial node in the tree 
	 */
	public static void collisionDetection(Node<BouncingBall> node) {
		//if we are in the smallest quadrant and there are no more children nodes
		if (node.getTL() == null) {
			for (int i = 0; i < node.ballList.size(); i++) {
				for (int j = i + 1; j < node.ballList.size(); j++) {
					BouncingBall a = (BouncingBall) node.ballList.get(i);
					BouncingBall b = (BouncingBall) node.ballList.get(j);
					//if the balls intersect bounce them
					if (a.box.intersects(b.box)) {
						BouncingBall.bounce(a, b);
						/*a.moveHorizontal();
						b.moveHorizontal()*/;
					}
				}
			}

		} else {
			//recursively go to the children classes of the nodes
			collisionDetection(node.getTL());
			collisionDetection(node.getTR());
			collisionDetection(node.getBL());
			collisionDetection(node.getBR());
		}
	}

}
