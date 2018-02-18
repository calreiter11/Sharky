package sharky.entity;

import sharky.graphics.Sprite;
import sharky.graphics.Tile;
import sharky.level.Level;

public class Aquatic extends Entity {

	// ==================== Constructor ====================
	
	public Aquatic(double startLayerPercent, double endLayerPercent, boolean deadly, 
					Sprite sprite, double maxSpeed, double acceleration, Level level) {
		super(startLayerPercent, endLayerPercent, deadly, sprite, maxSpeed, acceleration, level);
	}

	// ==================== Method Implementation ====================
	
	/** Moves the entity */
	@Override
	public void move() {
		
		Tile currentTile = level.getTile((xPos + sprite.getWIDTH() / 2) >> Tile.getTILE_SIZE_BIT(), (yPos + sprite.getHEIGHT() / 2) >> Tile.getTILE_SIZE_BIT());
		if (!currentTile.isSwimmable())
			ySpeed += GRAVITY;
		else if (ySpeed > 0)
			ySpeed -= ACCELERATION;
		
		int r = RANDOM.nextInt(100);
		
		if (xPos < 2 * Tile.getTILE_SIZE())
			flip = false;
		else if (xPos > level.getWidth() << Tile.getTILE_SIZE_BIT() - 3 * Tile.getTILE_SIZE())
			flip = true;
		else if (r == 0)
			flip = !flip;
		
		
		if (flip) xSpeed -= ACCELERATION;
		else xSpeed += ACCELERATION;
		
		if (xSpeed < -MAX_SPEED) xSpeed = -MAX_SPEED;
		if (xSpeed > MAX_SPEED) xSpeed = MAX_SPEED;
		if (ySpeed < 0) ySpeed = 0;
		if (ySpeed > MAX_FALLING_SPEED) ySpeed = MAX_FALLING_SPEED;
		
		if (!level.getTile((xPos + (int) xSpeed + (xSpeed > 0 ? sprite.getWIDTH() : 0)) >> Tile.getTILE_SIZE_BIT(), yPos >> Tile.getTILE_SIZE_BIT()).isSolid())
			xPos += (int) xSpeed;
		
		if (!level.getTile(xPos >> Tile.getTILE_SIZE_BIT(), (yPos + (int) ySpeed + (ySpeed > 0 ? sprite.getHEIGHT() : 0)) >> Tile.getTILE_SIZE_BIT()).isSolid())
			yPos += (int) ySpeed;
		
		if (xPos < 0) xPos = 0;
		if (xPos > level.getWidth() * Tile.getTILE_SIZE() - sprite.getWIDTH())
			xPos = level.getWidth() * Tile.getTILE_SIZE() - sprite.getWIDTH();
	}

	
	// ==================== Static Methods ====================
	
	public static Aquatic randomAquatic(Level level, int scale) {
		int r = RANDOM.nextInt(3);
		if (r == 0) return randomFish(level, scale);
		if (r == 1) return randomPufferfish(level, scale);
		if (r == 2) return randomJellyfish(level, scale);
		
		System.out.println("returning void aquatic");
		return new Aquatic(0, 1, false, Sprite.VOID, 2.0, 0.1, level);
	}
	
	public static Aquatic randomFish(Level level, int scale) {
		int r = RANDOM.nextInt(3);
		Sprite sprite = Sprite.VOID;
		if (r == 0) {
			if (scale == 1) sprite = Sprite.FISH_ORANGE;
			if (scale == 2) sprite = Sprite.FISH_ORANGEx2;
			if (scale == 3) sprite = Sprite.FISH_ORANGEx3;
			if (scale == 4) sprite = Sprite.FISH_ORANGEx4;
		} else if (r == 1) {
			if (scale == 1) sprite = Sprite.FISH_RED;
			if (scale == 2) sprite = Sprite.FISH_REDx2;
			if (scale == 3) sprite = Sprite.FISH_REDx3;
			if (scale == 4) sprite = Sprite.FISH_REDx4;
		} else if (r == 2) {
			if (scale == 1) sprite = Sprite.FISH_YELLOW;			
			if (scale == 2) sprite = Sprite.FISH_YELLOWx2;			
			if (scale == 3) sprite = Sprite.FISH_YELLOWx3;			
			if (scale == 4) sprite = Sprite.FISH_YELLOWx4;			
		}
		
		return new Aquatic(0.15 * scale - .10, 1, false, sprite, 3 / scale, 0.05 * scale, level);
	}
	
	public static Aquatic randomPufferfish(Level level, int scale) {
		int r = RANDOM.nextInt(2);
		Sprite sprite = Sprite.VOID;
		if (r == 0) {
			if (scale == 1) sprite = Sprite.PUFFERFISH1;
			if (scale == 2) sprite = Sprite.PUFFERFISH1x2;
			if (scale == 3) sprite = Sprite.PUFFERFISH1x3;
		} else if (r == 1) {
			if (scale == 1) sprite = Sprite.PUFFERFISH2;
			if (scale == 2) sprite = Sprite.PUFFERFISH2x2;
			if (scale == 3) sprite = Sprite.PUFFERFISH2x3;
		}
		
		return new Aquatic(0.20 * scale, 1, true, sprite, 2 / scale, 0.05 * scale, level);
	}
	
	public static Aquatic randomJellyfish(Level level, int scale) {
		Sprite sprite = Sprite.VOID;
		if (scale == 1) sprite = Sprite.JELLYFISH;
		if (scale == 2) sprite = Sprite.JELLYFISHx2;
		if (scale == 3) sprite = Sprite.JELLYFISHx3;

		return new Aquatic(0.25 * scale, 1, true, sprite, 2 / scale, 0.05 * scale, level);
	}
	
}
