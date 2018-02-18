package sharky.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Caleb Reiter
 */
public class Keyboard implements KeyListener {

	// ==================== Instance Data ====================
	
	/** An array to determine whether each key is pressed */
	private boolean[] keys = new boolean[1000];
	
	// ==================== Method Implementation ====================
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_P && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_SPACE)
			keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_P && e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_SPACE) {
			keys[e.getKeyCode()] = false;
			keys[KeyEvent.VK_ENTER] = false;
		} else if (e.getKeyCode() == KeyEvent.VK_P)
			keys[KeyEvent.VK_P] = !keys[KeyEvent.VK_P];
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			keys[KeyEvent.VK_ENTER] = true;
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			keys[KeyEvent.VK_SPACE] = !keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	// ==================== Methods ====================
	
	public boolean up() {
		return keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
	}

	public boolean down() {
		return keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
	}

	public boolean left() {
		return keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
	}

	public boolean right() {
		return keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	}

	
	public boolean paused() { 
		return keys[KeyEvent.VK_P]; 
	}
	
	public boolean enter() {
		return keys[KeyEvent.VK_ENTER];
	}
	
	public boolean space() {
		return keys[KeyEvent.VK_SPACE];
	}
	
	
	public void clear() {
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
	}
	
}
