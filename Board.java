import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.*;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Board extends JPanel implements ActionListener {


	private Craft craft;
	private ArrayList aliens;
	private Thread alienGenerator;

	private Timer timer;
	private boolean ingame;
	private int score;

	private Image background;
	private int b_width;
	private int b_height;

	public Board() {

		addKeyListener(new TAdapter());
		setFocusable(true);

		background = new ImageIcon("background.gif").getImage();
		// setBackground(Color.BLACK);

		setDoubleBuffered(true);
		ingame = true;
		score = 0;

		setSize(600, 500);

		craft = new Craft(this);

		initAliens();

		timer = new Timer(5, this);
		timer.start();
	}

	public void addNotify() {
		super.addNotify();
		b_width = getWidth();
		b_height = getHeight();
	}

	public void initAliens() {
		aliens = new ArrayList();

		alienGenerator = new AlienGenerator(this, aliens);
		alienGenerator.start();
		
		try {Thread.sleep(2000);} catch(InterruptedException e) {  }
	}

	public int getScore() { return score; }
	public int getBoardWidth() { return b_width; }
	public int getBoardHeight() { return b_height; }

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), null);

		if (ingame) {

			Graphics2D g2d = (Graphics2D)g;

			if (craft.isVisible())
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),
				this);

			ArrayList ms = craft.getMissiles();

			for (int i = 0; i < ms.size(); i++) {
				Missile m = (Missile)ms.get(i);
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++) {
				Alien a = (Alien)aliens.get(i);
				if (a.isVisible())
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
			}

			g2d.setColor(Color.WHITE);
			g2d.drawString("Aliens left: " + aliens.size(), 5, 15);
			g2d.drawString("Life: " + craft.getLife(), 5, 35);
			g2d.drawString("Score: " + score + " points", 5, 55);
			
			int level = 1;
			if (score > 300) { level = 5; }
			else if (score > 200) { level = 4; }
			else if (score > 100) { level = 3; }
			else if (score > 50) { level = 2; }
			g2d.drawString("Level: " + level, 5, 75);

		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (b_width - metr.stringWidth(msg)) / 2,
				b_height / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}


	public void actionPerformed(ActionEvent e) {

		ArrayList ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++) {
			Missile m = (Missile) ms.get(i);
			if (m.isVisible()) {
				m.move();
			} else ms.remove(i);
		}

		for (int i = 0; i < aliens.size(); i++) {
			Alien a = (Alien) aliens.get(i);
			if (a.isVisible()) 
				a.move();
			else aliens.remove(i);
		}

		craft.move();
		checkCollisions();
		repaint();
	}

	public void checkCollisions() {

		Rectangle r3 = craft.getBounds();
		boolean collision = false;

		for (int j = 0; j<aliens.size(); j++) {
			Alien a = (Alien) aliens.get(j);
			Rectangle r2 = a.getBounds();

			if (r3.intersects(r2)) {
				craft.loseLife();

				if (craft.getLife() <= 0) {
					craft.setVisible(false);
					a.setVisible(false);
					ingame = false;
				}
			}
		}

		ArrayList ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++) {
			Missile m = (Missile) ms.get(i);

			Rectangle r1 = m.getBounds();

			for (int j = 0; j<aliens.size(); j++) {
				Alien a = (Alien) aliens.get(j);
				Rectangle r2 = a.getBounds();

				if (r1.intersects(r2)) {
					m.setVisible(false);
					a.setVisible(false);

					score++;
				}
			}
		}
	}


	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			craft.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

			craft.keyPressed(e);
		}
	}
}