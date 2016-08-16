import javax.swing.JFrame;

public class Spacebattle extends JFrame {

	public Spacebattle() {
		add(new Board());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setTitle("Spacebattle");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Spacebattle();
	}
}