package sharky.entity;

import sharky.graphics.Sprite;
import sharky.graphics.Tile;
import sharky.input.Keyboard;
import sharky.level.Level;

/**
 * @author Caleb Reiter
 */
public class Player extends Entity {

	// ==================== Instance Data ====================
	
	/** A reference to the keyboard input */
	private final Keyboard KEYBOARD; 
	
	/** The player's starting y position */
	private static final double START_LAYER_PERCENT = 0;
	
	private static final double END_LAYER_PERCENT = 0.01;
	
	/** The player's maximum x speed */
	private static final int MAX_SPEED = 4;
	
	/** The player's acceleration */
	private static final double ACCELERATION = 0.3;
	
	/** The player's deceleration */
	private static final double DECELERATION = 0.1;
	
	public boolean godMode;
	
	private boolean dead;
	
	private int fishEaten;
	private double growthScale;
	private int score;
		
	// ==================== Constructor ====================	
	
	public Player(Keyboard keyboard, Level level, boolean godMode) {
		super(START_LAYER_PERCENT, END_LAYER_PERCENT, false, Sprite.SHARK1, MAX_SPEED, ACCELERATION, level);		
		
		this.KEYBOARD = keyboard;
		this.godMode = godMode;
		dead = false;
		fishEaten = 0;
		growthScale = 1;
		score = 0;
		
		animationSprites = new Sprite[4];
		animationSprites[0] = Sprite.SHARK1;
		animationSprites[1] = Sprite.SHARK2;
		animationSprites[2] = Sprite.SHARK1;
		animationSprites[3] = Sprite.SHARK3;
	}
	
	// ==================== Method Implementation ====================
	
	/** Moves the player */
	@Override
	public void move() {
				
		boolean up = KEYBOARD.up();
		boolean down = KEYBOARD.down();
		boolean left = KEYBOARD.left();
		boolean right = KEYBOARD.right();
		boolean moving = up || down || left || right;
		
		Tile currentTile = level.getTile((xPos + sprite.getWIDTH() / 2) >> Tile.getTILE_SIZE_BIT(), (yPos + sprite.getHEIGHT() / 2) >> Tile.getTILE_SIZE_BIT());
		
		flip = (left || flip) && !right;
		
		godMode = KEYBOARD.space();
		
		if (currentTile.isSwimmable() || godMode) {	
			
			if (moving)
				animate();
			
			if (up) ySpeed -= ACCELERATION;
			if (down) ySpeed += ACCELERATION;
			if (left) xSpeed -= ACCELERATION;
			if (right) xSpeed += ACCELERATION;

			if (!up && !down)
				if (ySpeed <= -DECELERATION) ySpeed += DECELERATION;
				else if (ySpeed >= DECELERATION) ySpeed -= DECELERATION;
				else ySpeed = 0;
			
			if (!left && !right)
				if (xSpeed <= -DECELERATION) xSpeed += DECELERATION;
				else if (xSpeed >= DECELERATION) xSpeed -= DECELERATION;
				else xSpeed = 0;

			if (!godMode) {
				if (ySpeed < -MAX_SPEED) ySpeed = -MAX_SPEED;
				if (ySpeed > MAX_SPEED) ySpeed = MAX_SPEED;
			}
			
		} else {
			ySpeed += GRAVITY;
			
			if (ySpeed > MAX_FALLING_SPEED) ySpeed = MAX_FALLING_SPEED;
		}
		
		if (!godMode) {
			if (xSpeed < -MAX_SPEED) xSpeed = -MAX_SPEED;
			if (xSpeed > MAX_SPEED) xSpeed = MAX_SPEED;
		}
		
		if (!level.getTile((xPos + (int) xSpeed + (xSpeed > 0 ? sprite.getWIDTH() : 0)) >> Tile.getTILE_SIZE_BIT(), yPos >> Tile.getTILE_SIZE_BIT()).isSolid() || godMode)
			xPos += (int) xSpeed;
		
		if (!level.getTile(xPos >> Tile.getTILE_SIZE_BIT(), (yPos + (int) ySpeed + (ySpeed > 0 ? sprite.getHEIGHT() : 0)) >> Tile.getTILE_SIZE_BIT()).isSolid() || godMode)
			yPos += (int) ySpeed;
				
	}
	
	// ==================== Public Methods ====================
	
	public void reset() {
		dead = false;
		growthScale = 1;
		setRandomPosition();
		xSpeed = 0;
		ySpeed = 0;
		score = 0;
		fishEaten = 0;
		
		sprite = Sprite.SHARK1;
		animationSprites[0] = Sprite.SHARK1;
		animationSprites[1] = Sprite.SHARK2;
		animationSprites[2] = Sprite.SHARK1;
		animationSprites[3] = Sprite.SHARK3;

	}
	
	public void grow() {
		
		growthScale = 1 + .01 * fishEaten;
		
		Sprite sprite1 = Sprite.SHARK1;
		Sprite sprite2 = Sprite.SHARK2;
		Sprite sprite3 = Sprite.SHARK3;
		
		animationSprites[0] = Sprite.scaleSprite(sprite1, growthScale);
		animationSprites[1] = Sprite.scaleSprite(sprite2, growthScale);
		animationSprites[2] = Sprite.scaleSprite(sprite1, growthScale);
		animationSprites[3] = Sprite.scaleSprite(sprite3, growthScale);
		
	}
	
	public void eat() {
		fishEaten++;
		score += ((double) yPos / (double) level.getHeightPixels()) * 100;
	}
		
	// ==================== Getters/Setters ====================
	
	public boolean isDead() { return dead; }
	public void setDead(boolean dead) { this.dead = dead; } 
	public double getGrowthScale() { return growthScale; }
	public int getScore() { return score; }
	
}
