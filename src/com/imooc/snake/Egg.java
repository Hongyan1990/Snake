package com.imooc.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Egg {
	private int row, col;
	private int w = Yard.SIZE;
	private int h = Yard.SIZE;
	
	private Color color = Color.GREEN;
	private static Random r = new Random();
	
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS-2) + 2);
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(row*Yard.SIZE, col*Yard.SIZE, w, h);
		if(color == Color.GREEN) {
			color = Color.RED;
		} else {
			color = Color.GREEN;
		}
		g.setColor(c);
	}
	
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2;
		this.col = r.nextInt(Yard.COLS-2) + 2;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(row*Yard.SIZE, col*Yard.SIZE, w, h);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
}
