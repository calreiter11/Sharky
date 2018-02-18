package sharky.graphics;


public class Tile {

	// ==================== Instance Data ====================
	
	/** The tile size as a power of 2 */
	private static final int TILE_SIZE_BIT = 5;
	
	/** The tile size as an integer */
	private static final int TILE_SIZE = (int) Math.pow(2, TILE_SIZE_BIT);
	
	private Sprite sprite;
	private boolean solid;
	private boolean swimmable;
	
	private String name;
	
	// ==================== Static Objects ====================
	
	public static final Tile SKY = new Tile(Sprite.SKY, false, false, "Sky");
	public static final Tile CLOUD = new Tile(Sprite.CLOUD, false, false, "Cloud");
	
	public static final Tile WAVE1 = new Tile(Sprite.WAVE1, false, true, "Wave1");
	public static final Tile WAVE2 = new Tile(Sprite.WAVE2, false, true, "Wave2");
	public static final Tile WAVE3 = new Tile(Sprite.WAVE3, false, true, "Wave3");
	public static final Tile WAVE4 = new Tile(Sprite.WAVE4, false, true, "Wave4");
	
	public static final Tile WATER1 = new Tile(Sprite.WATER1, false, true, "Water1");
	public static final Tile WATER2 = new Tile(Sprite.WATER2, false, true, "Water2");
	public static final Tile WATER3 = new Tile(Sprite.WATER3, false, true, "Water3");
	public static final Tile WATER4 = new Tile(Sprite.WATER4, false, true, "Water4");
	public static final Tile WATER5 = new Tile(Sprite.WATER5, false, true, "Water5");
	public static final Tile WATER6 = new Tile(Sprite.WATER6, false, true, "Water6");
	
	public static final Tile SAND1 = new Tile(Sprite.SAND1, true, false, "Sand1");
	public static final Tile SAND2 = new Tile(Sprite.SAND2, true, false, "Sand2");
	public static final Tile SAND3 = new Tile(Sprite.SAND3, true, false, "Sand3");
	public static final Tile SAND4 = new Tile(Sprite.SAND4, true, false, "Sand4");
	public static final Tile SAND5 = new Tile(Sprite.SAND5, true, false, "Sand5");
	public static final Tile SAND_SOLID = new Tile(Sprite.SAND_SOLID, true, false, "Sand Solid");
		
	public static final Tile GRASS = new Tile(Sprite.GRASS, true, false, "Grass");
	public static final Tile GRASS_CORNER = new Tile(Sprite.GRASS_CORNER, true, false, "Grass Corner");
	public static final Tile GRASS_CORNER_FLIP = new Tile(Sprite.GRASS_CORNER_FLIP, true, false, "Grass Corner Flip");
	
	public static final Tile BORDER_SKY1 = new Tile(Sprite.BORDER_SKY1, true, false, "Border Sky 1");
	public static final Tile BORDER_SKY1_FLIP = new Tile(Sprite.BORDER_SKY1_FLIP, true, false, "Border Sky 1 Flip");
	public static final Tile BORDER_SKY2 = new Tile(Sprite.BORDER_SKY2, true, false, "Border Sky 2");
	public static final Tile BORDER_SKY2_FLIP = new Tile(Sprite.BORDER_SKY2_FLIP, true, false, "Border Sky 2 Flip");
	public static final Tile BORDER_WATER1 = new Tile(Sprite.BORDER_WATER1, true, false, "Border Water 1");
	public static final Tile BORDER_WATER1_FLIP = new Tile(Sprite.BORDER_WATER1_FLIP, true, false, "Border Water 1 Flip");
	public static final Tile BORDER_WATER2 = new Tile(Sprite.BORDER_WATER2, true, false, "Border Water 2");
	public static final Tile BORDER_WATER2_FLIP = new Tile(Sprite.BORDER_WATER2_FLIP, true, false, "Border Water 2 Flip");
	public static final Tile BORDER_WAVE = new Tile(Sprite.BORDER_WAVE, true, false, "Border Wave");
	public static final Tile BORDER_WAVE_FLIP = new Tile(Sprite.BORDER_WAVE_FLIP, true, false, "Border Wave Flip");
	public static final Tile BORDER_SOLID = new Tile(Sprite.BORDER_SOLID, true, false, "Border Solid");

	public static final Tile VOID = new Tile(Sprite.VOID, false, false, "Void");
	
	// ==================== Constructor ====================
	
	public Tile(Sprite sprite, boolean solid, boolean swimmable, String name) {
		this.sprite = sprite;
		this.solid = solid;
		this.swimmable = swimmable;
		this.name = name;		
	}
	
	// ==================== Getters and Setters ====================
	
	public boolean isSolid() { return solid; }
	public boolean isSwimmable() { return swimmable; }
	public Sprite getSprite() { return sprite; }
	public static int getTILE_SIZE() { return TILE_SIZE; }
	public static int getTILE_SIZE_BIT() { return TILE_SIZE_BIT; }
	public String getName() { return name; }
	
}
