package Game.Screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Game;

public class Screen {

	private int[] pixels;
	private int width, height, scale;
	private int xOffset, yOffset;

	public Screen(int width, int height, int scale) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.scale = scale;
	}

	// Returns same pixel for each scale^2 grid
	public void renderAtScale(Game game) {
		int pixel, yScaled, xScaled, widthScaled;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixel = pixels[x + y * width];
				xScaled = x * scale;
				yScaled = y * scale;
				widthScaled = width * scale;
				for (int y1 = 0; y1 < scale; y1++) {
					for (int x1 = 0; x1 < scale; x1++) {
						game.setPixel((xScaled + x1) + (yScaled + y1) * widthScaled, pixel);
					}
				}
			}
		}
	}

	// Scrolling means it repeats horizontally infinitely
	public void renderScroll(BufferedImage image, int x, int y, boolean fixed) {
		// Does it even make sense for something that scrolls to be fixed to the screen?
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}

		for (int ypx = 0; ypx < image.getHeight(); ypx++) {
			int ya = ypx + y;
			if (ya < 0 || ya >= height) continue;
			for (int xpx = 0; xpx < image.getWidth(); xpx++) {
				int xa = xpx + x;
				while (xa >= width)
					xa -= (width);
				while (xa < 0)
					xa += (width);
				int colorPixel = image.getRGB(xpx, ypx);
				if (colorPixel != 0xffFF00FF) pixels[xa + ya * width] = colorPixel;
			}
		}
	}

	public void render(BufferedImage image, int x, int y, boolean fixed) {
	    y = height - y;
	    if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		for (int ypx = 0; ypx < image.getHeight(); ypx++) {
			int ya = ypx + y;
			if (ya < 0 || ya >= height) continue;
			for (int xpx = 0; xpx < image.getWidth(); xpx++) {
				int xa = xpx + x;
				if (xa < 0 || xa >= width) continue;
				int colorPixel = image.getRGB(xpx, ypx);
				if (colorPixel != 0xffFF00FF) pixels[xa + ya * width] = colorPixel;
			}
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xff87BBFF;
		}
	}

	public static BufferedImage load(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println(path + " NOT loaded");
			e.printStackTrace();
		}
		if (image == null) System.err.println("\"" + path + "\"" + " is NULL");
		return image;
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}
}
