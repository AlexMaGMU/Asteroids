package net.mrpaul.ads.HM150.ps11.asteroids;

/*
 *Alex Ma
 *ADSA, period 4
 *Problem set 11
 *Asteroids
 *2/9/2015
 */
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {

	// Boundaries of window
	private int yBounds, xBounds;
	
	
	// Everything related to Ship movement
	private double acceleration, speed, rotateSpeed = 7, portLength = 200,
			accelDirection;
	
	
	// Booleans to detect keyboard input
	private boolean isForward = false, isLeft = false, isRight = false,
			isPort = false, isShoot = false, isBoost = false;


	/*
	 *ARGUMENTS:       inShape, an array of Points
	 *				   inPosition, a Point object
	 *				   inRotation, a double representing rotation in degrees
	 *				   yBounds, an int representing the boundaries of the Canvas
	 *				   xBounds, an int representing the boundaries of the Canvas
	 *RETURN:          a Ship Polygon of the specified parameters
	 *DESCRIPTION:     Constructs a Ship object of shape inShape at point inPosition
	 *				   rotated inRotation degrees, with specified boundaries
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public Ship(Point[] inShape, Point inPosition, double inRotation,
			int yBounds, int xBounds) {
		super(inShape, inPosition, inRotation);
		this.yBounds = yBounds;
		this.xBounds = xBounds;
	} // end of Ship constructor

	// Getters for Boundaries
	public int getXBounds() {
		return xBounds;
	} // end of method getXBounds
	public int getYBounds() {
		return yBounds;
	} // end of method getYBounds

	
	/*
	 *ARGUMENTS:       brush, a Graphics object
	 *RETURN:          void
	 *DESCRIPTION:     Draws the current Ship object
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

	}// end of method paint

	
	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     Using real life-ish physics, and the 
	 *				   parameters of this object
	 *				   this method alters the position
	 *				   of the current Ship object
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void move() {
		
		
		// If forward, increase acceleration, and change the direction of acceleration
		if (isForward) {
			if (acceleration < 20) {
				acceleration += .7;
				accelDirection = (accelDirection + this.rotation) / 2;
			} // end of if -- acceleration is less than 20
		} // end of if -- holding forward

		// Teleportation stuff, uses ship direction instead of teleportation direction
		else if (isPort) {
			position.setY(position.getY() + portLength
					* Math.sin(Math.toRadians(rotation + 90)));
			position.setX(position.getX() + portLength
					* Math.cos(Math.toRadians(rotation + 90)));
			accelDirection = this.rotation;
			speed = 6;
		} // end of if -- pressing Q
		
		else if (isBoost) {
			accelDirection = this.rotation;
			speed += 10;
		} // end of if -- pressing W

		// Rotation (rotate speed is constant)
		else if (isRight) {
			rotation += rotateSpeed;
		} // end of if -- pressing right 
		else if (isLeft) {
			rotation -= rotateSpeed;
		} // end of if -- pressing left

		

		
		// Changes speed based off of acceleration, and constantly decreases speed
		speed += acceleration / 6.5; 
		if (speed > 0)
			speed /= 1.01;

		
		// Actually moves the ship
		position.setY(position.getY() + speed
				* Math.sin(Math.toRadians(accelDirection + 90)));
		position.setX(position.getX() + speed
				* Math.cos(Math.toRadians(accelDirection + 90)));

		// Prevents constant teleportation
		isPort = false;
		isBoost = false;
		
		
		acceleration = 0; // Ship's limiter, comment out to unleash true power!!!
		
	} // end of method move

	
	/*
	 *ARGUMENTS:       none
	 *RETURN:          void
	 *DESCRIPTION:     If the current Ship object reaches
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
	 *RETURN:          void
	 *DESCRIPTION:     Set's position and rotation of
	 *				   current Ship object to default
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void toCenter() {
		position.setX(xBounds / 2);
		position.setY(yBounds / 2);
		speed = 0;
		acceleration = 0;
		rotation = 180;
		accelDirection = rotation;
	} // end of method toCenter

	

	

	/*
	 *ARGUMENTS:       e, a KeyEvent
	 *RETURN:          void
	 *DESCRIPTION:     Tracks key presses for movement
	 *				   purposes
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isForward = true;
		} // end of if -- up is the key that is pressed
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isLeft = true;
		} // end of if -- left is the key that is pressed 
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isRight = true;
		} // end of if -- right is the key that is pressed 
		else if (e.getKeyCode() == KeyEvent.VK_Q) {
			isPort = true;
		} // end of if -- q is the key that is pressed 
		else if (e.getKeyCode() == KeyEvent.VK_W) {
			isBoost = true;
		} // end of if -- w is the key that is pressed
	} // end of method keyPressed

	/*
	 *ARGUMENTS:       e, a KeyEvent
	 *RETURN:          void
	 *DESCRIPTION:     Tracks key releases for movement
	 *				   purposes
	 *ASSUMPTIONS:     Assumes correct usage
	 */
	public void keyReleased(KeyEvent e) {
		isForward = false;
		isLeft = false;
		isRight = false;
		isBoost = false;
		acceleration = 0;
	} // end of method keyReleased

	// Unused member of KeyListener interface
	public void keyTyped(KeyEvent e) {
	} // end of unused method keyTyped
} // end of Class Ship
