package sharky.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	// ==================== Instance Data ====================
	
	/** The width of the sprite */
	private final int WIDTH;
	
	/** The height of the sprite */
	private final int HEIGHT;
	
	/** The row of the sprite on the sprite sheet */
	private final int ROW;
	
	/** The column of the sprite on the sprite sheet */
	private final int COL;
	
	/** The pixels that make up this sprite */
	private int[] pixels;
	
	/** The pixels of the spritesheet that contains this sprite */
	private int[] spritesheet;
	
	/** The path to the spritesheet */
	private final String PATH;
	
	/** The spritesheet's width */
	private int spritesheetWidth;
	
	/** The spritesheet's height */
	private int spritesheetHeight;
	
	private final String NAME;
	
	private static final String DEFAULT_PATH = "/textures/spritesheet10.png";
	
	// ==================== Static Objects  ====================
	
	public static final Sprite SHARK1 = new Sprite(48, 32, 0, 0, "Shark 1");
	public static final Sprite SHARK2 = new Sprite(48, 32, 2, 0, "Shark 2");
	public static final Sprite SHARK3 = new Sprite(48, 32, 4, 0, "Shark 3");
	
	public static final Sprite SHARK_ELECTRIC = new Sprite(48, 32, 7, 0, "Shark Electric");
	
	public static final Sprite FISH_ORANGE = new Sprite(16, 16, 6, 0, "Fish Orange");
	public static final Sprite FISH_ORANGEx2 = new Sprite(16, 16, 6, 0, 2, "Fish Orange x2");
	public static final Sprite FISH_ORANGEx3 = new Sprite(16, 16, 6, 0, 3, "Fish Orange x3");
	public static final Sprite FISH_ORANGEx4 = new Sprite(16, 16, 6, 0, 4, "Fish Orange x4");
	
	public static final Sprite FISH_YELLOW = new Sprite(16, 16, 6, 1, "Fish Yellow");
	public static final Sprite FISH_YELLOWx2 = new Sprite(16, 16, 6, 1, 2, "Fish Yellow x2");
	public static final Sprite FISH_YELLOWx3 = new Sprite(16, 16, 6, 1, 3, "Fish Yellow x3");
	public static final Sprite FISH_YELLOWx4 = new Sprite(16, 16, 6, 1, 4, "Fish Yellow x4");
	
	public static final Sprite FISH_RED = new Sprite(16, 16, 6, 2, "Fish Red");
	public static final Sprite FISH_REDx2 = new Sprite(16, 16, 6, 2, 2, "Fish Red x2");
	public static final Sprite FISH_REDx3 = new Sprite(16, 16, 6, 2, 3, "Fish Red x3");
	public static final Sprite FISH_REDx4 = new Sprite(16, 16, 6, 2, 4, "Fish Red x4");

	public static final Sprite JELLYFISH = new Sprite(48, 48, 0, 13, "Jellyfish");
	public static final Sprite JELLYFISHx2 = new Sprite(48, 48, 0, 13, 2, "Jellyfish x2");
	public static final Sprite JELLYFISHx3 = new Sprite(48, 48, 0, 13, 3, "Jellyfish x3");

	public static final Sprite PUFFERFISH1 = new Sprite(32, 32, 3, 14, "Pufferfish 1");
	public static final Sprite PUFFERFISH1x2 = new Sprite(32, 32, 3, 14, 2, "Pufferfish 1 x2");
	public static final Sprite PUFFERFISH1x3 = new Sprite(32, 32, 3, 14, 2, "Pufferfish 1 x3");
	public static final Sprite PUFFERFISH2 = new Sprite(32, 32, 5, 14, "Pufferfish 2");
	public static final Sprite PUFFERFISH2x2 = new Sprite(32, 32, 5, 14, 2, "Pufferfish 2 x2");
	public static final Sprite PUFFERFISH2x3 = new Sprite(32, 32, 5, 14, 3, "Pufferfish 2 x3");
	public static final Sprite PUFFERFISH3 = new Sprite(32, 32, 7, 14, "Pufferfish 3");
	public static final Sprite PUFFERFISH3x2 = new Sprite(32, 32, 7, 14, 2, "Pufferfish 3 x2");
	public static final Sprite PUFFERFISH3x3 = new Sprite(32, 32, 7, 14, 3, "Pufferfish 3 x3");
	
	public static final Sprite SKY = new Sprite(32, 32, 0, 5, "Sky");
	public static final Sprite CLOUD = new Sprite(32, 32, 2, 5, "Cloud");
	
	public static final Sprite WAVE1 = new Sprite(32, 32, 0, 3, "Wave 1");
	public static final Sprite WAVE2 = new Sprite(32, 32, 0, 7, "WAve 2");
	public static final Sprite WAVE3 = new Sprite(32, 32, 2, 7, "Wave 3");
	public static final Sprite WAVE4 = new Sprite(32, 32, 4, 7, "Wave 4");
	
	public static final Sprite WATER1 = new Sprite(32, 32, 2, 3, "Water 1");
	public static final Sprite WATER2 = new Sprite(32, 32, 4, 3, "Water 2");
	public static final Sprite WATER3 = new Sprite(32, 32, 6, 3, "Water 3");
	public static final Sprite WATER4 = new Sprite(32, 32, 8, 3, "Water 4");
	public static final Sprite WATER5 = new Sprite(32, 32, 10, 3, "Water 5");
	public static final Sprite WATER6 = new Sprite(32, 32, 12, 3, "Water 6");
	
	public static final Sprite SAND_SOLID = new Sprite(32, 32, 6, 7, "Sand Solid");
	public static final Sprite SAND1 = new Sprite(32, 32, 4, 5, "Sand 1");
	public static final Sprite SAND2 = new Sprite(32, 32, 6, 5, "Sand 2");
	public static final Sprite SAND3 = new Sprite(32, 32, 8, 5, "Sand 3");
	public static final Sprite SAND4 = new Sprite(32, 32, 10, 5, "Sand 4");
	public static final Sprite SAND5 = new Sprite(32, 32, 12, 5, "Sand 5");
	
	public static final Sprite GRASS = new Sprite(32, 32, 14, 0, "Grass");
	public static final Sprite GRASS_CORNER = new Sprite(32, 32, 14, 12, "Grass Corner");
	public static final Sprite GRASS_CORNER_FLIP = new Sprite(32, 32, 14, 12, true, "Grass Corner Flip");	
	
	public static final Sprite BORDER_SKY1 = new Sprite(32, 32, 14, 2, "Border Sky 1");
	public static final Sprite BORDER_SKY1_FLIP = new Sprite(32, 32, 14, 2, true, "Border Sky 1 Flip");
	public static final Sprite BORDER_SKY2 = new Sprite(32, 32, 14, 4, "Border Sky 2");
	public static final Sprite BORDER_SKY2_FLIP = new Sprite(32, 32, 14, 4, true, "Border Sky 2 Flip");
	public static final Sprite BORDER_WATER1 = new Sprite(32, 32, 14, 6, "Border Water 1");
	public static final Sprite BORDER_WATER1_FLIP = new Sprite(32, 32, 14, 6, true, "Border Water 1 Flip");
	public static final Sprite BORDER_WATER2 = new Sprite(32, 32, 14, 8, "Border Water 2");
	public static final Sprite BORDER_WATER2_FLIP = new Sprite(32, 32, 14, 8, true, "Border Water 2 Flip");
	public static final Sprite BORDER_WAVE = new Sprite(32, 32, 14, 10, "Border Wave");
	public static final Sprite BORDER_WAVE_FLIP = new Sprite(32, 32, 14, 10, true, "Border Wave Flip");
	public static final Sprite BORDER_SOLID = new Sprite(32, 32, 12, 12, "Border Solid");
	
	public static final Sprite VOID = new Sprite(32, 32, 14, 14, "Void");
	
	// ==================== Constructors ====================
	
	public Sprite(int width, int height, String name) {
		WIDTH = width;
		HEIGHT = height;
		ROW = 0;
		COL = 0;
		PATH = DEFAULT_PATH;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0xFFFFFF;
	}
	
	public Sprite(int width, int height, int row, int col, String name) {
		WIDTH = width;
		HEIGHT = height;
		ROW = row;
		COL = col;
		PATH = DEFAULT_PATH;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		loadSpriteSheet(PATH);
		loadSprite(WIDTH, HEIGHT, ROW, COL, false);
		
	}
	
	public Sprite(int width, int height, int row, int col, int scale, String name) {
		WIDTH = width * scale;
		HEIGHT = height * scale;
		ROW = row;
		COL = col;
		PATH = DEFAULT_PATH;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		loadSpriteSheet(PATH);
		loadSprite(WIDTH / scale, HEIGHT / scale, ROW, COL, false);
		scale(scale);
	}
	
	public Sprite(int width, int height, int row, int col, boolean flip, int scale, String name) {
		WIDTH = width * scale;
		HEIGHT = height * scale;
		ROW = row;
		COL = col;
		PATH = DEFAULT_PATH;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		loadSpriteSheet(PATH);
		loadSprite(WIDTH / scale, HEIGHT / scale, ROW, COL, flip);
		scale(scale);
	}
	
	public Sprite(int width, int height, int row, int col, boolean flip, String name) {
		WIDTH = width;
		HEIGHT = height;
		ROW = row;
		COL = col;
		PATH = DEFAULT_PATH;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		loadSpriteSheet(PATH);
		loadSprite(WIDTH, HEIGHT, ROW, COL, flip);
	}
	
	public Sprite(int width, int height, int row, int col, boolean flip, String path, String name) {
		WIDTH = width;
		HEIGHT = height;
		ROW = row;
		COL = col;
		PATH = path;
		NAME = name;
		
		pixels = new int[WIDTH * HEIGHT];
		loadSpriteSheet(PATH);
		loadSprite(WIDTH, HEIGHT, ROW, COL, flip);		
	}
	
	// ==================== Private Methods ====================
	
	private void loadSpriteSheet(String path) {
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
			int width = image.getWidth();
			int height = image.getHeight();
			spritesheetWidth = width;
			spritesheetHeight = height;
			spritesheet = new int[spritesheetWidth * spritesheetHeight];
			image.getRGB(0, 0, width, height, spritesheet, 0, width);
		} catch (IOException e) {
			System.out.println("ERROR: could not load sprite sheet");
			e.printStackTrace();
		}
	}
	
	private void loadSprite(int width, int height, int row, int col, boolean flip) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int xSprite = (flip ? width - 1 - x : x);
				pixels[y * WIDTH + x] = spritesheet[(row * 16 + y) * spritesheetWidth + (col * 16 + xSprite)];
			}
		}
	}
	
	// ==================== Public Methods ====================
	
	public void scale(int scale) {
		Sprite newSprite = new Sprite(WIDTH, HEIGHT, "New Sprite");
		
		for (int y = 0; y < WIDTH; y++)
			for (int x = 0; x < HEIGHT; x++)
				newSprite.setPixel(x, y, this.getPixel(x / scale, y / scale));
		
		for (int i = 0; i < newSprite.getPixels().length; i++)
			this.setPixel(i, newSprite.getPixel(i));
	}
	
	public static Sprite scaleSprite(Sprite sprite, double scale) {
		int oldWidth = sprite.getWIDTH();
		int oldHeight = sprite.getHEIGHT();
		int newWidth = (int) (sprite.getWIDTH() * scale);
		int newHeight = (int) (sprite.getHEIGHT() * scale);
		
		Sprite newSprite = new Sprite(newWidth, newHeight, "New Sprite");

		int[] toPixels = new int[newWidth * newHeight];
		
		BufferedImage fromImage = new BufferedImage(oldWidth, oldHeight, BufferedImage.TYPE_INT_RGB);
		BufferedImage toImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics = (Graphics2D) toImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		fromImage.setRGB(0, 0, oldWidth, oldHeight, sprite.getPixels(), 0, oldWidth);
		graphics.drawImage(fromImage, 0, 0, newWidth, newHeight, null);
		toImage.getRGB(0, 0, newWidth, newHeight, toPixels, 0, newWidth);
		newSprite.setPixels(toPixels);
		
		return newSprite;
	}
	
	// ==================== Getters and Setters ====================
	
	public int getWIDTH() { return WIDTH; }
	public int getHEIGHT() { return HEIGHT; }
	
	public int[] getPixels() { return pixels; }
	public void setPixels(int[] pixels) { this.pixels = pixels; } 
	
	public int getPixel(int i) { return pixels[i]; }
	public int getPixel(int x, int y) { return pixels[y * WIDTH + x]; }
	public void setPixel(int i, int color) { pixels[i] = color; }
	public void setPixel(int x, int y, int color) { pixels[y * WIDTH + x] = color; }
	
	public String getNAME() { return NAME; }
	
	
}
