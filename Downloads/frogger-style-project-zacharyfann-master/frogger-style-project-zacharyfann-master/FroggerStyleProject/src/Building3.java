import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Building3 {
    private Image forward;     
    private AffineTransform tx;
    
    int dir = 0;                     
    int width, height;
    int x;                        
    int y;
    int vx, vy;                     
    double scaleWidth = 2.0;        
    double scaleHeight = 1.8;         

    public Building3() {
        forward = getImage("/imgs/"+"building-pixilart.png");
        
        width = 145;
        height = 45;
        
        x = -width;
        y = 300;
        vx = 6; // Faster than Building2
        vy = 0;
        
        tx = AffineTransform.getTranslateInstance(0, 0);
        
        init(x, y);
    }
    
    public int getVx() {
        return vx;
    }

    public Building3(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public boolean collided(Spiderman character) {
        Rectangle main = new Rectangle(
            character.getX(),
            character.getY(),
            character.getWidth(),
            character.getHeight()
        );
        Rectangle thisObject = new Rectangle(x+10, y+5, width, height);
        
        return main.intersects(thisObject);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        x += vx;
        y += vy;    
        
        if(x > 1350) {
            x = -150;
        }
        
        init(x,y);
        
        g2.drawImage(forward, tx, null);    
        
        if (Frame.debugging) {
            g.setColor(Color.blue); // Different color for Building3
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
            URL imageURL = Building3.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
} 