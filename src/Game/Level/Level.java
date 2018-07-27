package Game.Level;

import Game.Level.Blocks.Block;
import Game.Screen.Screen;

public class Level {

	protected int height, width;
	private static String backgroundPath = "res/background.png";
	private static String levelPath = "res/map1.png";
	protected Block[] blocks;

	protected static int blockSize = 16;
	private boolean[] collisions = new boolean[4];
	public static Level spawn = new SpawnLevel(levelPath);

	public Level() {
	}

/*	// Why should this be in the level class instead of Block class? it shouldn't
	public boolean[] blockCollision(int x1, int y1, int x2, int y2) {
		for (int i = 0; i < collisions.length; i++)
			collisions[i] = false;
		return collisions;
	}
*/
	public void render(Screen screen) {
		screen.renderScroll(Screen.load(backgroundPath), 0, 0, false);
		renderBlocks(screen);
	}

	private void renderBlocks(Screen screen) {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].render(screen);
		}
	}

}