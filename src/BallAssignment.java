import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallAssignment extends JFrame {

	static Node<BouncingBall> root;
	public static final int THRESHOLD = 4;
	private static JFrame window;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenX = (int) screenSize.getWidth();
	public static int screenY = (int) screenSize.getHeight();

	public static ArrayList<Node> drawBoundary = new ArrayList();

	public static void main(String[] args) {
		System.out.println(screenX);
		System.out.println(screenY);

		root = new Node(0, 0, screenX, screenY);
		drawBoundary.add(root);
		window = new BallAssignment();
	}

	public void addBalls(int balls) {

		for (int i = 0; i < balls; i++) {
			root.add(new BouncingBall(), root);
		}

	}

	// Constructor
	public BallAssignment() {
		super("Ball Game");

		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height + 15);
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

		// Declare variables
		int direction;
		Random rand = new Random();

		public void paintComponent(Graphics g) {

			// Call the super class
			super.paintComponent(g);
			setDoubleBuffered(true);

			Node.addBranch(root);

			Node.removeBranch();

			// CHECK FOR COLLISION

			drawBoundary.clear();
			Node.draw(root);

			// Draw
			for (int i = 0; i < drawBoundary.size(); i++) {
				Node a = drawBoundary.get(i);

				g.setColor(Color.BLACK);
				g.drawRect(a.boundingBox.x, a.boundingBox.y, a.boundingBox.width, a.boundingBox.height);

			}
			// Draw all squares
			g.setColor(Color.BLACK);

			for (int i = 0; i < root.getBallList().size(); i++) {

				int x = root.getBallList().get(i).getX() - root.getBallList().get(i).getRadius();
				int y = root.getBallList().get(i).getY() - root.getBallList().get(i).getRadius();
				int width = root.getBallList().get(i).getRadius() * 2;
				int height = root.getBallList().get(i).getRadius() * 2;

				g.fillOval(x, y, width, height);
			}

			// moves the balls

			for (int i = 0; i < root.getBallList().size(); i++) {

				// collision // how to do direction

				////////////// Put in where???
				if (root.getBallList().get(i).getX() >= screenX - root.getBallList().get(i).getRadius() * 2) {
					root.getBallList().get(i).moveHorizental();
				} else if (root.getBallList().get(i).getY() >= screenY - root.getBallList().get(i).getRadius() * 2) {
					root.getBallList().get(i).moveVertical();

				} else if (root.getBallList().get(i).getX() <= 0 + root.getBallList().get(i).getRadius() * 2) {
					root.getBallList().get(i).moveHorizental();

				} else if (root.getBallList().get(i).getY() <= 0 + root.getBallList().get(i).getRadius() * 2) {
					root.getBallList().get(i).moveVertical();
				}

				root.getBallList().get(i).move();

				BouncingBall ball = root.getBallList().get(i);

				if (!ball.boundingBox.contains(ball.getX(), ball.getY())) {
					Node.removeBall(ball, root);
					Node.add(ball, root);
				}

			}


		Node.collisionDetection(root);
			

			// Repaint
			repaint();

		} // End of paintComponent
	}// End of GameAreaPanel

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_A) {
				addBalls(10000);
			}

		}

	}
}
