package net.mrpaul.ads.HM150.ps11.asteroids;

/*
 *Alex Ma
 *ADSA, period 4
 *Problem set 11
 *Asteroids
 *2/9/2015
 */
/*
 CLASS: Asteroids
 DESCRIPTION: Extending Game, Asteroids is all in the paint method.
 NOTE: This class is the metaphorical "main method" of your program,
 it is your control center.

 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;

class Asteroids extends Game implements KeyListener {

	// Global statics cross-games
	private static int shipCount = 30, bulletLifespan = 500, numAsteroids = 5;

	// Game-level counters
	private int invincibilityFrames = 20, score = 0, lifeCount = shipCount;

	// The Ship
	private Ship rohan;

	// The Asteroids
	private Asteroid[] enemigos = new Asteroid[numAsteroids];

	// The Bullets
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	// The Helpers & Indicators
	private Random rng = new Random();
	private Polygon[] lives = new Polygon[lifeCount];
	private boolean isShot = false, scoreCanIncrement = true;

	
	
	// The Constructor
	/*
	 *ARGUMENTS:       none
	 *RETURN:          An instance of an Asteroids object
	 *DESCRIPTION:     The Constructor for the Asteroids class
	 *ASSUMPTIONS:     Assumes correct use
	 */
	public Asteroids() {

		// Triggers super-constructor, sets Canvas size
		super("Asteroids!", 1000, 800);

		// Makes this instance a KeyListener
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);

		// Point array that will become the shape of the Ship
		Point[] shipShape = { new Point(-10, 0), new Point(0, 10),
				new Point(10, 0), new Point(0, 20) };

		// Actually constructs the Ship
		rohan = new Ship(shipShape, new Point(500, 400), 180, 800, 1000);

		// Makes the Ship a Key Listener
		this.addKeyListener(rohan);

		
		// Instantiates Polygons representing the Lives
		for (int c = 0; c < lifeCount; c++) {
			lives[c] = new Polygon(shipShape, new Point(10 + 25 * c, 10), 180);
		} // end of for loop

		
		// Constructs the Asteroids to be semi-random shapes
		for (int c = 0; c < numAsteroids; c++) {

			// Makes the semi-random shapes
			Point[] asteroidShape = {
					new Point(120 + rng.nextInt(40), rng.nextInt(40)),
					new Point(rng.nextInt(40), 60 + rng.nextInt(40)),
					new Point(60 + rng.nextInt(40), 180 + rng.nextInt(40)),
					new Point(90 + rng.nextInt(40), 180 + rng.nextInt(40)),
					new Point(120 + rng.nextInt(40), 180 + rng.nextInt(40)),
					new Point(142 + rng.nextInt(40), 90 + rng.nextInt(40)) };

			// Actually constructs each Asteroid at a semi-random location
			enemigos[c] = new Asteroid(asteroidShape, new Point(
					rng.nextInt(200), rng.nextInt(200)), rng.nextInt(360), 800,
					1000);

		} // end of Asteroid constructor for loop

	} // end of Asteroids game constructor

	
	
	
	
	/*
	 *ARGUMENTS:       brush, an object of the Graphics class
	 *RETURN:          void
	 *DESCRIPTION:     Draws using brush all of the properties
	 *				   of the current Asteroids class
	 *ASSUMPTIONS:     Assumes correct parameters
	 */
	public void paint(Graphics brush) {

		// Collision boolean handles any Asteroid - ship collision
		boolean collision = false;

		
		// Paints window black
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		

		// Handles invincibility frames (increments through them, sets brush to red)
		if (invincibilityFrames > 0) {
			brush.setColor(Color.red);
			invincibilityFrames--;
		} // end of if -- invincibility frames are active
		else
			brush.setColor(Color.white);

		
		// Life Counter (Additional method "paint" added to Polygon)
		for (int c = 0; c < lifeCount; c++) {
			lives[c].paint(brush);
		} // end of for loop: draw life counter

		
		// Draw's the "score" String below the Life Counter
		if (score > 0)
			brush.drawString("Score: $" + score, 10, 50);
		else
			brush.drawString("Score: " + score, 10, 50);

		// Loop to handle in-game Asteroid operations
		for (int c = 0; c < numAsteroids; c++) {
			if (enemigos[c].hit == false) {
				enemigos[c].paint(brush);
				enemigos[c].move();
				enemigos[c].wrapAround();
			} // end of if -- Asteroid[c] was not hit

			// Detects Asteroid - Bullet Collisions
			for (Bullet b : bullets) {
				if (enemigos[c].contains(b.position)) {
					if (enemigos[c].hit == false)
						score += 10000000;
					enemigos[c].hit = true;
				} // end of if -- a bullet just hit Asteroid[c]
			} // end of for loop: detect Asteroid - Bullet Collisions

			// Detects Asteroid - Ship Collisions
			collision = collision
					|| (rohan.collides(enemigos[c])
							&& (invincibilityFrames <= 0) && (!enemigos[c].hit));


		}// end of for loop: handling Asteroid operations

		
		// Paints the Ship and handles Ship operations
		rohan.paint(brush);
		rohan.move();
		rohan.wrapAround();

		
		
		// Temporary ArrayList to contain Bullets to be deleted
		ArrayList<Bullet> toBeRemoved = new ArrayList<Bullet>();

		// Puts deletable Bullets in the ArrayList
		for (Bullet b : bullets) {

			if (b.getTraveled() >= bulletLifespan && bullets.contains(b))
				toBeRemoved.add(b);
		} // end of for loop: put any deletable Bullets in ArrayList toBeRemoved 
		
		// Deletes the Bullets
		for (Bullet b : toBeRemoved) {
			bullets.remove(b);
		} // end of for loop: remove any Bullets that were in toBeRemoved

		
		
		// Instantiates shot bullets
		if (isShot == true) {
			bullets.add(new Bullet(rohan.position.clone(), rohan.rotation,
					rohan.getXBounds(), rohan.getYBounds()));
			isShot = false;
		} // end of if -- spacebar was pressed

		// Does Bullet movement stuff
		for (Bullet b : bullets) {
			b.paint(brush);
			b.move();
			b.wrapAround();
		} // end of for loop: handle Bullet movement

		// Does things if ship has collided
		if (collision) {
			lifeCount--;
			rohan.toCenter();
			invincibilityFrames = 70;
			collision = false;
		} // end of if -- Ship has collided with an Asteroid
		
		// Does game-over things
		if (lifeCount <= 0) {
			brush.drawString("Game Over", 500, 400);
			brush.drawString("Score is: " + score, 500, 420);
			scoreCanIncrement = false;
		} // end of if -- lives have hit zero
		
	} // end of paint method

	
	// Tracks when the Spacebar is pressed
	public void keyPressed(KeyEvent e) {

		// set the shot flag when space key being pressed
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isShot = true;
		} // end of if -- spacebar was pressed
		
	}// end of keyPressed method

	// Ignore these, they're part of the KeyListener interface, but require no
	// implementation code
	public void keyReleased(KeyEvent e) {
	} // end of unused KeyReleased method

	public void keyTyped(KeyEvent arg0) {
	} // end of unused KeyTyped method

	/*
	 * This is a program which instantiates an instance of an Asteroids game and
	 * uses stop motion while tracking user input and object properties to
	 * generate an animated game for the user to play.
	 */
	public static void main(String[] args) {

		Asteroids a = new Asteroids();
		a.repaint();

	} // end of main method

} // end of Asteroids class