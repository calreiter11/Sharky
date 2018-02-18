package sharky.entity;

import java.awt.Rectangle;
import java.util.Random;

import sharky.graphics.Screen;
import sharky.graphics.Sprite;
import sharky.graphics.Tile;
import sharky.level.Level;

/**
 * @author Caleb Reiter
 */
public abstract class Entity {

	// ==================== Instance Data ====================
	
	/** The speed at which to cycle through the animation (higher value indicates longer time) */
	protected static final int ANIMATION_SPEED = 8;
	
	/** The falling acceleration when the entity is outside of a swimmable tile */
	protected static final double GRAVITY = 0.08;
	
	/** The x position of the entity */
	protected int xPos;
	
	/** The y position of the entity */
	protected int yPos;
	
	/** The maximum speed of the entity */
	protected final double MAX_SPEED;
	
	/** The maximum speed of the entity in the air */
	protected final double MAX_FALLING_SPEED;
	
	/** The acceleration of the entity */
	protected final double ACCELERATION;
	
	/** The current speed of the entity in the x direction */
	protected double xSpeed;
	
	/** The current speed of the entity in the y direction */
	protected double ySpeed;

	/** The sprite of the entity */
	protected Sprite sprite;
	
	/** A boolean to track the direction the player is facing */
	protected boolean flip;
	
	protected double startLayerPercent;
	protected double endLayerPercent;
	
	protected Sprite[] animationSprites;
	protected int animationState;
	protected int animationTimer;
	
	protected final boolean DEADLY;
	
	protected Level level;
	
	protected static final Random RANDOM = new Random();

	// ==================== Constructor ====================
	
	public Entity(double startLayerPercent, double endLayerPercent, boolean deadly, Sprite sprite, double maxSpeed, double acceleration, Level level) {
		this.startLayerPercent = startLayerPercent;
		this.endLayerPercent = endLayerPercent;
		DEADLY = deadly;
		this.sprite = sprite;
		MAX_SPEED = maxSpeed;
		ACCELERATION = acceleration;
		this.level = level;
		xSpeed = 0;
		ySpeed = 0;		
		
		MAX_FALLING_SPEED = 5;
		
		setRandomPosition();
	}
	
	// ==================== Abstract Methods ====================
	
	/** Moves the entity */
	public abstract void move();
	
	
	// ==================== Public Methods ====================
	
	public void setRandomPosition() {
		xPos = RANDOM.nextInt(level.getWidthPixels() - 6 * Tile.getTILE_SIZE()) + 3 * Tile.getTILE_SIZE();
		yPos = RANDOM.nextInt((int) (level.getHeightPixels() * (endLayerPercent - startLayerPercent)))
				+ (int) (level.getHeightPixels() * startLayerPercent);
		
		if (xPos < 0) xPos = 0;
		if (xPos > level.getWidthPixels() - sprite.getWIDTH()) xPos = level.getWidthPixels() - sprite.getWIDTH();
		if (yPos < 0) yPos = 0;
		if (yPos > level.getHeightPixels() - sprite.getHEIGHT() - Tile.getTILE_SIZE()) yPos = level.getHeightPixels() - sprite.getHEIGHT() - Tile.getTILE_SIZE();
	}
		
	public Rectangle getBounds() {
		int leftBound = getLeftBound();
		int topBound = getTopBound();
		int width = getRightBound() - leftBound;
		int height = getBottomBound() - topBound;
		
		return new Rectangle(leftBound, topBound, width, height);
	}
	
	public void animate() {
		animationTimer++;
		
		if (animationTimer % ANIMATION_SPEED == 0) {
			animationState++;
			sprite = animationSprites[animationState % animationSprites.length];
		}
		
		if (animationTimer > 1000) animationTimer = 0;
		if (animationState > 1000) animationState = 0;
	}
	
	// ==================== Private Methods ====================
	
	private int getLeftBound() {
		for (int x = 0; x < sprite.getWIDTH(); x++)
			for (int y = 0; y < sprite.getHEIGHT(); y++)
				if (sprite.getPixel(x, y) != 0xFFFF00FF)
					return xPos + x;
		
		return xPos;
	}
	
	private int getRightBound() {
		for (int x = sprite.getWIDTH() - 1; x >= 0; x--)
			for (int y = sprite.getHEIGHT() - 1; y >= 0; y--)
				if (sprite.getPixel(x, y) != 0xFFFF00FF)
					return xPos + x;
		
		return xPos + sprite.getWIDTH();
	}
	
	private int getTopBound() {
		for (int y = 0; y < sprite.getHEIGHT(); y++)
			for (int x = 0; x < sprite.getWIDTH(); x++)
				if (sprite.getPixel(x, y) != 0xFFFF00FF)
					return yPos + y;
		
		return yPos;
	}
	
	private int getBottomBound() {
		for (int y = sprite.getHEIGHT() - 1; y >= 0; y--)
			for (int x = sprite.getWIDTH() - 1; x >= 0; x--)
				if (sprite.getPixel(x, y) != 0xFFFF00FF)
					return yPos + y;
		
		return yPos + sprite.getHEIGHT();
	}
	
	// ==================== Static Methods ====================
	
	public static boolean collision(Entity entity1, Entity entity2) {	
		Rectangle entity1Rectangle = entity1.getBounds();
		Rectangle entity2Rectangle = entity2.getBounds();
		
		if (entity1Rectangle.intersects(entity2Rectangle)) {
			Rectangle collisionRectangle = entity2Rectangle.intersection(entity1Rectangle);
			
			for (int y = collisionRectangle.y; y < collisionRectangle.y + collisionRectangle.height; y++) {
				for (int x = collisionRectangle.x; x < collisionRectangle.x + collisionRectangle.width; x++) {
					if (entity1.getSprite().getPixel(x - entity1.getXPos(), y - entity1.getYPos()) != 0xFFFF00FF && 
						entity2.getSprite().getPixel(x - entity2.getXPos(), y - entity2.getYPos()) != 0xFFFF00FF) {
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static Entity getLargerEntity(Entity entity1, Entity entity2) {
		Rectangle entity1Rectangle = entity1.getBounds();
		Rectangle entity2Rectangle = entity2.getBounds();
		
		return (entity1Rectangle.getHeight() > entity2Rectangle.getHeight() &&
				entity1Rectangle.getWidth() > entity2Rectangle.getWidth() ?
						entity1 : entity2);
	}
	
	public static boolean onScreen(Entity entity, Screen screen) {
		Rectangle entityRectangle = entity.getBounds();
		Rectangle screenRectangle = new Rectangle(screen.getXOffset(), screen.getYOffset(), screen.getWIDTH(), screen.getHEIGHT());
		return entityRectangle.intersects(screenRectangle);
	}
	
	public static boolean inRange(Entity entity1, Entity entity2, int range) {
		Rectangle entity1Rectangle = entity1.getBounds();
		Rectangle entity2Rectangle = new Rectangle(entity2.getXPos() - range, entity2.getYPos() - range, 2 * range, 2 * range);
		
		if (entity1Rectangle.intersects(entity2Rectangle))
			return true;
		
		return false;
	}
	
	// ==================== Getters and Setters ====================
	
	public int getXPos() { return xPos; }
	public int getYPos() { return yPos; }

	public Sprite getSprite() { return sprite; }
	public void setSprite(Sprite sprite) { this.sprite = sprite; }
	
	public boolean isDeadly() { return DEADLY; }
	
	public boolean isFlipped() { return flip; }
	
}
