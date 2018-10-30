
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
	public static final int THRESHOLD = 4;
	private static JFrame window;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenX = (int) screenSize.getWidth();
	public static int screenY = (int) screenSize.getHeight();


	public static ArrayList<Node<BouncingBall>> drawBoundary = new ArrayList<Node<BouncingBall>>();

	FrameRate frameRate = new FrameRate();

	public static void main(String[] args) {
		System.out.println(screenX);
		System.out.println(screenY);

		
		root = new Node<BouncingBall>(0, 0, screenX, screenY);
		drawBoundary.add(root);
		window = new BallAssignment();
	}

	public void addBalls(int balls) {
		for (int i = 0; i < balls; i++) {
			Node.add(new BouncingBall(), root);
		}

	}

	// Constructor
	public BallAssignment() {
		super("Ball Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280,800);
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
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

			Node.addBranch(root);

			Node.removeBranch();

			// CHECK FOR COLLISION

			frameRate.update();
			frameRate.draw(g, 50, 50);

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

			// moves the balls
			BouncingBall.collisionDetection(root);

			// bounce off if collided with screen borders
			g.fillOval(4, 50, 10, 10);
			for (int i = 0; i < root.getBallList().size(); i++) {

				BouncingBall ball = root.getBallList().get(i);

				if ((root.getBallList().get(i).getX() >= screenX - 10) && (root.getBallList().get(i).getSpeedX() > 0)) {

					root.getBallList().get(i).moveHorizontal();
					root.getBallList().get(i).setX(screenX - 10);
				}
				
				if ((root.getBallList().get(i).getY() >= screenY - 30) && (root.getBallList().get(i).getSpeedY() > 0)) {
					
					root.getBallList().get(i).moveVertical();
					root.getBallList().get(i).setY(screenY - 30);
				}
				if ((root.getBallList().get(i).getX() <= 4) && (root.getBallList().get(i).getSpeedX() < 0)) {
					
					root.getBallList().get(i).moveHorizontal();
					root.getBallList().get(i).setX(4);
				}
				if ((root.getBallList().get(i).getY() <= 4) && (root.getBallList().get(i).getSpeedY() < 0)) {
					
					root.getBallList().get(i).moveVertical();
					root.getBallList().get(i).setY(4);
				}
				
							

				root.getBallList().get(i).move();

				// if the ball has left the quadrant update the quadrants ball list
				if (!ball.boundingBox.contains(ball.getX(), ball.getY())) {
					Node.removeBall(ball, root);
					Node.add(ball, root);
				}

			}

//			try {
//				Thread.sleep(100);
//			} catch (Exception e) {
//			}

			repaint();

		} // End of paintComponent
	}// End of GameAreaPanel

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_A) {
				addBalls(10);
			}

		}

	}
}
