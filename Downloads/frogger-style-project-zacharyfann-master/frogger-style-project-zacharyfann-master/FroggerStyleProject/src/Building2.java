import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Building2{
	private Image forward; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x;						//position of the object
	int y;
	int vx, vy;						//movement variables
	double scaleWidth = 2.0;		//change to scale image
	double scaleHeight = 1.8; 		//change to scale image

	public Building2() {
		forward 	= getImage("/imgs/"+"building-pixilart.png"); //load the image for Tree
		
		//alter these
		width = 145;
		height = 45;
		
		//used for placement on the JFrame
		x = -width;
		y = 300;
		//if your movement will not be hopping base
		vx = -5;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public int getVx() {
		return vx;
	}
	//2nd constructor - allow setting x and y during construction
	public Building2(int x, int y) {
		
		//call the default constructor for all the normal stuff
		this(); //invokes default constructor
		
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
		
	}
	public boolean collided(Spiderman character) {
		
		//represent each object as a rectangle
		//then check if they are intersecting
		Rectangle main = new Rectangle(
			character.getX(),
			character.getY(),
			character.getWidth(),
			character.getHeight()
			);
		Rectangle thisObject = new Rectangle(x+10,y+5,width,height);
		
		//user built-in method to check intersection (COLLISION)
		return main.intersects(thisObject);
		
	}
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
	//update x and y if using vx, vy variables	
		x+=vx;
		y+=vy;	
		
		//for infinite scrolling - teleport to the other side
		//once it leaves the other side!
		if(x <-300) {
			x=1200;
		}
		
		init(x,y);
		
		
	g2.drawImage(forward, tx,null);	
	
	//draw hit box based on x,y,width,height
	//for collision detection 
	
	if (Frame.debugging) {
		g.setColor(Color.green);
		g.drawRect(x+10, y+5, width, height);
	}
	
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Building1.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
