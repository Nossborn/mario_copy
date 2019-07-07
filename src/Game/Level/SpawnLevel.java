package Game.Level;

import java.awt.image.BufferedImage;

import Game.Level.Blocks.Block;
import Game.Level.Blocks.MiscBlock;
import Game.Level.Blocks.VoidBlock;
import Game.Screen.Screen;

public class SpawnLevel extends Level {

	private int[] blocksCol;

	public SpawnLevel(String path) {
		super();
		createLevel(path);
	}

	private void createLevel(String path) {
		BufferedImage image = Screen.load(path);
		width = image.getWidth();
		height = image.getHeight();
		blocksCol = new int[height * width];
		blocks = new Block[height * width];
		image.getRGB(0, 0, width, height, blocksCol, 0, width);
		setBlocks();
	}

	private void setBlocks() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int blockCol = blocksCol[x + (height - 1 - y) * width];
				blocks[x + y * width] = block(blockCol, x * blockSize, (y) * blockSize);
			}
		}
	}

	private Block block(int blockCol, int xPos, int yPos) {
		Block block = null;
		switch (blockCol) {
		case Block.col_MiscBlock:
			block = new MiscBlock(xPos, yPos);
			break;

		case Block.col_VoidBlock:
			block = new VoidBlock(xPos, yPos);
			break;
		}

		if (block == null) {
			block = new VoidBlock(xPos, yPos);
		}

		return block;
	}
}
