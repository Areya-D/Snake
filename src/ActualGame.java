import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList; 

public class ActualGame extends JPanel implements ActionListener, KeyListener{
	
	private class Tile{
		int x,y; 
		
		Tile(int x, int y){
			this.x = x; 
			this.y = y; 
		}
	}
	int width, height;
	int tileSize = 25; 
	
	//food, body, and snake head
	ArrayList<Tile> body; 
	Tile head,food; 
	Random rand; 
	
	//logic variables 
	Timer loop; 
	int velocityX, velocityY;
	boolean gameOver = false; 
	
	ActualGame(int width, int height){
		this.width = width; 
		this.height = height; 
		setPreferredSize(new Dimension(this.width, this.height));
		setBackground(Color.darkGray);
		addKeyListener(this);
		setFocusable(true);
		
		head = new Tile(5,5);
		body = new ArrayList<Tile>();
		
		food = new Tile(10,10);
		rand = new Random();
		putFood(); 
		
		velocityX = 0; 
		velocityY = 0; 
		
		loop = new Timer(100, this);
		loop.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		//snake head
		g.setColor(new Color(17,205,47));
		g.fill3DRect(tileSize * head.x, tileSize * head.y,tileSize,tileSize, true);

		//food
		g.setColor(Color.red);
		g.fill3DRect(tileSize * food.x, tileSize * food.y,tileSize,tileSize, true );

		//draw new parts of snake body
		for(int i = 0; i < body.size(); i++) {
			Tile part = body.get(i);
			g.setColor(Color.GREEN);
			g.fill3DRect(part.x * tileSize, part.y * tileSize, tileSize, tileSize,true);
		}
		
		//score
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		if(gameOver == true) {
			g.setColor(Color.red);
			g.drawString("You Lose: " + String.valueOf(body.size()),tileSize -16, tileSize );
		}
		else {
			g.drawString("Score:" + String.valueOf(body.size()), tileSize - 16, tileSize);
		}
	}
	public void putFood() {
		//puts food in random location on grid
		food.x = rand.nextInt(width/tileSize); //0-24
		food.x = rand.nextInt(height/tileSize); //0-24
	}
	
	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
		
	}
	public void move() {
		//eat food 
		if(collision(head,food)) {
			body.add(new Tile(food.x,food.y));
			putFood();
		}
		
		//each tile needs to attach to the end of the snake before the 
		//head can move
		
		for(int i = body.size()-1; i>= 0; i--) {
			Tile part = body.get(i);
			
			if(i==0) {
				part.x = head.x;
				part.y = head.y; 
			}
			else {
				Tile prevPart = body.get(i-1);
				part.x = prevPart.x; 
				part.y = prevPart.y; 
			}
		}

		//moves snake head
		head.y += velocityY; 
		head.x += velocityX; 
		
		//gameover
		
		//snake collides with own body
		for(int i = 0; i < body.size(); i++) {
			Tile spart = body.get(i);
			if(collision(head,spart)) {
			gameOver = true;
			}
		}
		
		if(head.x *tileSize < 0 || head.x *tileSize > width 
				|| head.y*tileSize < 0 || head.y*tileSize > height) {
			gameOver = true; 
		}
	}
		
	@Override 
	public void actionPerformed(ActionEvent event) {
		move();
		repaint();
		if(gameOver == true) {
			loop.stop();
			}
		}
	
	@Override
	public void keyPressed(KeyEvent event) {
		//&& velocityY/velocityX makes sure snake cant move backwards 
		
		if(event.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
			velocityX = 0;
			velocityY = -1; 
		}
		else if(event.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1; 
		}
		else if(event.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0; 
		}
		else if(event.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
			velocityX = 1;
			velocityY = 0;
		}
	}
	
	//dont need these two
	@Override
	public void keyTyped(KeyEvent event) {}
	
	@Override
	public void keyReleased(KeyEvent event) {}
	
}
