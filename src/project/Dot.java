package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Dot {
	private int x;
	private int y;
	private Color color;
	private int id;

	// constructor
	public Dot() {
		this.color = Color.BLACK;
		this.x = 0;
		this.y = 0;
	}

	public Dot(int a, int b) {
		this.color = Color.BLACK;
		this.x = a;
		this.y = b;
	}

	// getter setter
	public void setX(int a) {
		x = a;
	}

	public void setY(int b) {
		y = b;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getColor() {
		return color.toString();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	// method
	public boolean isEmpty() {
		return this.x == 0 && this.y == 0;
	}
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(color);
		g2.drawOval(x, y, 25, 25);
		g2.drawString("" + id, x + 8, y + 16);

	}
}
