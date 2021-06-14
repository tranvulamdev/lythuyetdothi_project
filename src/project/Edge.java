package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Edge {
	private Dot dot1, dot2;
	private int weight;
	Color color = Color.BLACK;
	boolean typeEdge = true;

	public Edge() {
		dot1 = dot2 = new Dot();
	}

	public Edge(Dot d1, Dot d2) {
		d1.setX(d1.getX() + 5);
		d1.setY(d1.getY() + 5);
		d2.setX(d2.getX() + 5);
		d2.setY(d2.getY() + 5);
		this.dot1 = d1;
		this.dot2 = d2;
	}

	public Dot getDot1() {
		return this.dot1;
	}

	public Dot getDot2() {
		return this.dot2;
	}

	public void setEdge(Dot a, Dot b) {
		this.dot1 = a;
		this.dot2 = b;
	}

	public void setDot1(Dot d1) {
		d1.setX(d1.getX() + 5);
		d1.setY(d1.getY() + 5);
		this.dot1 = d1;
	}

	public void setDot2(Dot d2) {
		d2.setX(d2.getX() + 5);
		d2.setY(d2.getY() + 5);
		this.dot2 = d2;
	}

	public boolean has1() {
		return (dot1.getX() > 0 && dot1.getY() > 0);
	}

	public boolean has2() {
		return (dot2.getX() > 0 && dot2.getY() > 0);
	}

	public boolean equals1(Dot dot) {
		return dot1.getX() == dot.getX() && dot1.getY() == dot.getY();
	}

	public boolean equals2(Dot dot) {
		return dot2.getX() == dot.getX() && dot2.getY() == dot.getY();
	}

	public boolean equalsEdge(Edge otherEdge) {
		return (this.equals1(otherEdge.getDot1()) && this.equals2(otherEdge.getDot2()))
				|| (this.equals1(otherEdge.getDot2()) && this.equals2(otherEdge.getDot1()));
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public int calcWeight() {
		int x1 = dot1.getX();
		int y1 = dot1.getY();
		int x2 = dot2.getX();
		int y2 = dot2.getY();
		weight = (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) / 5;
		return weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(color);

		int x1 = dot1.getX();
		int y1 = dot1.getY();
		int x2 = dot2.getX();
		int y2 = dot2.getY();

		int midPointX = (int) ((x2 + x1) / 2);
		int midPointY = (int) ((y2 + y1) / 2);
		g2.drawString(Integer.toString(weight), midPointX, midPointY);
		drawEdgeLine(g2, x1, y1, x2, y2);
	}

	public void drawEdgeLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
		if (x1 - x2 >= -80 && (y1 - y2 >= 80 || y1 - y2 <= -80)) {
			if (y1 < y2)
				g2.drawLine(x1 + 6, y1 + 20, x2 + 6, y2 - 5);
			else if (y1 > y2)
				g2.drawLine(x1 + 6, y1 - 5, x2 + 6, y2 + 20);
			else
				g2.drawLine(x1 + 6, y1 - 5, x2 + 6, y2 + 20);
		}

		else if (x1 - x2 <= 80 && (y1 - y2 >= 80 || y1 - y2 <= -80)) {
			if (y1 < y2)
				g2.drawLine(x1 + 6, y1 + 20, x2 + 6, y2 - 5);
			else if (y1 > y2)
				g2.drawLine(x1 + 6, y1 - 5, x2 + 6, y2 + 20);
			else
				g2.drawLine(x1 + 6, y1 - 5, x2 + 6, y2 + 20);
		}

		else if (y1 - y2 >= -80) {
			if (x1 < x2)
				g2.drawLine(x1 + 20, y1 + 6, x2 - 6, y2 + 6);
			else
				g2.drawLine(x1 - 6, y1 + 6, x2 + 20, y2 + 6);
		}

		else if (y1 - y2 <= 80) {
			if (x1 < x2)
				g2.drawLine(x1 + 20, y1 + 6, x2 - 6, y2 + 6);
			else
				g2.drawLine(x1 - 6, y1 + 6, x2 + 20, y2 + 6);
		}
	}

}
