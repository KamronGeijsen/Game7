package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private VolatileImage volatileImage;
	private Graphics2D graphics2d;
	Screen screen;
	private long updateTick = 0;
	private long frameTick = 0;
	

	public Window() {
		screen = new GameScreen(this);
		
		updateTick = System.currentTimeMillis();
		
		addKeyListener(screen);
		addMouseListener(screen);
		addMouseMotionListener(screen);
		addMouseWheelListener(screen);
		
		setSize(1000, 600);
		setResizable(true);
		setTitle("Game7 demo");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {
		if (volatileImage == null || volatileImage.contentsLost() || volatileImage.getWidth() != getWidth() || volatileImage.getHeight() != getHeight()) {
			GraphicsConfiguration gc = getGraphicsConfiguration();
			volatileImage = gc.createCompatibleVolatileImage(getWidth(), getHeight());
			graphics2d = volatileImage.createGraphics();
		}
		
		long currentTime = System.currentTimeMillis();
		if(currentTime > updateTick) {
			updateTick += 50;
			screen.update();
		}
		
		if(currentTime > frameTick) {
			frameTick += 16;

			screen.draw(graphics2d);
			g.drawImage(volatileImage, 0, 0, null);
		}
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {}
		
		repaint();
	}

	
	
	
	public static void main(String[] args) {
		new Window();
	}
	
	static abstract class Screen implements KeyListener, MouseInputListener, MouseMotionListener, MouseWheelListener {
		
		Window window;
		Screen(Window window) {
			this.window = window;
		}
		
		abstract void draw(Graphics2D g);
		abstract void update();

		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {}
		
	}
}
