package com.imooc.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	public static final int ROWS = 50;
	public static final int COLS = 50;
	public static final int SIZE = 10;
	private int score = 0;
	boolean gameOver = false;
	
	private Font gameOverFont = new Font("宋体", Font.BOLD, 50);
	Snake snake = new Snake(this);
	Egg e = new Egg();
	PaintThread paintThread = new PaintThread();
	Image offScreenImage = null;
	
	public void launch() {
		this.setLocation(200, 200);
		this.setSize(COLS * SIZE, ROWS * SIZE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				int key = e.getKeyCode();
				System.out.println(key);
				snake.pressKey(e);
			}
			
		});
		new Thread(paintThread).start();
		this.setVisible(true);
	}
	
	public void stopGame() {
		this.gameOver = true;
	}
	
	private class PaintThread implements Runnable {
		private boolean running = true;
		private boolean pause = false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(running) {
				if(pause) continue;
				repaint();
				System.out.println("repaint");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void stop() {
			this.running = false;
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() {
			this.pause = false;
		}
		
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS * SIZE, ROWS * SIZE);
		g.setColor(Color.DARK_GRAY);
		for(int i=1; i<COLS; i++) {
			g.drawLine(i*SIZE, 0, i*SIZE, ROWS*SIZE);
		}
		for(int i=1; i<ROWS; i++) {
			g.drawLine(0, i*SIZE, COLS*SIZE, i*SIZE);
		}
		g.setColor(Color.YELLOW);
		g.drawString("score: " + score, 10, 60);
		if(this.gameOver) {
			g.setFont(gameOverFont);
			g.drawString("游戏结束 ", 100, 200);
			System.out.println("游戏结束");
			this.paintThread.pause();
		} else {
			snake.eat(e);
			e.draw(g);
			snake.draw(g);
			
		}
		
	}
	

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS*SIZE, ROWS*SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static void main(String[] args) {
		new Yard().launch();

	}

}
