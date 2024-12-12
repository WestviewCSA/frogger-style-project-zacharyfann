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

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//for any debugging code we add
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	
	
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("C:\\Users\\zacha\\Downloads\\frogger-style-project-zacharyfann-master\\frogger-style-project-zacharyfann-master\\FroggerStyleProject\\Spider-Man - 1967 - Main.wav", true);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	Spiderman spiderman = new Spiderman();
	Spiderman spiderman2 = new Spiderman(100, 200);
	//frame width/height
	
	ArrayList<LifeImage> lives = new ArrayList<LifeImage>();
	EndScreen endScreen = new EndScreen(0,0);
	WingboardScrolling[] row1 = new WingboardScrolling[3];
	ArrayList<WingboardScrolling> row1Upper = new ArrayList<WingboardScrolling>();
	DroneScrolling[] row2 = new DroneScrolling[4];
	LightningBoltScrolling[] row3 = new LightningBoltScrolling[3];
	DroneScrollingRight[] row4 = new DroneScrollingRight[4];
	Building[] row5 = new Building[3];
	DroneScrolling3[] row6 = new DroneScrolling3[5];
	Building1[] row7 = new Building1[4];
	Building2[] row8 = new Building2[5];
	Building3[] row9 = new Building3[5];
	
	Background background = new Background(0,0);
	Goop goop = new Goop(0, 200);
	int width = 900;
	int height = 1200;	
	
	private boolean keyPressed = false;
	private long lastKeyPressTime = 0;
	private final int KEY_DELAY = 75; // Milliseconds between allowed key presses
	
	private int score = 0;
	private Font scoreFont = new Font("Times New Roman", Font.BOLD, 40);
	
	private boolean waitingToReset = false;
	private long resetStartTime = 0;
	private final int RESET_DELAY = 2000; // 2 seconds in milliseconds
	
	SimpleAudioPlayer goopSound;
	
	
	
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
		for (Building obj: row5) {
			obj.paint(g);
		}
		for (Building1 obj: row7) {
			obj.paint(g);
		}
		for (Building2 obj: row8) {
			obj.paint(g);
		}
		for (Building3 obj: row9) {
			obj.paint(g);
		}

		for (WingboardScrolling obj: row1) {
			obj.paint(g);
		}
		for (WingboardScrolling obj: row1Upper) {
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
		
		for (DroneScrolling3 obj: row6) {
			obj.paint(g);
		}
		
		
		spiderman.paint(g);
		
		boolean riding = false;
		boolean danger = false;
		
		// Check goop collision first
		if (goop.collided(spiderman)) {
			danger = true;
		}
		
		// Check Building collisions
		for(Building obj: row5) {
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding = true;
			}
		}
		
		// Check Building1 collisions
		for(Building1 obj: row7) {
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding = true;
			}
		}
		
		// Check Building2 collisions
		for(Building2 obj: row8) {
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding = true;
			}
		}
		
		// Check Building3 collisions
		for(Building3 obj: row9) {
			if (obj.collided(spiderman)) {
				spiderman.setVx(obj.getVx());
				riding = true;
			}
		}
		
		// Reset if in danger and not riding
		if (danger && !riding) {
			if (lives.size()==0) {
				endScreen.paint(g);
			}
			resetDuck();
		}
		// Stop movement if not riding any building
		else if (!riding) {
			spiderman.setVx(0);
		}
		
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
		
		for(WingboardScrolling obj: row1Upper) {
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
			
		// Screen boundary check
		if (spiderman.x < 0 || spiderman.x > width - spiderman.width || 
			spiderman.y < 0 || spiderman.y > height - spiderman.height) {
			resetDuck();
		}
		
		// Add score display in top right
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(scoreFont);
		g2.setColor(Color.RED);
		g2.drawString("Score: " + score, width - 225, 50);

		// Check if player reached the end
		if (spiderman.getY() <= 100 && spiderman.getY() >= 0 && !waitingToReset) {
			score += 100;
			waitingToReset = true;
			resetStartTime = System.currentTimeMillis();
		}
		
		// Display text while waiting to reset
		if (waitingToReset) {
			g2.setFont(myFont);
			g2.setColor(Color.GREEN);
			g2.drawString("Goated Spiderman!", width/2 - 150, height/2);
		}
		
		// Check if it's time to reset after delay
		if (waitingToReset && System.currentTimeMillis() - resetStartTime >= RESET_DELAY) {
			waitingToReset = false;
			// Reset position without removing life
			spiderman.setVx(0);
			spiderman.x = 550;
			spiderman.y = 950;
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
		for (int i = 0; i<4; i++) {
			row1Upper.add(new WingboardScrolling(i*300,150));
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
			row5[i] = new Building(i*450,300);
		}
		for(int i = 0; i<row6.length; i++) {
			row6[i] = new DroneScrolling3(i*200,600);
		}
		for(int i = 0; i<row7.length; i++) {
			row7[i] = new Building1(i*400,350);
		}
		for(int i = 0; i<row8.length; i++) {
			row8[i] = new Building2(i*300,250);
		}
		for(int i = 0; i<row9.length; i++) {
			row9[i] = new Building3(i*300,200);
		}
		
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		ImageIcon cursorIcon = new ImageIcon("torch.png");
		if (cursorIcon.getImage() != null) {
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				cursorIcon.getImage(),
				new Point(0,0),"custom cursor"));
		}
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	public void resetDuck() {
			try {
				goopSound = new SimpleAudioPlayer("C:\\Users\\zacha\\Downloads\\frogger-style-project-zacharyfann-master\\frogger-style-project-zacharyfann-master\\FroggerStyleProject\\Voicy_Yummie.wav", false);
				goopSound.play();
			} catch (Exception e) {
				System.err.println("Error playing goop sound: " + e.getMessage());
		}
		
		spiderman.setVx(0);
		spiderman.x = 550;
		spiderman.y = 950;
		if (lives.size()>0) {
			lives.remove(lives.size()-1);
		} else {
			try {
				backgroundMusic.stop();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endGame();
		}
	}
		
	public void endGame() {
		try {
			backgroundMusic.stop();
		} catch (Exception e) {
			System.err.println("Error stopping background music: " + e.getMessage());
		}
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
		long currentTime = System.currentTimeMillis();
		
		// Check for reset key (R) when game is over
		if (arg0.getKeyCode() == 82 && lives.size() == 0) { // 82 is R key
			resetGame();
			return;
		}
		
		// Only process movement keys if enough time has passed since last press
		if (currentTime - lastKeyPressTime >= KEY_DELAY && !keyPressed) {
			keyPressed = true;
			lastKeyPressTime = currentTime;
			
			if(arg0.getKeyCode()==87) { // W key
				spiderman.move(0);
			} else if (arg0.getKeyCode()==83) { // S key
				spiderman.move(1);
			} else if (arg0.getKeyCode()==65) { // A key
				spiderman.move(2);
			} else if (arg0.getKeyCode()==68) { // D key
				spiderman.move(3);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keyPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void resetGame() {
		// Reset lives
		lives.clear();
		for(int i = 0; i < 5; i++) {
			lives.add(new LifeImage(i*50,0));
		}
		
		// Reset score
		score = 0;
		
		// Reset player position
		spiderman.x = 550;
		spiderman.y = 950;
		spiderman.setVx(0);
		
		// Restart background music
		try {
			backgroundMusic.play();
		} catch (Exception e) {
			System.err.println("Error playing background music: " + e.getMessage());
		}
	}

	

}

