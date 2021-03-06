package sharky.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sharky.graphics.Screen;
import sharky.graphics.Sprite;
import sharky.input.Keyboard;

public final class IntroState extends State {

	// ==================== Instance Data ====================
	
	private final String PATH;
	private int width;
	private int height;
	private int[] pixels;
	private int[] background;
	
	// ==================== Constructor ====================
	
	public IntroState(Keyboard keyboard, Screen screen, String path) {
		super(keyboard, screen);
		PATH = path;
		background = new int[SCREEN.getWIDTH() * SCREEN.getHEIGHT()];
	}
	
	// ==================== Method Implementations ====================
	
	@Override
	public void update() {
	}

	@Override
	public void render() {
		for (int i = 0; i < pixels.length; i++)
			if (pixels[i] != 0xFFFF00FF)
				SCREEN.setPixel(i, pixels[i]);
	}

	@Override
	public void init() {
		
		for (int i = 0; i < background.length; i++)
			background[i] = SCREEN.darkenColor(SCREEN.getPixels()[i], 0.50);
		
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource(PATH));
			int width = image.getWidth();
			int height = image.getHeight();
			this.width = width;
			this.height = height;
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			System.out.println("ERROR: could not load intro screen");
			e.printStackTrace();
		}
		
		for (int i = 0; i < background.length; i++)
			if (pixels[i] == 0xFFFF00FF)
				pixels[i] = background[i];
		
	}

}
