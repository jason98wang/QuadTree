
/**
 * [BallAssignment.java]
 * Class creating the game window and updating it
 * Authors: Jason Wang 
 * October 22, 2018
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallAssignment extends JFrame {

	static Node<BouncingBall> root;
	public static final int THRESHOLD = 5;
	private static JFrame window;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenX = (int) screenSize.getWidth();
	public static int screenY = (int) screenSize.getHeight();

	public static ArrayList<Node<BouncingBall>> drawBoundary = new ArrayList<Node<BouncingBall>>();

	public static void main(String[] args) {

		root = new Node<BouncingBall>(0, 0, screenX, screenY);
		drawBoundary.add(root);
		window = new BallAssignment();
	}

	// Constructor
	public BallAssignment() {
		super("Ball Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(1280,800);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setResizable(false);

		// Set up the game panel
		JPanel gamePanel = new GameAreaPanel();
		this.add(gamePanel);

		MyKeyListener keyListener = new MyKeyListener();
		this.addKeyListener(keyListener);

		// Focus the frame
		this.requestFocusInWindow();

		// Make the frame visible
		this.setVisible(true);

	} // End of constructor

	// Inner class for game area (DRAWING HERE)
	private class GameAreaPanel extends JPanel {

		public void paintComponent(Graphics g) {

			// Call the super class
			super.paintComponent(g);
			setDoubleBuffered(true);

			//Add branches to the trees based on the locations of the balls
			Node.addBranch(root);

			//remove branches to the trees based on the locations of the balls
			Node.removeBranch();

			//Add to array list which quadrants to draw
			drawBoundary.clear();
			Node.draw(root);

			// Draw the quadrants
			for (int i = 0; i < drawBoundary.size(); i++) {
				Node<BouncingBall> a = drawBoundary.get(i);
				g.setColor(Color.BLACK);
				g.drawRect(a.boundingBox.x, a.boundingBox.y, a.boundingBox.width, a.boundingBox.height);

			}
			// Draw the balls
			g.setColor(Color.BLACK);

			for (int i = 0; i < root.getBallList().size(); i++) {
				double x = root.getBallList().get(i).getX() - root.getBallList().get(i).getRadius();
				double y = root.getBallList().get(i).getY() - root.getBallList().get(i).getRadius();
				int width = root.getBallList().get(i).getRadius() * 2;
				int height = root.getBallList().get(i).getRadius() * 2;

				g.fillOval((int) x, (int) y, width, height);
			}

			// Check for collision between the balls
			BouncingBall.collisionDetection(root);

			// Bounce off if collided with screen borders
			for (int i = 0; i < root.getBallList().size(); i++) {

				BouncingBall ball = root.getBallList().get(i);

				if ((ball.getX() >= screenX - 10) && (ball.getSpeedX() > 0)) {
					ball.moveHorizontal();
					ball.setX(screenX - 10);
				}
				if ((ball.getY() >= screenY - 30) && (ball.getSpeedY() > 0)) {
					ball.moveVertical();
					ball.setY(screenY - 30);
				}
				if ((ball.getX() <= 4) && (ball.getSpeedX() < 0)) {
					ball.moveHorizontal();
					ball.setX(4);
				}
				if ((ball.getY() <= 4) && (ball.getSpeedY() < 0)) {
					ball.moveVertical();
					ball.setY(4);
				}

				
				ball.move();

				// if the ball has left the quadrant update the quadrants ball list
				if (!ball.boundingBox.contains(ball.getX(), ball.getY())) {
					Node.removeBall(ball, root);
					Node.add(ball, root);
				}

			}
			

			//slow down the program for testing purposes(uncommon if needed)
/*						try {
				Thread.sleep(100);
			} catch (Exception e) {
			}*/

			repaint();

		} // End of paintComponent
	}// End of GameAreaPanel

	/**
	 * [MyKeyListener.java]
	 * Class tracking keyboard 
	 * Authors: Jason Wang 
	 * October 22, 2018
	 */
	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		/**
		 * keyReleased
		 * This method is detecting key press
		 * @param e, the key pressed
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			//if space is pressed add ball
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {

				for (int i = 0; i < 5; i++) {
					Node.add(new BouncingBall(), root);
				}
			}
		}

	}
}
