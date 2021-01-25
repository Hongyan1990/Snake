package com.imooc.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	Yard y;
	Node n = new Node(10, 10, Dir.R);
	Node tail, head;
	public Snake(Yard y) {
		this.y = y;
		this.head = n;
		this.tail = n;
	}
	
	public void draw(Graphics g) {
		addHead();
		deleteTail();
		checkDead();
		for(Node n=head; n!=null; n = n.next) {
			n.draw(g);
		} 
	}
	
	public void pressKey(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case  KeyEvent.VK_UP:
			if(head.dir != Dir.D)
				head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Dir.L)
				head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Dir.U)
				head.dir = Dir.D;
			break;
		case KeyEvent.VK_LEFT:
			if(head.dir != Dir.R)
				head.dir = Dir.L;
			break;
		}
		
	}
	
	public void addHead() {
		Dir dir = head.dir;
		Node node;
		Node temp;
		switch(dir) {
		case U:
			node = new Node(head.row, head.col-1, dir);
			break;
		case R:
			node = new Node(head.row+1, head.col, dir);
			break;
		case D:
			node = new Node(head.row, head.col+1, dir);
			break;
		case L:
			node = new Node(head.row-1, head.col, dir);
			break;
		default:
			node = new Node(head.row, head.col, dir);
		}
		node.next = head;
		head.prev = node;
		head = node;
		
	}
	
	public void deleteTail() {
		tail = tail.prev;
		tail.next = null;
	}
	
	public void checkDead() {
		int row = head.row;
		int col = head.col;
		if(col>Yard.COLS-1 || row>Yard.ROWS-1 || col<1 || row<2) {
			this.y.stopGame();
		}
	}
	
	public void eat(Egg e) {
		if(this.getRectangle().intersects(e.getRectangle())) {
			e.reAppear();
			addHead();
			y.setScore(y.getScore() + 5);
		}
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(head.row*Yard.SIZE, head.col*Yard.SIZE, head.w, head.h);
	}
	
	private class Node {
		int w = Yard.SIZE;
		int h = Yard.SIZE;
		int row, col;
		Node next, prev;
		Dir dir = Dir.R;
		public Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(row*Yard.SIZE, col*Yard.SIZE, w, h);
			g.setColor(c);
		}
	}
}
