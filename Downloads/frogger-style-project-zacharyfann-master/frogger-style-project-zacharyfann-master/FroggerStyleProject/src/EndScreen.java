import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class EndScreen{
	private Image gameOver; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 9.5;		//change to scale image
	double scaleHeight = 10; 		//change to scale image

	public EndScreen(int x, int y) {
		this.x = x;
		this.y = y;
		gameOver = getImage("/imgs/pixilart-drawing (8).png");
		tx = AffineTransform.getTranslateInstance(x-50, y);
	}
	
	public boolean collided(EndScreen character) {
		
		//represent each object as a rectangle
		//then check if they are intersecting
		Rectangle main = new Rectangle();
		
		return false;
		
	}
	
	
	//Getters!
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
	//update x and y if using vx, vy variables	
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		
	g2.drawImage(gameOver, tx,null);	
	
	//draw hit box based on x,y,width,height
	//for collision detection 
	
	x = -30;

	// Center the game over screen
	// x = (900 - 600) / 2;  // 900 is frame width, 600 is approximate scaled image width
	// y = (1200 - 400) / 2; // 1200 is frame height, 400 is approximate scaled image height
	
	// Add reset instruction text
	g2.setColor(Color.RED);
	g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
	g2.drawString("Press R to Reset Game", 300, 800);
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = EndScreen.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
