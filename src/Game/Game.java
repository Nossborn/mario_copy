package Game;

import Game.Input.Keyboard;
import Game.Level.Level;
import Game.Mobs.Player;
import Game.Screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

//TODO Add - collision for blocks

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private String title = "Mario Copy";
	public static int WIDTH = 300;
	public static int HEIGHT = 160;
	private static int SCALE = 3;

	private boolean running = false;
	private static Game game;

	private JFrame frame;
	private Thread thread;
	private Screen screen;
	private Level level;
	private Keyboard key;
	private Player player;

	private BufferedImage finalImage = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) finalImage.getRaster().getDataBuffer()).getData();

	//public static BufferedImage background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private Game() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame();
		screen = new Screen(WIDTH, HEIGHT, SCALE);
		key = new Keyboard();

		addKeyListener(key);

		level = Level.spawn;
		player = new Player(screen, key, 30, 30);
	}

	private void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	private void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1_000_000_000.0 / 60;
		double delta = 0;
		double delta2 = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			delta2 += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			while (delta2 >= 1) {
				render();
				frames++;
				delta2--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(" UPS: " + updates + "  FPS: " + frames);
				frame.setTitle(title + " |  UPS: " + updates + "  FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		level.render(screen);
		player.render();

		screen.renderAtScale(game);
		Graphics g = bs.getDrawGraphics();
		g.drawImage(finalImage, 0, 0, getWidth(), getHeight(), null);
		g.drawString((int) player.getX() + ":x | " + (int) player.getY() + ":y", 10, 20);
		g.drawString((int) player.getVelX() + ":xV | " + (int) player.getVelY() + ":yV", 10, 40);
		g.dispose();
		bs.show();
	}

	public void setPixel(int i, int value) {
		game.pixels[i] = value;
	}

	private void update() {
		key.update();
		player.update();
	}

	public static void main(String[] args) {
		game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
