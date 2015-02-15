package net.mrpaul.ads.HM150.ps11.asteroids;

/*
 *Alex Ma
 *ADSA, period 4
 *Problem set 11
 *Asteroids
 *2/9/2015
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Bullet {
	public Point position;
	public double rotation;
	private int speed = 10;
	private int traveled = 0;
	int xBounds = 800;
	int yBounds = 600;
	boolean space = false;
	int degreeRot = 10;


	/*
	 *ARGUMENTS:       inPosition, a Point object
	 *				   inRotation, a double representing rotation in degrees
	 *				   yBounds, an int representing the boundaries of the Canvas
	 *				   xBounds, an int representing the boundaries of the Canvas
	 *RETURN:          a Bullet of the specified parameters
	 *DESCRIPTION:     Constructs a bullet object at point inPosition
	 *				   rotated inRotation degrees, with specified boundaries
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public Bullet(Point inPosition, double inRotation, int xBounds, int yBounds) {
		this.position = inPosition;
		this.rotation = inRotation;
		this.xBounds = xBounds;
		this.yBounds = yBounds;
	} // end of Bullet constructor

	
	/*
	 *ARGUMENTS:       brush, a Graphics object
	 *RETURN:          void
	 *DESCRIPTION:     Draws the current Bullet object
	 *				   using brush
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public void paint(Graphics brush) {

		// draw a small circle with center position (x,y) and diameter of 7 pixels
		brush.setColor(Color.blue);
		brush.fillOval((int) position.x, (int) position.y, 7, 7);
	} // end of method paint

	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     Mutates the position of the
	 *				   current Bullet object at 
	 *				   rate "speed"
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void move() {

		position.x = position.x + speed * Math.cos(Math.toRadians(rotation + 90));
		position.y = position.y + speed * Math.sin(Math.toRadians(rotation + 90));
		traveled+=speed;

	} // end of method move

	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     If the current Bullet object reaches
	 *				   the boundaries of the Canvas, wraps
	 *				   it around to the opposite edge
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void wrapAround() {
		if (position.getY() > yBounds)
			position.setY(0);
		else if (position.getY() < 0)
			position.setY(yBounds);
		else if (position.getX() > xBounds)
			position.setX(0);
		else if (position.getX() < 0)
			position.setX(xBounds);
	} // end of method wrapAround

	/*
	 *ARGUMENTS:       none
	 *RETURN:          traveled, the distance traveled by this Bullet
	 *DESCRIPTION:     returns traveled
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public int getTraveled() {
		return traveled;
	} // end of method getTraveled

}// end of Class Bullet