package sharky.game;

import sharky.audio.Audio;
import sharky.entity.Player;
import sharky.graphics.Screen;
import sharky.input.Keyboard;
import sharky.level.Level;

/**
 * @author Caleb Reiter
 * @version 1.0
 */
public class Sharky implements Runnable {

	// ==================== Game Details ====================
			
		/** The width of the game in pixels */
		private static final int WIDTH = 450;
		
		/** The height of the game in pixels */
		private static final int HEIGHT = WIDTH * 9 / 16;
			
		/** The multiplier applied to height and width */
		private static final int SCALE = 2;
		
		/** The title of the game */
		private static final String TITLE = "Sharky";
		
		/** The optimal updates per second that the game will run at */
		private static final int OPTIMAL_UPS = 60;
		
	// ==================== Game Component Variables ====================
		
		/** The keyboard that controls the game */
		private static final Keyboard KEYBOARD = new Keyboard();
		
		/** The screen of the game*/
		private static final Screen SCREEN = new Screen(WIDTH, HEIGHT, SCALE, TITLE, KEYBOARD);		
		
	// ==================== Entity Variables ====================
		
		/** Whether the player is in God Mode */
		private static final boolean GOD_MODE = true;
		
		/** The player */
		private static final Player PLAYER = new Player(KEYBOARD, Level.DEFAULT_LEVEL, GOD_MODE);
				
	// ==================== Game State Variables ====================
		
		/** Whether the game is running */
		private boolean running = false;
		
		/** The current level */
		private Level level;
		
		
		private int currentGameState;
		private static final State INTRO = new IntroState(KEYBOARD, SCREEN, "/textures/Intro.png");
		private static final State HELP = new HelpState(KEYBOARD, SCREEN, "/textures/Help.png");
		private static final State GAME = new GameState(KEYBOARD , SCREEN, PLAYER, Level.DEFAULT_LEVEL);		
		private static final State PAUSE = new PauseState(KEYBOARD, SCREEN, "/textures/Pause.png");	
		private static final State GAME_OVER = new GameOverState(KEYBOARD, SCREEN, "/textures/GameOver.png");
		private static final State[] GAME_STATES = {INTRO, HELP, GAME, PAUSE, GAME_OVER};
		
		private final int INTRO_NUM = 0;
		private final int HELP_NUM = 1;
		private final int GAME_NUM = 2;
		private final int PAUSE_NUM = 3;
		private final int GAME_OVER_NUM = 4;
		
		
			
	// ==================== Constructor ====================
		
		public Sharky() {
			currentGameState = INTRO_NUM;
			GAME.init();
			GAME.render();
			INTRO.init();
			level = Level.DEFAULT_LEVEL;
			Audio.JAWS.play();
		}
		
	// ==================== Methods to Run the Game ====================
		
		/** Starts the game */
		public void start() {
			running = true;
			run();
		}
		
		/** Stops the game */
		public void stop() {
			running = false;
		}
		
		/** Runs the game until user exits */
		public void run() {
			
			int frames = 0;
			int updates = 0;
			
			double optimalTime = 1000000000 / OPTIMAL_UPS;
			
			long lastTime = System.nanoTime();
			long currentTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			
			double delta = 0;
			
			while(running) {
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / optimalTime;
				lastTime = currentTime;		
				
				while (delta >= 1) {
						update();
						updates++;
						delta--;
				}
					
				render();
				frames++;
					
				if (System.currentTimeMillis() - timer > 1000) {
					SCREEN.setTitle(TITLE + "   |   " + frames + " FPS   |   " + updates + " UPS");
					updates = 0;
					frames = 0;
					timer += 1000;
				}
			}
			stop();
		}
		
		/** Updates the game */
		public void update() {
		
			if (currentGameState == HELP_NUM && KEYBOARD.enter()) {
				currentGameState = GAME_NUM;
			}
			
			if (currentGameState == INTRO_NUM && KEYBOARD.enter()) {
				currentGameState = HELP_NUM;
				GAME.render();
				HELP.init();
				KEYBOARD.clear();
			}
			
			if (currentGameState == GAME_OVER_NUM && KEYBOARD.enter()) {
				currentGameState = GAME_NUM;
				GAME.init();
			}
			
			if (PLAYER.isDead()) {
				if (currentGameState != GAME_OVER_NUM)
					GAME_OVER.init();
				currentGameState = GAME_OVER_NUM;
			} else if (KEYBOARD.paused()) {
				if (currentGameState != PAUSE_NUM)
					PAUSE.init();
				currentGameState = PAUSE_NUM;
			} else if (currentGameState != INTRO_NUM && currentGameState != HELP_NUM){ 
				currentGameState = GAME_NUM;
			}
			
			GAME_STATES[currentGameState].update();
			
		}
		
		/** Renders the game */
		public void render() {
			SCREEN.clear(); //1st operation
			GAME_STATES[currentGameState].render();
			SCREEN.render(); //last operation
			
		}
		
	// ==================== Main Method ====================
	
		/** Runs the program */
		public static void main(String[] args) {
			Sharky game = new Sharky();
			game.start();
		}
	
	// ==================== Getters/Setters ====================
		
	public static int getWIDTH() { return WIDTH; }
	public static int getHEIGHT() { return HEIGHT; }
	public static int getSCALE() { return SCALE; }
	public static String getTITLE() { return TITLE; }
	public static int getOPTIMAL_UPS() { return OPTIMAL_UPS; }
	public static Screen getSCREEN() { return SCREEN; }
	public static Keyboard getKEYBOARD() { return KEYBOARD; }
	public Level getLevel() { return level; }
		
		
	
}
