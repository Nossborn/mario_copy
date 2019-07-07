package Game.Level;

import Game.Level.Blocks.Block;
import Game.Screen.Screen;

public class Level {

	protected int height, width;
	private static String backgroundPath = "res/background.png";
	private static String levelPath = "res/map-1.png";
	protected Block[] blocks;

	public static int blockSize = 16;
	public static Level spawn = new SpawnLevel(levelPath);

	public Level() {
	}

	public void update() {
	}

	public void render(Screen screen) {
		screen.renderScroll(Screen.load(backgroundPath), 0, 0, false);
		renderBlocks(screen);
	}

	private void renderBlocks(Screen screen) {
		for (Block block : blocks) {
			block.render(screen);
		}
	}

	public boolean getColliBlocks(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return false;
		return blocks[(y)*width + x].isCollidable();
	}

}