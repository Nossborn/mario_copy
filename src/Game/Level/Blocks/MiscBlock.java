package Game.Level.Blocks;

public class MiscBlock extends Block {

	private static String spritePath = "res/block.png";

	public MiscBlock(int xPos, int yPos) {
		super(xPos, yPos, spritePath);
		collidable = true;
	}

}
