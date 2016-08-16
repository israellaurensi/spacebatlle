import java.awt.Image;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Missile {

	private int x, y;
	private Image image;
	boolean visible;
	private int width, height;

	private final int BOARD_WIDTH = 590;
	private int missile_speed;

	public Missile(int x, int y, int speed) {

		ImageIcon ii = new ImageIcon(this.getClass().getResource("missile.png"));
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		missile_speed = speed;
	}


	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void move() {
		x += missile_speed;
		if (x > BOARD_WIDTH)
			visible = false;
	}
}