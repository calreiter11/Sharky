package sharky.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import sharky.graphics.Tile;

public class Level {
	
	// ==================== Level Settings ====================
	
	/** The width of the default level */
	private static final int DEFAULT_LEVEL_WIDTH = 256;
	
	/** The height of the default level */
	private static final int DEFAULT_LEVEL_HEIGHT = 256;
	
	/** The total number of fish entities in the level */
	private static final int TOTAL_FISH = 500;
	
	/** The total number of jellyfish entities in the level */
	private static final int TOTAL_JELLYFISH = 50;
	
	/** The total number of pufferfish entities in the level */
	private static final int TOTAL_PUFFERFISH = 50;
	
	private static final int TOTAL_FISHx2 = TOTAL_FISH / 2;
	private static final int TOTAL_FISHx3 = TOTAL_FISHx2 / 2;
	private static final int TOTAL_FISHx4 = TOTAL_FISHx3 / 2;
	private static final int TOTAL_JELLYFISHx2 = TOTAL_JELLYFISH / 2;
	private static final int TOTAL_JELLYFISHx3 = TOTAL_JELLYFISHx2 / 2;
	private static final int TOTAL_PUFFERFISHx2 = TOTAL_PUFFERFISH / 2;
	private static final int TOTAL_PUFFERFISHx3 = TOTAL_PUFFERFISHx2 / 2;
	
	private static final int WAVE_LAYER = 4;
	
	// ==================== Tile Colors ====================
	
	private static final int SKY_COLOR = 0;
	private static final int CLOUD_COLOR = 1;
	
	private static final int WAVE1_COLOR = 2;
	private static final int WAVE2_COLOR = 3;
	private static final int WAVE3_COLOR = 4;
	private static final int WAVE4_COLOR = 5;
	private static final int[] WAVE_COLORS = {WAVE1_COLOR, WAVE2_COLOR, WAVE3_COLOR, WAVE4_COLOR};
	
	private static final int WATER1_COLOR = 6;
	private static final int WATER2_COLOR = 7;
	private static final int WATER3_COLOR = 8;
	private static final int WATER4_COLOR = 9;
	private static final int WATER5_COLOR = 10;
	private static final int WATER6_COLOR = 11;
	private static final int[] WATER_COLORS = {WATER1_COLOR, WATER3_COLOR, WATER4_COLOR};
	private static final int[] WATER_BUBBLE_COLORS = {WATER2_COLOR, WATER5_COLOR, WATER6_COLOR};
	
	private static final int SAND1_COLOR = 12;
	private static final int SAND2_COLOR = 13;
	private static final int SAND3_COLOR = 14;
	private static final int SAND4_COLOR = 15;
	private static final int SAND5_COLOR = 16;
	private static final int SAND_SOLID_COLOR = 17;
	private static final int[] SAND_COLORS = {SAND1_COLOR, SAND2_COLOR};
	private static final int[] SAND_PLANT_COLORS = {SAND3_COLOR, SAND4_COLOR, SAND5_COLOR};
	
	private static final int GRASS_CORNER_COLOR = 18;
	private static final int GRASS_CORNER_FLIP_COLOR = 19;
	
	private static final int BORDER_SKY1_COLOR = 20;
	private static final int BORDER_SKY1_FLIP_COLOR = 21;
	private static final int BORDER_SKY2_COLOR = 22;
	private static final int BORDER_SKY2_FLIP_COLOR = 23;
	private static final int[] BORDER_SKY_COLORS = {BORDER_SKY1_COLOR, BORDER_SKY2_COLOR};
	private static final int[] BORDER_SKY_FLIP_COLORS = {BORDER_SKY1_FLIP_COLOR, BORDER_SKY2_FLIP_COLOR};
	
	private static final int BORDER_WATER1_COLOR = 24;
	private static final int BORDER_WATER1_FLIP_COLOR = 25;
	private static final int BORDER_WATER2_COLOR = 26;
	private static final int BORDER_WATER2_FLIP_COLOR = 27;
	private static final int[] BORDER_WATER_COLORS = {BORDER_WATER1_COLOR, BORDER_WATER2_COLOR};
	private static final int[] BORDER_WATER_FLIP_COLORS = {BORDER_WATER1_FLIP_COLOR, BORDER_WATER2_FLIP_COLOR};
	
