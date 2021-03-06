package sharky.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sharky.graphics.Screen;
import sharky.graphics.Sprite;
import sharky.input.Keyboard;

public class GameOverState extends State {

	// ==================== Instance Data ====================
	
	private final String PATH;
	private int width, height;
	private int[] background;
	private int[] pixels;
	
	// ==================== Constructor ====================
	
	public GameOverState(Keyboard keyboard, Screen screen, String path) {
		super(keyboard, screen);
		PATH = path;
		background = new int[SCREEN.getWIDTH() * SCREEN.getHEIGHT()];
	}
	
	// ==================== Method Implementation ====================
	
	@Override
	public void init() {
		
		for (int i = 0; i < background.length; i++)
			background[i] = SCREEN.darkenColor(SCREEN.getPixels()[i], 0);
		
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource(PATH));
			int width = this.width = image.getWidth();
			int height = this.height = image.getHeight();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			System.out.println("ERROR: could not load game over screen");
			e.printStackTrace();
		}
		
		for (int i = 0; i < background.length; i++)
			if (pixels[i] == 0xFFFF00FF)
				pixels[i] = background[i];
		
	}

	@Override
	public void update() {
	}

	@Override
	public void render() {
		for (int i = 0; i < pixels.length; i++)
			SCREEN.setPixel(i, pixels[i]);
	}

}
