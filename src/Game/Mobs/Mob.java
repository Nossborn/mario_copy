package Game.Mobs;

import Game.Level.Level;
import Game.Screen.Screen;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Mob {
    private int mobSize = 16;
    protected double xPos,yPos = 0;
    protected double xVel, yVel = 0;
    boolean[] collisions;

    protected double baseLevel = 0 - mobSize;

    protected BufferedImage sprite;
    protected Screen screen;
    protected Level level;

    public Mob(Level level, Screen screen, double xPos, double yPos) {
        this.level = level;
        this.screen = screen;
        this.xPos = xPos;
        this.yPos = yPos;
        collisions = new boolean[4];
    }

    public boolean[] getCollisions() {
        //collisions: 0 - (up) | 1 - (right) | 2 - (down) | 3 - (left)
        Arrays.fill(collisions, Boolean.FALSE);

        int xLeft = (int) (xPos - 1);
        int xRight = (int) (xPos + mobSize);
        int yTop = (int) (yPos + mobSize);
        int yBottom = (int) (yPos - 1);

        //TODO Fix collisions so that players won't get stuck inside of blocks
        //TODO Change to corner-checking collisions(top two corner for top collision, right two for right collision etc)

        if (xVel >= 0) {
            collisions[1] = level.getColliBlocks(xRight / Level.blockSize, (int) yPos / Level.blockSize);
        }
        if (xVel <= 0) {
            collisions[3] = level.getColliBlocks(xLeft / Level.blockSize, (int) yPos / Level.blockSize);
        }

        if (yVel >= 0) {
            collisions[0] = level.getColliBlocks((int) xPos / Level.blockSize, yTop / Level.blockSize);
        }
        if (yVel <= 0) {
            // Also check yVel == 0 here bc when you are standing still you are colliding with ground
            collisions[2] = level.getColliBlocks((int) xPos / Level.blockSize, yBottom / Level.blockSize);
        }

        if (yPos <= baseLevel) collisions[2] = true;
        return collisions;
    }

    private void move() {
    }

    public void render() {
        screen.render(sprite, (int) xPos, (int) yPos, false);
    }

    public void update() {
        xPos += xVel;
        yPos += yVel;
        collisions = getCollisions();
        move();
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
