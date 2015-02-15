package net.mrpaul.ads.HM150.ps11.asteroids;

/*
 *Alex Ma
 *ADSA, period 4
 *Problem set 11
 *Asteroids
 *2/9/2015
 */
import java.awt.Graphics;

public class Asteroid extends Polygon {
	private int yBounds, xBounds, speed = 3;
	public boolean hit = false;

	/*
	 *ARGUMENTS:       inShape, an array of Points
	 *				   inPosition, a Point object
	 *				   inRotation, a double representing rotation in degrees
	 *				   yBounds, an int representing the boundaries of the Canvas
	 *				   xBounds, an int representing the boundaries of the Canvas
	 *RETURN:          an Asteroid Polygon of the specified parameters
	 *DESCRIPTION:     Constructs an Asteroid object of shape inShape at point inPosition
	 *				   rotated inRotation degrees, with specified boundaries
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public Asteroid(Point[] inShape, Point inPosition, double inRotation,
			int yBounds, int xBounds) {
		super(inShape, inPosition, inRotation);
		this.yBounds = yBounds;
		this.xBounds = xBounds;
	} // end of Asteroid constructor

	/*
	 *ARGUMENTS:       brush, a Graphics object
	 *RETURN:          void
	 *DESCRIPTION:     Draws the current Asteroid object
	 *				   using brush
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public void paint(Graphics brush) {
		Point[] points = this.getPoints();
		int[] x = new int[points.length];
		int[] y = new int[points.length];
		for (int c = 0; c < points.length; c++) {
			x[c] = (int) (points[c].getX());
			y[c] = (int) (points[c].getY());
		} // end of for loop: go through all members of points array
		brush.drawPolygon(x, y, points.length);
	} // end of method paint
	
	
	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     Mutates the position of the
	 *				   current Asteroid object at 
	 *				   rate "speed"
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void move() {
		
		position.x = position.x + speed * Math.cos(Math.toRadians(rotation + 90));
		position.y = position.y + speed * Math.sin(Math.toRadians(rotation + 90));
		
	} // end of method move
	
	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     If the current Asteroid object reaches
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
	
} // end of Class Asteroid
