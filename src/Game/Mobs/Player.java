package Game.Mobs;

import java.awt.image.BufferedImage;

import Game.Game;
import Game.Input.Keyboard;
import Game.Screen.Screen;

public class Player extends Mob{
	private int playerSize = 24;
	private double xPos = 0;
	private double yPos = 0;
	private double xVel = 0;
	private double yVel = 0;
	private double xPlayerOffs = (Game.WIDTH - playerSize) / 2;
	private double yPlayerOffs = Game.HEIGHT - playerSize;
	private double baseLevel = 0 - playerSize;
	private boolean[] collisions = new boolean[] { false, false, false, false };

	private Screen screen;
	private Keyboard key;

	private BufferedImage sprite;

	public Player(Screen screen, Keyboard key, double xPos, double yPos) {
		this.screen = screen;
		this.key = key;
		this.sprite = Screen.load("res/character.png");
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void update() {
		xPos += xVel;
		yPos += yVel;
		move();
		collisions = collisions();
	}

	// Checking collision after moving :/
	private void move() {
		if (key.up && collisions[2]) yVel = 10;
		if (key.right) xVel = 5;
		if (key.left) xVel = -5;

		// Gravity
		if (!collisions[2]) yVel -= 0.5;
		// Stop player after collision
		if (collisions[2] && yVel < 0) {
			yVel = 0;
			xVel = 0;
		} else if (collisions[0] && yVel > 0) yVel = 0;
		if (collisions[1] && xVel > 0 || collisions[3] && xVel < 0) xVel = 0;
		// Stops Player from falling off map
		if (yPos < baseLevel) yPos = baseLevel;

		screen.setOffset((int) (xPos), 0);
	}

	private boolean[] collisions() {
		// Index: 0 - Up, 1 - Right, 2 - Down, 3 - Left
		for (int i = 0; i < collisions.length; i++) {
			collisions[i] = false;
		}

		if (yPos <= baseLevel) collisions[2] = true;
		return collisions;
	}

	private int[] getBlockColliCords(int x1, int y1, int x2, int y2) {
	    return null;
	}

	public void render() {
		screen.render(sprite, (int) (xPos + xPlayerOffs), (int) (yPlayerOffs - yPos), false);
	}

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public double getVelX() {
		return xVel;
	}

	public double getVelY() {
		return yVel;
	}
}
