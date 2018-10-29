import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Node<T> {

	private Node<T> TL;
	private Node<T> TR;
	private Node<T> BL;
	private Node<T> BR;
	ArrayList<BouncingBall> ballList;
	Rectangle boundingBox;
	public static boolean subDivided;

	public Node(int x, int y, int width, int height) {
		this.TL = null;
		this.TR = null;
		this.BL = null;
		this.BR = null;
		ballList = new ArrayList();
		boundingBox = new Rectangle(x, y, width, height);
		subDivided = false;
	}

	public static void add(BouncingBall ball, Node node) {

		if (node == null || !node.boundingBox.contains(ball.getX(), ball.getY())) {
			return;
		}

		node.ballList.add(ball);

		ball.boundingBox = node.boundingBox;

		add(ball, node.TL);
		add(ball, node.TR);
		add(ball, node.BL);
		add(ball, node.BR);
	}

	public static void addBranch(Node node) {

		if (node == null) {
			return;
		}
		if (node.TL == null) {

			if (node.ballList.size() > BallAssignment.THRESHOLD) {
				int x = (int) node.boundingBox.getX();
				int y = (int) node.boundingBox.getY();
				int width = (int) (node.boundingBox.getWidth() / 2);
				int height = (int) (node.boundingBox.getHeight() / 2);

				BallAssignment.drawBoundary.add(node);
				node.TL = new Node(x, y, width, height);
				node.TR = new Node(x + width, y, width, height);
				node.BL = new Node(x, y + height, width, height);
				node.BR = new Node(x + width, y + height, width, height);

				for (int i = 0; i < node.ballList.size(); i++) {
					BouncingBall ball = (BouncingBall) node.ballList.get(i);
					add(ball, node.TL);
					add(ball, node.TR);
					add(ball, node.BL);
					add(ball, node.BR);
				}
			}
		} else {
			addBranch(node.TL);
			addBranch(node.TR);
			addBranch(node.BL);
			addBranch(node.BR);
		}
	}

	public static void removeBranch() {
		
		for (int i = 0; i < BallAssignment.drawBoundary.size(); i++) {
			Node node = BallAssignment.drawBoundary.get(i);
			if (node != null) {
				if (node.ballList.size() < BallAssignment.THRESHOLD) {
					node.TL = null;
					node.TR = null;
					node.BL = null;
					node.BR = null;

				}
			}
		}
	}

	public static void draw(Node node) {		
		if (node.TL == null || BallAssignment.drawBoundary.contains(node.TL)) {
			return;
		} else {
			BallAssignment.drawBoundary.add(node.TL);
			BallAssignment.drawBoundary.add(node.TR);
			BallAssignment.drawBoundary.add(node.BL);
			BallAssignment.drawBoundary.add(node.BR);
			draw(node.TL);
			draw(node.TR);
			draw(node.BL);
			draw(node.BR);
		}
	}

	public static void removeBall(BouncingBall ball, Node node) {

		if (node == null || !node.ballList.contains(ball)) {
			return;
		}
		node.ballList.remove(ball);
		removeBall(ball, node.TL);
		removeBall(ball, node.TR);
		removeBall(ball, node.BL);
		removeBall(ball, node.BR);
	}

	public ArrayList<BouncingBall> getBallList() {
		return ballList;
	}
	
	public static void collisionDetection(Node node) {
		if(node.TL == null) {
			for(int i = 0; i<node.ballList.size(); i++) {
				for(int j = i + 1; j<node.ballList.size(); j++) {
					BouncingBall a = (BouncingBall) node.ballList.get(i);
					BouncingBall b = (BouncingBall) node.ballList.get(j);
					if(a.box.intersects(b.box)) {
						a.moveHorizental();
						b.moveVertical();				
					}
					
				}
			}
			return;
		}else {
			collisionDetection(node.TL);
			collisionDetection(node.TR);
			collisionDetection(node.BL);
			collisionDetection(node.BR);
		}		
	}

}
