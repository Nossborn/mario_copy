package Game.Level.Blocks;

import java.awt.image.BufferedImage;

import Game.Screen.Screen;

public class Block {
	private double xPos, yPos;
	protected boolean collidable = false;
	private BufferedImage sprite;
	private String spritePath;

	boolean isVoid = false;

	public final static int col_MiscBlock = 0xffDDAA77;
	public final static int col_VoidBlock = 0xffFFFFFF;

	public Block(int xPos, int yPos, String spritePath) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.spritePath = spritePath;
		this.sprite = Screen.load(this.spritePath);
	}

	public boolean isCollidable() {
		return collidable;
	}

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public void render(Screen screen) {
		if(!isVoid) screen.render(sprite, (int) xPos, (int) yPos, false);
	}
}
