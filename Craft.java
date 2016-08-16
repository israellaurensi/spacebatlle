import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Craft {

	private String craft = "craft.png";

	private Board board;
	private int b_width;
	private int b_height;

	private int x, dx;
	private int y, dy;
	private int width;
	private int height;
	private boolean visible;
	private Image image;

	private int level;
	private int life;
	private ArrayList missiles;

	public Craft(Board b) {
		board = b;
		b_width = 600;
		b_height = 500;
		
		ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		missiles = new ArrayList();
		visible = true;
		life = 500;
		x = 40;
		y = 60;
	}


	public void move() {

		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}

		if (x >= b_width) {
			x = b_width;
		}

		if (y >= b_height) {
			y = b_height;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	public ArrayList getMissiles() {
		return missiles;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void loseLife() {
		life = life - 1;
	}
	
	public int getLife() {
		return life;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			fire();
		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -2;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 2;
		}
	}

	public void fire() {
		if (board.getScore() < 50) {
			// level 1
			missiles.add(new Missile(x + width, y + height/2, 1));
		} else if (board.getScore() > 300) {
			// level 5
			missiles.add(new Missile(x + width, y, 4));
			missiles.add(new Missile(x + width, y + height/2, 4));
			missiles.add(new Missile(x + width, y + height, 4));

			missiles.add(new Missile(x + width+45, y, 4));
			missiles.add(new Missile(x + width+45, y + height/2, 4));
			missiles.add(new Missile(x + width+45, y + height, 4));
		} else if (board.getScore() > 200) {
			// level 4
			missiles.add(new Missile(x + width, y, 3));
			missiles.add(new Missile(x + width, y + height/2, 3));
			missiles.add(new Missile(x + width, y + height, 3));
		} else if (board.getScore() > 100) {
			// level 3
			missiles.add(new Missile(x + width, y, 2));
			missiles.add(new Missile(x + width, y + height/2, 2));
			missiles.add(new Missile(x + width, y + height, 2));
		} else if (board.getScore() >= 50) {
			// level 2
			missiles.add(new Missile(x + width, y, 2));
			missiles.add(new Missile(x + width, y + height, 2));
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
}