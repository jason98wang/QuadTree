
/**
 * [Node.java]
 * Class creating and making changes to the quad tree
 * Authors: Jason Wang 
 * October 22, 2018
 */
import java.awt.Rectangle;
import java.util.ArrayList;

public class Node<T> {

	private Node<T> TL; //top left
	private Node<T> TR; //top right
	private Node<T> BL; //bottom left
	private Node<T> BR; //bottom right
	ArrayList<BouncingBall> ballList;
	Rectangle boundingBox;

	public Node(int x, int y, int width, int height) {
		this.setTL(null);
		this.setTR(null);
		this.setBL(null);
		this.setBR(null);
		ballList = new ArrayList<BouncingBall>();
		boundingBox = new Rectangle(x, y, width, height);
	}

	/**
	 * getTL 
	 * This method returns the top left child node
	 * @return TL, the top left node
	 */
	public Node<T> getTL() {
		return TL;
	}

	/**
	 * setTL
	 * This method sets the top left child node
	 * @param TL, the node to set it to
	 */
	public void setTL(Node<T> tL) {
		TL = tL;
	}

	/**
	 * getTR
	 * This method returns the top right child node
	 * @return TR, the top right node
	 */
	public Node<T> getTR() {
		return TR;
	}

	/**
	 * setTR
	 * This method sets the top right child node
	 * @param TR, the node to set it to
	 */
	public void setTR(Node<T> tR) {
		TR = tR;
	}

	/**
	 * getBL
	 * This method returns the bottom left child node
	 * @return BL, the bottom left node
	 */
	public Node<T> getBL() {
		return BL;
	}

	/**
	 * setBL
	 * This method sets the bottom left child node
	 * @param BL, the node to set it to
	 */
	public void setBL(Node<T> bL) {
		BL = bL;
	}

	/**
	 * getBR
	 * This method returns the bottom right child node
	 * @return BR, the bottom right node
	 */
	public Node<T> getBR() {
		return BR;
	}

	/**
	 * setBR
	 * This method sets the bottom right child node
	 * @param BR, the node to set it to
	 */
	public void setBR(Node<T> bR) {
		BR = bR;
	}

	/**
	 * add
	 * This method is for adding balls into the proper ball array lists in the quadtree
	 * @param ball, the ball that is being added
	 * @param node, the initial node that it's basing off of
	 */
	public static void add(BouncingBall ball, Node<BouncingBall> node) {

		//return if the node is null or the node doesn't contain the ball
		if (node == null || !node.boundingBox.contains(ball.getX(), ball.getY())) {
			return;
		}

		//add balls to the list
		node.ballList.add(ball);

		//changing the balls set block its in to the bounding box of the node
		ball.boundingBox = node.boundingBox;

		//recursively go to all of the children nodes
		add(ball, node.getTL());
		add(ball, node.getTR());
		add(ball, node.getBL());
		add(ball, node.getBR());
	}

	/**
	 * addBranch
	 * This method is for adding branches in to the quad tree
	 * @param node, the previous/initial node in the tree 
	 */
	public static void addBranch(Node<BouncingBall> node) {
		if (node == null) {
			return;
		}
		if (node.getTL() == null) {
			//check if there are more balls in this part than the threshold
			if (node.ballList.size() > BallAssignment.THRESHOLD) {
				int x = (int) node.boundingBox.getX();
				int y = (int) node.boundingBox.getY();
				int width = (int) (node.boundingBox.getWidth() / 2);
				int height = (int) (node.boundingBox.getHeight() / 2);

				//subdivide and create new nodes to contain these balls
				node.setTL(new Node<BouncingBall>(x, y, width, height));
				node.setTR(new Node<BouncingBall>(x + width, y, width, height));
				node.setBL(new Node<BouncingBall>(x, y + height, width, height));
				node.setBR(new Node<BouncingBall>(x + width, y + height, width, height));

				//add the balls into the newly created sections of the tree
				for (int i = 0; i < node.ballList.size(); i++) {
					BouncingBall ball = (BouncingBall) node.ballList.get(i);
					add(ball, node.getTL());
					add(ball, node.getTR());
					add(ball, node.getBL());
					add(ball, node.getBR());
				}
			}
		} else {
			//recursively go to the next nodes in the tree
			addBranch(node.getTL());
			addBranch(node.getTR());
			addBranch(node.getBL());
			addBranch(node.getBR());
		}
	}

	/**
	 * removeBranch
	 * This method is for removing branches in the quad tree
	 * @param node, the previous/initial node in the tree 
	 */
	public static void removeBranch() {
		//go through all of the different nodes/boxes that exist
		for (int i = 0; i < BallAssignment.drawBoundary.size(); i++) {
			Node<BouncingBall> node = BallAssignment.drawBoundary.get(i);
			if (node != null) {
				//if the list has less balls than the threshold remove it's children nodes
				if (node.ballList.size() < BallAssignment.THRESHOLD) {
					node.setTL(null);
					node.setTR(null);
					node.setBL(null);
					node.setBR(null);
				}
			}
		}
	}

	/**
	 * draw
	 * This method is for determining what needs to be drawn
	 * @param node, the previous/initial node in the tree 
	 */
	public static void draw(Node<BouncingBall> node) {
		//if there are no more children of the node or the current node contains the next node then return
		if (node.getTL() == null || BallAssignment.drawBoundary.contains(node.getTL())) {
			return;
		} else {
			//add the children nodes 
			BallAssignment.drawBoundary.add(node.getTL());
			BallAssignment.drawBoundary.add(node.getTR());
			BallAssignment.drawBoundary.add(node.getBL());
			BallAssignment.drawBoundary.add(node.getBR());
			//recursively go to the children of the node
			draw(node.getTL());
			draw(node.getTR());
			draw(node.getBL());
			draw(node.getBR());
		}
	}

	/**
	 * removeBall
	 * This method is for removing the ball once it leaves a quadrant
	 * @param ball, the ball that is being removed
	 * @param node, the previous/initial node in the tree 
	 */
	public static void removeBall(BouncingBall ball, Node<BouncingBall> node) {

		//if the node is null or it doesn't contain the ball then return
		if (node == null || !node.ballList.contains(ball)) {
			return;
		}

		//remove the ball from the current node's array list
		node.ballList.remove(ball);
		//go the the next nodes to also remove the ball from there
		removeBall(ball, node.getTL());
		removeBall(ball, node.getTR());
		removeBall(ball, node.getBL());
		removeBall(ball, node.getBR());
	}

	/**
	 * getBallList
	 * This method is for returning the ball list
	 * return ballList, list of balls contain in a node
	 */
	public ArrayList<BouncingBall> getBallList() {
		return ballList;
	}

}
