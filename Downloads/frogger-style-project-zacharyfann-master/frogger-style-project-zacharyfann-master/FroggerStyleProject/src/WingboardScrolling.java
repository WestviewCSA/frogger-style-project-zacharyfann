import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class WingboardScrolling{
	private Image forward; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x;						//position of the object
	int y;
	double vx, vy;						//movement variables
	double scaleWidth = 1.0;		//change to scale image
	double scaleHeight = 1.0; 		//change to scale image

	public WingboardScrolling() {
		forward 	= getImage("/imgs/"+"output-onlinegiftools (2).gif"); //load the image for Tree
		
		//alter these
		width = 35;
		height = 65;
		
		//used for placement on the JFrame
		x = -width;
		y = 300;
		//if your movement will not be hopping base
		vx = 4.5;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public double getVx() {
		return vx;
	}
	//2nd constructor - allow setting x and y during construction
	public WingboardScrolling(int x, int y) {
		
		//call the default constructor for all the normal stuff
		this(); //invokes default constructor
		
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
		
	}
	public boolean collided(Spiderman character) {
		
		//represent each object as a rectangle
		//then check if they are intersecting
		Rectangle main1 = new Rectangle(
			character.getX(),
			character.getY(),
			character.getWidth(),
			character.getHeight()
			);
		Rectangle thisObject = new Rectangle(x+70,y+15,width,height);
		Rectangle main2 = new Rectangle(
				character.getX(),
				character.getY(),
				character.getWidth(),
				character.getHeight()
				);
			Rectangle thisObject2 = new Rectangle(x+190,y+15,width,height);
		Boolean inter = false;
		//user built-in method to check intersection (COLLISION)
		if(main1.intersects(thisObject)) {
			inter = true;
		} else if (main2.intersects(thisObject2)) {
			inter = true;
		}
		return inter;
	}
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
	//update x and y if using vx, vy variables	
		x+=vx;
		y+=vy;	
		
		//for infinite scrolling - teleport to the other side
		//once it leaves the other side!
		if(x >950) {
			x=-150;
		}
		
		init(x,y);
		
		
	g2.drawImage(forward, tx,null);	
	
	//draw hit box based on x,y,width,height
	//for collision detection 
	
	if (Frame.debugging) {
		g.setColor(Color.green);
		g.drawRect(x+80, y+15, width, height);
		g.drawRect(x+190, y+15, width, height);
	}
	
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = WingboardScrolling.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
