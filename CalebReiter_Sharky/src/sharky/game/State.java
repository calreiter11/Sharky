package sharky.game;

import sharky.graphics.Screen;
import sharky.input.Keyboard;

public abstract class State {
	
	
	// ==================== Instance Data ====================
	
	protected final Keyboard KEYBOARD;
	protected final Screen SCREEN;
	
	// ==================== Constructor ====================
	
	public State(Keyboard keyboard, Screen screen) {
		KEYBOARD = keyboard;
		SCREEN = screen;
	}
	
	// ==================== Abstract Methods ====================
	
	public abstract void init();
	public abstract void update();
	public abstract void render();
	
}
