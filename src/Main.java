import javax.swing.*; 

public class Main {

	public static void main(String[] args) throws Exception{
		int width = 600,height = 600; 
		
		JFrame frame = new JFrame("Snake.io");
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		ActualGame snakeio = new ActualGame(width,height);
		frame.add(snakeio);
		frame.pack();
		snakeio.requestFocus();
		
	}
}
