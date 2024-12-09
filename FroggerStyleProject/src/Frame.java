import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//for any debugging code we add
	public static boolean debugging = true;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	
	
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	Spiderman spiderman = new Spiderman();
	Spiderman spiderman2 = new Spiderman(100, 200);
	//frame width/height
	
	ArrayList<LifeImage> lives = new ArrayList<LifeImage>();
	EndScreen endScreen = new EndScreen(0,0);
	WingboardScrolling[] row1 = new WingboardScrolling[3];
	ArrayList<WingboardScrolling> row1list = new ArrayList<WingboardScrolling>();
	DroneScrolling[] row2 = new DroneScrolling[4];
	LightningBoltScrolling[] row3 = new LightningBoltScrolling[3];
	DroneScrollingRight[] row4 = new DroneScrollingRight[4];
	Building[] row5 = new Building[5];
	DroneScrolling3[] row6 = new DroneScrolling3[5];
	Building1[] row7 = new Building1[5];
	
	Background background = new Background(0,0);
	Goop goop = new Goop(0, 200);
	int width = 900;
	int height = 1200;	
	

	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//paint the other objects on the screen
		background.paint(g);
		goop.paint(g);
		
		//have the row1 objects paint on the screen!
		//for each obj in row1
		
		for (LifeImage obj: lives) {
			obj.paint(g);
		}

		for (WingboardScrolling obj: row1) {
			obj.paint(g);
		}
		
		for (DroneScrolling obj: row2) {
			obj.paint(g);
		}
		for (LightningBoltScrolling obj: row3) {
			obj.paint(g);
		}
		for (DroneScrollingRight obj: row4) {
			obj.paint(g);
		}
		for (Building obj: row5) {
			obj.paint(g);
		}
		for (DroneScrolling3 obj: row6) {
			obj.paint(g);
		}
		for (Building1 obj: row7) {
			obj.paint(g);
		}
		for (WingboardScrolling obj:row1list) { //arraylist example
			obj.paint(g);
		}
		spiderman.paint(g);
		
		boolean riding1 = false;
		boolean riding2 = false;
		boolean danger = false;
		
		//Collision Detection
		
		
		
		for(WingboardScrolling obj: row1) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		for(WingboardScrolling obj: row1list) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		
		for(Building obj: row5) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding1 = true;
//				break;
				
			}
			//main character stops moving if not on a rideable object
			//but also let's limit it in the Y
			
			else if(!riding1 && danger) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
			else if (!riding1) {
				riding1= false;
				spiderman.setVx(0);
			}
		
		}
		if (goop.collided(spiderman)) {
			danger = true;
		}
		else if (!goop.collided(spiderman)) {
			danger = false;
		}
		
		for(Building1 obj: row7) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			
			
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding2 = true;
//				break;
			} 
			
			//main character stops moving if not on a rideable object
			//but also let's limit it in the Y
			
			else if(danger && !riding2) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
			else if (!riding2) {
				riding2 = false;
				spiderman.setVx(0);
			} 
			
		
		}
		for(DroneScrolling obj: row2) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		for(LightningBoltScrolling obj: row3) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		for(DroneScrollingRight obj: row4) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		for(DroneScrolling3 obj: row6) {
			
			//invoke the collided method for your class
			//class- pass the main character for your argument
			
			if (obj.collided(spiderman)) {
				if (lives.size()==0) {
					
					endScreen.paint(g);
				}
				resetDuck();
			}
		
		}
		
		
		if (lives.size()==0) {
				
			endScreen.paint(g);
		}
			
		
	}
	
	
	private Image getImage(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
        f.add(this);
		f.setVisible(true);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
//		backgroundMusic.play();
/*
 * Setup any 1D array here! - create the objects that go in the ;)
 * 
 */
	//traverse the array
		
		for(int i = 0; i<5; i++) {
			this.lives.add(new LifeImage(i*50,0));
		}
		
		for(int i = 0; i<row1.length; i++) {
			row1[i] = new WingboardScrolling(i*350,500);
		}
		
		//practice row for ArrayList
		
		for (int i = 0; i<5; i++) {
			//run the body 10x
			this.row1list.add(new WingboardScrolling(i*300,100));
		}
		for(int i = 0; i<row2.length; i++) {
			row2[i] = new DroneScrolling(i*260,700);
		}
		for(int i = 0; i<row3.length; i++) {
			row3[i] = new LightningBoltScrolling(i*350,820);
		}
		for(int i = 0; i<row4.length; i++) {
			row4[i] = new DroneScrollingRight(i*280,650);
		}
		for(int i = 0; i<row5.length; i++) {
			row5[i] = new Building(i*300,300);
		}
		for(int i = 0; i<row6.length; i++) {
			row6[i] = new DroneScrolling3(i*200,600);
		}
		for(int i = 0; i<row7.length; i++) {
			row7[i] = new Building1(i*300,350);
		}
		
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void resetDuck() {
		spiderman.setVx(0);
		spiderman.x = 550;
		spiderman.y = 950;
		if (lives.size()>0) {
			lives.remove(lives.size()-1);
		}	
		else {	
			endGame();
			}
		}
		
	public void endGame() {
		
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode()==87) {
			spiderman.move(0);
			//move main character up
		} else if (arg0.getKeyCode()==83) {
			//move character down
			spiderman.move(1);
		} else if (arg0.getKeyCode()==65) {
			//move character left
			spiderman.move(2);
		} else if (arg0.getKeyCode()==68) {
			//move character down
			spiderman.move(3);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

