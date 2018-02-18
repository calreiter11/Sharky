package sharky.game;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import sharky.audio.Audio;
import sharky.entity.Aquatic;
import sharky.entity.Entity;
import sharky.entity.Player;
import sharky.graphics.Screen;
import sharky.graphics.Sprite;
import sharky.input.Keyboard;
import sharky.level.Level;

public class GameState extends State {
	
	// ==================== Instance Data ====================
	
	/** The entities in the world */
	private ArrayList<Entity> entities;
	
	private final Player PLAYER;
	private Level level;
	
	private static final Random RANDOM = new Random();
	
	// ==================== Constructor ====================
	
	public GameState(Keyboard keyboard, Screen screen, Player player, Level level) {
		super(keyboard, screen);
		PLAYER = player;
		this.level = level;
		
		entities = new ArrayList<Entity>();
		fillEntities();
	}
	
	// ==================== Private Methods ====================
	
	private void fillEntities() {
				
		for (int i = 0; i < Level.getTOTAL_FISH(); i++)
			entities.add(Aquatic.randomFish(level, 1));

		for (int i = 0; i < Level.getTOTAL_FISHx2(); i++)
			entities.add(Aquatic.randomFish(level, 2));

		for (int i = 0; i < Level.getTOTAL_FISHx3(); i++)
			entities.add(Aquatic.randomFish(level, 3));
		
		for (int i = 0; i < Level.getTOTAL_FISHx4(); i++)
			entities.add(Aquatic.randomFish(level, 4));
		
		for (int i = 0; i < Level.getTOTAL_JELLYFISH(); i++)
			entities.add(Aquatic.randomJellyfish(level, 1));
		
		for (int i = 0; i < Level.getTOTAL_JELLYFISHx2(); i++)
			entities.add(Aquatic.randomJellyfish(level, 2));
		
		for (int i = 0; i < Level.getTOTAL_JELLYFISHx3(); i++)
			entities.add(Aquatic.randomJellyfish(level, 3));
		
		for (int i = 0; i < Level.getTOTAL_PUFFERFISH(); i++)
			entities.add(Aquatic.randomPufferfish(level, 1));
		
		for (int i = 0; i < Level.getTOTAL_PUFFERFISHx2(); i++)
			entities.add(Aquatic.randomPufferfish(level, 2));
		
		for (int i = 0; i < Level.getTOTAL_PUFFERFISHx3(); i++)
			entities.add(Aquatic.randomPufferfish(level, 3));
	}
	
	// ==================== Method Implementations ====================

	@Override
	public void init() {
		entities = new ArrayList<Entity>();
		fillEntities();
		
		PLAYER.reset();
	}
	
	@Override
	public void update() {
		
		if (!KEYBOARD.paused()) {
			PLAYER.move();
			level.animate();
			
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i);
				entity.move();
				if (!PLAYER.godMode && Entity.onScreen(entity, SCREEN)) {
						if (Entity.inRange(entity, PLAYER, SCREEN.getHEIGHT() / 3)) {
							if (entity.getSprite() == Sprite.PUFFERFISH1 || entity.getSprite() == Sprite.PUFFERFISH2)
								entity.setSprite(Sprite.PUFFERFISH3);
							
							if (entity.getSprite() == Sprite.PUFFERFISH1x2 || entity.getSprite() == Sprite.PUFFERFISH2x2)
								entity.setSprite(Sprite.PUFFERFISH3x2);
							
							if (entity.getSprite() == Sprite.PUFFERFISH1x3 || entity.getSprite() == Sprite.PUFFERFISH2x3)
								entity.setSprite(Sprite.PUFFERFISH3x3);
							
							
							if (Entity.collision(PLAYER, entity)) {
								if (Entity.getLargerEntity(PLAYER, entity) == PLAYER && !entity.isDeadly()) {
									entities.remove(entity);
									entities.add(Aquatic.randomAquatic(level, RANDOM.nextInt(3) + 1));
									PLAYER.eat();
									PLAYER.grow();
									Audio.GULP.play();
									continue;
								} else {
									
									if (entity.getSprite() == Sprite.JELLYFISH || 
										entity.getSprite() == Sprite.JELLYFISHx2 ||
										entity.getSprite() == Sprite.JELLYFISHx3)
											PLAYER.setSprite(Sprite.scaleSprite(Sprite.SHARK_ELECTRIC, PLAYER.getGrowthScale()));
									
									PLAYER.setDead(true);
								}
							}
							
						} else {
							int r = RANDOM.nextInt(2);
							
							if (entity.getSprite() == Sprite.PUFFERFISH3)
								entity.setSprite(r == 0 ? Sprite.PUFFERFISH1 : Sprite.PUFFERFISH2);
							
							if (entity.getSprite() == Sprite.PUFFERFISH3x2)
								entity.setSprite(r == 0 ? Sprite.PUFFERFISH1x2 : Sprite.PUFFERFISH2x2);

							if (entity.getSprite() == Sprite.PUFFERFISH3x3)
								entity.setSprite(r == 0 ? Sprite.PUFFERFISH1x3 : Sprite.PUFFERFISH2x3);

						}
				}
			}
		}
	}
	
	@Override
	public void render() {		
		int xOffset = PLAYER.getXPos() - SCREEN.getWIDTH() / 2 + PLAYER.getSprite().getWIDTH() / 2;
		int yOffset = PLAYER.getYPos() - SCREEN.getHEIGHT() / 2 + PLAYER.getSprite().getHEIGHT() / 2;
		SCREEN.setXOffset(xOffset);
		SCREEN.setYOffset(yOffset);
	
		SCREEN.renderLevel(level);
		
		double darkenScale = ((SCREEN.getYOffset() + (SCREEN.getHEIGHT() / 2)) / (level.getHeightPixels() * 1.10)) * 3 / 4;
		
		for (Entity entity : entities)
			SCREEN.renderEntity(entity, darkenScale);
		
		SCREEN.renderEntity(PLAYER, darkenScale);
		
		SCREEN.drawString(new Font("DS-Digital", 0, 25), Color.BLACK, "Score: " + PLAYER.getScore(), 25, 25);
	}	
}
