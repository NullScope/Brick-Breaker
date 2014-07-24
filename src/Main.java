import javax.swing.JFrame;

public class Main{
	static JFrame window = new JFrame();
	Game gameWindow = new Game();
	public Main(){
		window.setTitle("Super Duper Brick Breaker");
		window.setBounds(0,0,(int)(Game.getScaleX()*Game.width+6),(int)(Game.getScaleY()*(Game.height+1.9)));
		window.add(gameWindow);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Main screen = new Main();
	}
}
