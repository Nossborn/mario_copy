package Game.Level.Blocks;

@SuppressWarnings("SpellCheckingInspection")
public class VoidBlock extends Block {

	private static String spritePath = "res/voidblock.png";

	public VoidBlock(int xPos, int yPos) {
		super(xPos, yPos, spritePath);
		isVoid = true;
	}

}
