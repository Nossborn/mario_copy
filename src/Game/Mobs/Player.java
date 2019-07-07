package Game.Mobs;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import Game.Game;
import Game.Input.Keyboard;
import Game.Level.Level;
import Game.Screen.Screen;

public class Player extends Mob {
    private int mobSize = 16;
    private double xPlayerOffs = mobSize / 2;
    private double yPlayerOffs = mobSize / 2;

    private Keyboard key;
    private BufferedImage sprite;

    public Player(Level level, Screen screen, Keyboard key, double xPos, double yPos) {
        super(level, screen, xPos, yPos);
        this.key = key;
        this.sprite = Screen.load("res/character.png");
        this.xPos = xPos + xPlayerOffs;
        this.yPos = yPos + yPlayerOffs;
    }

    public void update() {
        xPos += xVel;
        yPos += yVel;
        collisions = getCollisions();
        move();
    }

    private void move() {
        //collisions: 0 - (up) | 1 - (right) | 2 - (down) | 3 - (left)
        if (key.up && collisions[2]) yVel = 4;
        if (key.right) xVel = 1;
        if (key.left) xVel = -1;

        // Gravity
        if (!collisions[2]) yVel -= 0.1;

        // Stop player after a collision
        if (collisions[2]) {
            if (!(key.right || key.left)) xVel = 0;
            if (yVel < 0) yVel = 0;
        } else if (collisions[0] && yVel > 0) yVel = 0;

        if (collisions[1] && xVel > 0) xVel = 0;
        else if (collisions[3] && xVel < 0) xVel = 0;

        screen.setOffset((int) (xPos)- (Game.WIDTH - mobSize) / 2, Level.blockSize);
    }

    public void render() {
        screen.render(sprite, (int) (xPos), (int) (yPos), false);
    }
}
