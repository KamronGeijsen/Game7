package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Window.Screen;

public class GameScreen extends Screen {
	
	
	BufferedImage map;
	BufferedImage knight;
	BufferedImage barracks;
	double x, y;
	double targetx, targety;
	double scale = 600d/240;
	
	GameScreen(Window window) {
		super(window);
		
		try {
			map = ImageIO.read(new File("data/map.png"));
			knight = ImageIO.read(new File("data/cavalry.png"));
			barracks = ImageIO.read(new File("data/barracks.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());
		AffineTransform tx = g.getTransform();
		g.scale(scale, scale);
		
//		g.fillRect(100, 100, 100, 100);
		g.drawImage(map, 0, 0, null);
		
		g.drawImage(knight, (int)x-16, (int)y-32, null);
		
		g.drawImage(barracks, 192, 160, null);
		
		g.setColor(Color.RED);
		g.fillOval((int)targetx-2, (int)targety-2, 4, 4);
		
		g.setColor(Color.BLUE);
		g.fillOval((int)x-2, (int)y-2, 5, 5);
		
		g.setTransform(tx);
	}
	
	void update() {
//		System.out.println("Hello!");
		double dx = targetx - x;
		double dy = targety - y;
		
		if(Math.sqrt(dx*dx+dy*dy) < 1.5) {
			x = targetx;
			y = targety;
		} else {
			double r = Math.atan2(dy, dx);
			
			x += 1.5*Math.cos(r);
			y += 1.5*Math.sin(r);
		}
		
		
		
//		System.out.println(r);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		targetx = e.getX()/scale;
		targety = e.getY()/scale;
//		System.out.println(targetx + ", " +targety);
	}
}
