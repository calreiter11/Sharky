package sharky.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import sharky.entity.Entity;
import sharky.input.Keyboard;
import sharky.level.Level;

/**
 * @author Caleb Reiter
 */
@SuppressWarnings("serial")
public class Screen extends JFrame {

	// ==================== Instance Data ====================
	
	/** The width of the screen */
	private final int WIDTH;
	
	/** The height of the screen */
	private final int HEIGHT;
	
	/** The multiplier applied to <code>WIDTH</code> and <code>HEIGHT</code> */
	private final int SCALE;
	
	/** The title of the game */
	private final String TITLE;
	
	/** The canvas for the screen */
	private final Canvas CANVAS;
	
	/** The <code>BufferedImage</code> to display */
	private final BufferedImage IMAGE;
	
	/** The pixels on the screen in one dimensional format */
	private int[] pixels;
	
	/** The <code>KeyListener</code> for the screen */
	private final Keyboard KEYBOARD;	
	
	/** The power of 2 that indicates the size of the tiles (i.e. a tile size of 3 indicates 2^3 = 8) */
	private static final int TILE_SIZE = Tile.getTILE_SIZE();
	
	private static final int TILE_SIZE_BIT = Tile.getTILE_SIZE_BIT();

	/** The x distance the screen is offset from its original position */
	private int xOffset;
	
	/** The y distance the screen is offset from its original position */
	private int yOffset;
	
	// ==================== Constructor ====================
 	
	public Screen(int width, int height, int scale, String title, Keyboard keyboard) {
		WIDTH = width;
		HEIGHT = height;
		SCALE = scale;
		TITLE = title;
		CANVAS = new Canvas();
		IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		KEYBOARD = keyboard;
		
		xOffset = 0;
		yOffset = 0;
		
		pixels = ((DataBufferInt)IMAGE.getRaster().getDataBuffer()).getData();

		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);		
		setPreferredSize(size);
		
		setResizable(false);
		setTitle(TITLE);
		add(CANVAS);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);	
		
		CANVAS.createBufferStrategy(3);
		
		CANVAS.addKeyListener(this.KEYBOARD);
		
		CANVAS.requestFocus();
	}
	
	// ==================== Methods ====================
	
	/** Renders the screen */
	public void render() {
		BufferStrategy bs = CANVAS.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();		
		
		g.drawImage(IMAGE, 0, 0, CANVAS.getWidth(), CANVAS.getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public void renderTile(int xPos, int yPos, Tile tile, double darkenScale) {
		
		int xTile = 0, yTile = 0;
		int xScreen = 0, yScreen = 0;
		
		int xScreenStart = xPos - xOffset;
		int yScreenStart = yPos - yOffset;
		
		for (int y = 0; y < tile.getSprite().getHEIGHT(); y++) {
			yTile = y;
			yScreen = yScreenStart + y;
			for (int x = 0; x < tile.getSprite().getWIDTH(); x++) {
				xTile = x;
				xScreen = xScreenStart + x;
				
				if (xScreen < 0 || xScreen >= WIDTH ||
						yScreen < 0 || yScreen >= HEIGHT)
					continue;
				
				int color = tile.getSprite().getPixel(xTile, yTile);
				if (color != 0xFFFF00FF)
					setPixel(xScreen, yScreen, darkenColor(color, darkenScale));
			}
		}
	}
	
	public void renderLevel(Level level) {
		int x0 = xOffset >> TILE_SIZE_BIT;
		int x1 = (xOffset + WIDTH + TILE_SIZE) >> TILE_SIZE_BIT;
		int y0 = yOffset >> TILE_SIZE_BIT;
		int y1 = (yOffset + HEIGHT + TILE_SIZE) >> TILE_SIZE_BIT;
		
		for (int y = y0; y < y1; y++) {
			double darkenScale = (yOffset + (HEIGHT / 2)) / (level.getHeightPixels() * 1.10);
			
			for (int x = x0; x < x1; x++)
				renderTile(x << TILE_SIZE_BIT, y << TILE_SIZE_BIT, level.getTile(x, y), darkenScale);
		}
	}
	
	
	public int darkenColor(int color, double darkenScale) {
//		int alpha = (color >> 24) & 0x000000FF;
		darkenScale = 1 - darkenScale;
		
		if (darkenScale < 0) darkenScale = 0;
		if (darkenScale > 1) darkenScale = 1;
		
		int red = (int) (((color >> 16) & 0x000000FF) * darkenScale);
		int green = (int) (((color >>8 ) & 0x000000FF) * darkenScale);
		int blue = (int) (((color) & 0x000000FF) * darkenScale);
		return (red << 16) + (green << 8) + blue; 
	}
	
	public void renderEntity(Entity entity, double darkenScale) {
		if (!Entity.onScreen(entity, this))
			return;
		
		int xSprite = 0, ySprite = 0;
		int xScreen = 0, yScreen = 0;
		
		int xScreenStart = entity.getXPos() - xOffset;
		int yScreenStart = entity.getYPos() - yOffset;
		
		Sprite entitySprite = entity.getSprite();
		
		for (int y = 0; y < entitySprite.getHEIGHT(); y++) {
			yScreen = yScreenStart + y;
			ySprite = y;
			for (int x = 0; x < entitySprite.getWIDTH(); x++) {
				xScreen = xScreenStart + x;
				xSprite = (entity.isFlipped() ? entitySprite.getWIDTH() - 1 - x : x); 
				
				int color = entitySprite.getPixel(xSprite, ySprite);
				
				if (xScreen < 0 || xScreen >= WIDTH ||
						yScreen < 0 || yScreen >= HEIGHT)
							continue;
				
				if (color != 0xFFFF00FF)
					setPixel(xScreen, yScreen, darkenColor(entitySprite.getPixel(xSprite, ySprite), darkenScale));
			}
		}
	}
	
	/** Clears the screen */
	public void clear() {		
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0xFFFFFF;
	}
	
	public void drawString(Font font, Color color, String string, int x, int y) {
		Graphics g = IMAGE.getGraphics();
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x, y);
		g.dispose();
	}
	
	// ==================== Getters and Setters ====================
	
	public int[] getPixels() { return pixels; }
	public void setPixel(int x, int y, int color) { pixels[y * WIDTH + x] = color; }
	public void setPixel(int i, int color) { pixels[i] = color; }
	
	public int getWIDTH() { return WIDTH; }
	public int getHEIGHT() { return HEIGHT; }
	
	public int getXOffset() { return xOffset; }
	public int getYOffset() { return yOffset; }
	
	public void setXOffset(int xOffset) { this.xOffset = xOffset; }
	public void setYOffset(int yOffset) { this.yOffset = yOffset; }	
}