	private static final int BORDER_WAVE_COLOR = 28;
	private static final int BORDER_WAVE_FLIP_COLOR = 29;
	
	private static final int BORDER_SOLID_COLOR = 30;
	
	// ==================== Instance Data ====================
	
	private int width;
	private int height;
	private int[] tiles;
	private int animationTimer, animationState;

	private static final Random RANDOM = new Random();
	
	/** The default level */
	public static final Level DEFAULT_LEVEL = new Level(DEFAULT_LEVEL_WIDTH, DEFAULT_LEVEL_HEIGHT);
	
	// ==================== Constructors ====================
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[this.width * this.height];
//		generateRandomLevel();
		generateTestLevel();
	}
	
	public Level(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ==================== Public Methods ====================
	
	public void generateRandomLevel() {
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = RANDOM.nextInt(31);			
	}
	
	public void generateTestLevel() {
		generateSky();
		generateWaves();
		generateWater();
		generateSand();	
		generateBorder();
	}
	
	public void animate() {
		animationTimer++;
		
		if (animationTimer % 15 == 0) {
			animationState++;
			
			for (int i = width * WAVE_LAYER + 1; i < width * (WAVE_LAYER + 1) - 1; i++)
				tiles[i] = WAVE_COLORS[animationState % WAVE_COLORS.length];
		}
		
		if (animationTimer > 1000) animationTimer = 0;
		if (animationState > 1000) animationState = 0;
	}
	
	// ==================== Private Methods ====================
	
	private void generateSky() {
		for (int i = 0; i < width * WAVE_LAYER; i++) {
			int r = RANDOM.nextInt(10);
			tiles[i] = (r == 0 ? CLOUD_COLOR : SKY_COLOR);
		}	
	}
	
	private void generateWaves() {
		for (int i = width * WAVE_LAYER; i < width * (WAVE_LAYER + 1); i++)
			tiles[i] = WAVE1_COLOR;
	}
	
	private void generateWater() {
		for (int i = width * (WAVE_LAYER + 1); i < width * (height - 1); i++) {
			int r = RANDOM.nextInt(10);
			if (r == 0) {
				r = RANDOM.nextInt(WATER_BUBBLE_COLORS.length);
				tiles[i] = WATER_BUBBLE_COLORS[r];
			} else {
				r = RANDOM.nextInt(WATER_COLORS.length);
				tiles[i] = WATER_COLORS[r];
			}
		}
	}
	
	private void generateSand() {
		for (int i = width * (height - 1); i < width * height; i++) {
			int r = RANDOM.nextInt(10);
			if (r == 0) {
				r = RANDOM.nextInt(SAND_PLANT_COLORS.length);
				tiles[i] = SAND_PLANT_COLORS[r];
			} else {
				r = RANDOM.nextInt(SAND_COLORS.length);
				tiles[i] = SAND_COLORS[r];
			}
		}
	}
	
	private void generateBorder() {
		tiles[0] = GRASS_CORNER_COLOR;
		tiles[width - 1] = GRASS_CORNER_FLIP_COLOR;
		
		for (int i = width; i < WAVE_LAYER * width; i += width) {
			int r = RANDOM.nextInt(BORDER_SKY_COLORS.length);
			tiles[i] = BORDER_SKY_COLORS[r];
		}
		
		for (int i = width * 2 - 1; i < WAVE_LAYER * width + width - 1; i += width) {
			int r = RANDOM.nextInt(BORDER_SKY_FLIP_COLORS.length);
			tiles[i] = BORDER_SKY_FLIP_COLORS[r];
		}
		
		tiles[WAVE_LAYER * width] = BORDER_WAVE_COLOR;
		tiles[WAVE_LAYER * width + width - 1] = BORDER_WAVE_FLIP_COLOR;
		
		for (int i = (WAVE_LAYER + 1) * width; i < width * (height - 1); i += width) {
			int r = RANDOM.nextInt(BORDER_WATER_COLORS.length);
			tiles[i] = BORDER_WATER_COLORS[r];
		}
		
		for (int i = (WAVE_LAYER + 1) * width + width - 1; i < width * (height - 1) + width - 1; i += width) {
			int r = RANDOM.nextInt(BORDER_WATER_FLIP_COLORS.length);
			tiles[i] = BORDER_WATER_FLIP_COLORS[r];
		}
		
		tiles[width * height - width] = BORDER_SOLID_COLOR;
		tiles[width * height - 1] = BORDER_SOLID_COLOR;
	}
	
	// ==================== Getters and Setters ====================
	
	public Tile getTile(int x, int y) {
		
		if (y < 0) return Tile.SKY;
		
		if (y == 0 && (x < 0 || x >= width))
			return Tile.GRASS;
		
		if (x < 0 || x >= width)
			return Tile.BORDER_SOLID;
		
		if (y >= width) {
			if (x == 0 || x == width - 1)
				return Tile.BORDER_SOLID;
			else
				return Tile.SAND_SOLID;
		}
		
		int tile = tiles[y * width + x];
		
		if (tile == SKY_COLOR) return Tile.SKY;
		if (tile == CLOUD_COLOR) return Tile.CLOUD;
		if (tile == WAVE1_COLOR) return Tile.WAVE1;
		if (tile == WAVE2_COLOR) return Tile.WAVE2;
		if (tile == WAVE3_COLOR) return Tile.WAVE3;
		if (tile == WAVE4_COLOR) return Tile.WAVE4;
		if (tile == WATER1_COLOR) return Tile.WATER1;
		if (tile == WATER2_COLOR) return Tile.WATER2;
		if (tile == WATER3_COLOR) return Tile.WATER3;
		if (tile == WATER4_COLOR) return Tile.WATER4;
		if (tile == WATER5_COLOR) return Tile.WATER5;
		if (tile == WATER6_COLOR) return Tile.WATER6;
		if (tile == SAND1_COLOR) return Tile.SAND1;
		if (tile == SAND2_COLOR) return Tile.SAND2;
		if (tile == SAND3_COLOR) return Tile.SAND3;
		if (tile == SAND4_COLOR) return Tile.SAND4;
		if (tile == SAND5_COLOR) return Tile.SAND5;
		if (tile == SAND_SOLID_COLOR) return Tile.SAND_SOLID;
		if (tile == GRASS_CORNER_COLOR) return Tile.GRASS_CORNER;
		if (tile == GRASS_CORNER_FLIP_COLOR) return Tile.GRASS_CORNER_FLIP;
		if (tile == BORDER_SKY1_COLOR) return Tile.BORDER_SKY1;
		if (tile == BORDER_SKY1_FLIP_COLOR) return Tile.BORDER_SKY1_FLIP;
		if (tile == BORDER_SKY2_COLOR) return Tile.BORDER_SKY2;
		if (tile == BORDER_SKY2_FLIP_COLOR) return Tile.BORDER_SKY2_FLIP;
		if (tile == BORDER_WATER1_COLOR) return Tile.BORDER_WATER1;
		if (tile == BORDER_WATER1_FLIP_COLOR) return Tile.BORDER_WATER1_FLIP;
		if (tile == BORDER_WATER2_COLOR) return Tile.BORDER_WATER2;
		if (tile == BORDER_WATER2_FLIP_COLOR) return Tile.BORDER_WATER2_FLIP;
		if (tile == BORDER_WAVE_COLOR) return Tile.BORDER_WAVE;
		if (tile == BORDER_WAVE_FLIP_COLOR) return Tile.BORDER_WAVE_FLIP;
		if (tile == BORDER_SOLID_COLOR) return Tile.BORDER_SOLID;
		
		return Tile.VOID;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getWidthPixels() { return width << Tile.getTILE_SIZE_BIT(); }
	public int getHeightPixels() { return height << Tile.getTILE_SIZE_BIT(); }
	public static int getTOTAL_FISH() { return TOTAL_FISH; }
	public static int getTOTAL_FISHx2() { return TOTAL_FISHx2; }
	public static int getTOTAL_FISHx3() { return TOTAL_FISHx3; }
	public static int getTOTAL_FISHx4() { return TOTAL_FISHx4; }
	public static int getTOTAL_JELLYFISH() { return TOTAL_JELLYFISH; }
	public static int getTOTAL_JELLYFISHx2() { return TOTAL_JELLYFISHx2; }
	public static int getTOTAL_JELLYFISHx3() { return TOTAL_JELLYFISHx3; }
	public static int getTOTAL_PUFFERFISH() { return TOTAL_PUFFERFISH; }
	public static int getTOTAL_PUFFERFISHx2() { return TOTAL_PUFFERFISHx2; }
	public static int getTOTAL_PUFFERFISHx3() { return TOTAL_PUFFERFISHx3; }
	
	
	
}
