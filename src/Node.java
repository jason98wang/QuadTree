import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Node<T> {

	private Node<T> TL;
	private Node<T> TR;
	private Node<T> BL;
	private Node<T> BR;
	private ArrayList<BouncingBall> ballList;
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

	
	@SuppressWarnings("unchecked")
	public void add(BouncingBall ball) {
		
		ballList.add(ball);
		 
		if(ballList.size() < BallAssignment.THRESHOLD){
					
			if (TL == null) {
				return;
			}
			
			if(TL.boundingBox.contains(new Point(ball.getX(),ball.getY()))) {
				TL.add(ball);
			}
			
			if(TR.boundingBox.contains(new Point(ball.getX(),ball.getY()))) {
				TR.add(ball);
			}
			
			if(BR.boundingBox.contains(new Point(ball.getX(),ball.getY()))) {
				BR.add(ball);
			}
			
			if(BL.boundingBox.contains(new Point(ball.getX(),ball.getY()))) {
				BL.add(ball);
			}
			
		}else {

			if(!this.subDivided) {
			//subdividing
			int x = (int)this.boundingBox.getX();
			int y = (int)this.boundingBox.getY();
			int width = (int)this.boundingBox.getWidth()/2;		
			int height = (int)this.boundingBox.getHeight()/2;	
			
			TL = new Node(x,y,width,height);
			x = x + width;
			
			TR = new Node(x, y, width,height);
			y= y + height;
			
			BR = new Node(x,y,width,height);
			
			x = (int)this.boundingBox.getX();
			BL = new Node(x,y,width,height);
				
			this.subDivided = true; 
			}
			
			
	
			
		}

		
		

	}
	
	public ArrayList<BouncingBall> getBallList() {
		return ballList; 
	}

}
