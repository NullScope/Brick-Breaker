import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Game extends JPanel implements KeyListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8622458551243671389L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//DEADLY IMAGEICON OF DEATH
	ImageIcon ballIco = new ImageIcon("ball.png");
	ImageIcon ballParticleIco = new ImageIcon("ballParticle.png");
	ImageIcon ballFireIco = new ImageIcon("ballFire.png");
	ImageIcon wallIco = new ImageIcon("wall.png");
	
	ImageIcon backgroundIco = new ImageIcon("background.jpg");
	
	ImageIcon powerQIco = new ImageIcon("powerQ.png");
	ImageIcon powerWIco = new ImageIcon("powerW.png");
	ImageIcon powerEIco = new ImageIcon("ballFire.png");
	ImageIcon powerRIco = new ImageIcon("powerR.png");
	ImageIcon powerTIco = new ImageIcon("powerT.png");
	ImageIcon powerYIco = new ImageIcon("powerY.png");
	
	ImageIcon gunIco = new ImageIcon("gun.png");
	ImageIcon gunFireIco = new ImageIcon("gunFire.png");
	
	ImageIcon plasmaIco = new ImageIcon("plasma.png");
	
	ImageIcon playerIco = new ImageIcon("player.png");
	ImageIcon playerRightIco = new ImageIcon("playerRIGHT.png");
	ImageIcon playerLeftIco = new ImageIcon("playerLEFT.png");
	ImageIcon playerHitIco = new ImageIcon("playerhit.png");
	
	ImageIcon brick1RightIco = new ImageIcon("brick1RIGHT.png");
	ImageIcon brick1LeftIco = new ImageIcon("brick1LEFT.png");
	ImageIcon brick1MidIco = new ImageIcon("brick1MID.png");
	
	ImageIcon brick2RightIco = new ImageIcon("brick2RIGHT.png");
	ImageIcon brick2LeftIco = new ImageIcon("brick2LEFT.png");
	ImageIcon brick2MidIco = new ImageIcon("brick2MID.png");
	
	ImageIcon brick3RightIco = new ImageIcon("brick3RIGHT.png");
	ImageIcon brick3LeftIco = new ImageIcon("brick3LEFT.png");
	ImageIcon brick3MidIco = new ImageIcon("brick3MID.png");
	
	//DEADLY IMAGES OF DEATH
	Image ballIMG = ballIco.getImage();
	Image ballParticleIMG = ballParticleIco.getImage();
	Image ballFireIMG = ballFireIco.getImage();
	
	Image wallIMG = wallIco.getImage();
	
	Image backgroundIMG = backgroundIco.getImage();
	
	Image powerQIMG = powerQIco.getImage();
	Image powerWIMG = powerWIco.getImage();
	Image powerEIMG = powerEIco.getImage();
	Image powerRIMG = powerRIco.getImage();
	Image powerTIMG = powerTIco.getImage();
	Image powerYIMG = powerYIco.getImage();
	
	Image gunIMG = gunIco.getImage();
	Image gunFireIMG = gunFireIco.getImage();
	
	Image plasmaIMG = plasmaIco.getImage();
	
	Image playerIMG = playerIco.getImage();
	Image playerRightIMG = playerRightIco.getImage();
	Image playerLeftIMG = playerLeftIco.getImage();
	Image playerHitIMG = playerHitIco.getImage();
	
	Image brick1RightIMG = brick1RightIco.getImage();
	Image brick1MidIMG = brick1MidIco.getImage();
	Image brick1LeftIMG = brick1LeftIco.getImage();
	
	Image brick2RightIMG = brick2RightIco.getImage();
	Image brick2MidIMG = brick2MidIco.getImage();
	Image brick2LeftIMG = brick2LeftIco.getImage();
	
	Image brick3RightIMG = brick3RightIco.getImage();
	Image brick3MidIMG = brick3MidIco.getImage();
	Image brick3LeftIMG = brick3LeftIco.getImage();
	
	//Collision collisionMaster;
	
	private static javax.swing.Timer gunCycler = new javax.swing.Timer(200, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(player.getGunCycle() == 0){
				plasma.add(new Plasma(player.getGunLX(), player.getGunLY()));
				plasma.lastElement().start();
				player.setGunCycle(1);
				lastFired = 0;
			}else{
				plasma.add(new Plasma(player.getGunRX(), player.getGunRY()));
				plasma.lastElement().start();
				player.setGunCycle(0);
				lastFired = 1;
			}
			gunCycler.stop();
		}
	});
	
	private JButton play = new JButton("PLAY");
	private JButton exit = new JButton("EXIT");
	
	private static int move = 0;
	private static int playerFound = 0;
	private static int brickFound = 0;
	private static int defaultWidth = 40;
	private static int defaultHeight = 40;
	private static int defaultScaleX = 15;
	private static int defaultScaleY = 15;
	static int width = defaultWidth;
	static int height = defaultHeight;
	private static int scaleX = defaultScaleX;
	private static int scaleY = defaultScaleY;
	private static int fullscreen = 0;
	private static int paused = 0;
	private static int gameover = 0;
	private static int rightFlag = 0;
	private static int leftFlag = 0;
	private static int cycle = 0;
	private static int lastFired = -1;
	private static int sizeRunner;
	private static int spawn = 0;
	private static int lives = 3;
	private static int level = 1;
	private static int option = 0;
	
	private static JOptionPane menu = new JOptionPane();
	
	static Vector<Ball> bolas = new Vector<>();
	static Vector<Power> powerup = new Vector<Power>();
	static Vector<Wall> walls = new Vector<>();
	static Vector<Bricks> bricks = new Vector<>();
	static Vector<Plasma> plasma = new Vector<>();
	
	static Player player;
	
	char[][] matrix = new char[width][height];
	
	public Game(){
		
		setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 10));
		setDoubleBuffered(true);
		setBackground(Color.WHITE);
		setBounds(0, 0, width*scaleX, height*scaleY);
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		loadLevel();
		bolas.add(new Ball(player.getX()+player.getWidth()/3, player.getY()-15, 2, 1));
		requestFocus();
		
		//collisionMaster = new Collision(bolas, walls, bricks, powerup, player, scaleX, scaleY);
		//collisionMaster.start();
	}

	public void loadLevel(){
		BufferedReader temp = null;
		int y = 0;
		try {
			temp = new BufferedReader(new FileReader("level"+level+".txt"));
	        String str;
	        while((str = temp.readLine()) != null){
	        	if(y < height){
	        		for(int x = 0; x<width; x++){	
	        			matrix[x][y] = str.charAt(x);
	        			switch(matrix[x][y]){
		        			case('P'):{
		        				if(playerFound == 0){
		        					playerFound = 1;
		        					player = new Player(x*scaleX, y*scaleY, 10);
		        					player.start();
		        				}else{
		        					player.setWidth(player.getWidth()+scaleX);
		        				}
		        				brickFound = 0;
		        			break;
		        			}
		        			case('|'):{
		        				if(brickFound == 0){
		        					brickFound = 1;
			        				bricks.add(new Bricks(1));
			        				bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        				}else{
		        					brickFound = 0;
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        				}
		        			break;
		        			}
		        			
		        			case('3'):{
		        				String ch=""+(matrix[x][y]);
		        				int tempHit = (int) Double.parseDouble(ch);
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).setHits(tempHit);
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        				}
		        			break;
		        			}
		        			case('2'):{
		        				String ch=""+(matrix[x][y]);
		        				int tempHit = (int) Double.parseDouble(ch);
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).setHits(tempHit);
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        				}
		        			break;
		        			}
		        			case('-'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        				}
		        			break;
		        			}
		        			case('Q'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(1);
		        				}
		        			break;
		        			}
		        			case('W'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(2);
		        				}
		        			break;
		        			}
		        			case('E'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(3);
		        				}
		        			break;
		        			}
		        			case('R'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(4);
		        				}
		        			break;
		        			}
		        			case('T'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(5);
		        				}
		        			break;
		        			}
		        			case('Y'):{
		        				if(brickFound == 1){
		        					bricks.get(bricks.size()-1).addBricker(x*scaleX, y*scaleY, scaleX);
		        					bricks.get(bricks.size()-1).setPower(6);
		        				}
		        			break;
		        			}
		        			case('w'):{
		        				walls.add(new Wall(x*scaleX, y*scaleY));
		        				brickFound = 0;
		        			break;
		        			}
		        			case('_'):{
		        				brickFound = 0;
		        			break;
		        			}
		        			
	        			}
		        	}
		        	y+=1;
		    		
	        	}

	        }
		}catch(Exception e){
			
		}
	}
	
	public void movePlayer(){
		if(move > 0 && rightFlag == 0){
			player.setX(player.getX()+5);
			if(spawn == 0){
				bolas.firstElement().setX(bolas.firstElement().getX()+5);
			}
			move = move-1;
			player.setDirection(2);
			leftFlag = 0;
		}else{
			if(move < 0 && leftFlag == 0){
				player.setX(player.getX()-5);
				if(spawn == 0){
					bolas.firstElement().setX(bolas.firstElement().getX()-5);
				}
				move = move+1;
				player.setDirection(1);
				rightFlag = 0;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void paint(Graphics g) {
		try{
			if(gameover == 0){
				if(paused==0){
					super.paint(g);
					if(lives == 0){
						gameover = 1;
					}
					if(bricks.size() == 0){
						level += 1;
						gameover = 1;
					}
					if(bolas.size() == 0){
						lives -= 1;
						spawn = 0;
						for (int i = 0; i < plasma.size(); i++) {
							plasma.get(i).suspend();
						}
						for (int i = 0; i < powerup.size(); i++) {
							powerup.get(i).suspend();
						}
						player.setGunActive(0);
						player.setWidth(player.getDefaultWidth());
						if(lives > 0){
							bolas.add(new Ball(player.getX()+player.getWidth()/3, player.getY()-15, 2, 1));
						}
						
					}
					movePlayer();
					
					Graphics2D g2d = (Graphics2D)g;
					AffineTransform originalTransform = g2d.getTransform();
					g2d.drawString("", -100,-100);
					
					//g2d.drawImage(backgroundIMG, 0, 0, width*scaleX, height*scaleY, this);
					
					if(player.getDirection() == 0){
						if(player.getHit() == 1){
							g2d.drawImage(playerHitIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
						}else{
							g2d.drawImage(playerIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
						}
					}else{
						if(player.getDirection() == 1){
							if(player.getHit() == 1){
								g2d.drawImage(playerHitIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
							}else{
								g2d.drawImage(playerLeftIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
							}
						}else{
							if(player.getHit() == 1){
								g2d.drawImage(playerHitIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
							}else{
								g2d.drawImage(playerRightIMG,player.getX(), player.getY(), player.getWidth(), scaleY, this);
							}
						}
					}
					
					if(player.getGunActive() == 1){
						if(lastFired == -1){
							g2d.drawImage(gunIMG,player.getGunLX(), player.getGunLY(), this);
							g2d.drawImage(gunIMG,player.getGunRX(), player.getGunRY(), this);
						}else{
							if(lastFired == 0){
								g2d.drawImage(gunFireIMG,player.getGunLX(), player.getGunLY(), this);
								g2d.drawImage(gunIMG,player.getGunRX(), player.getGunRY(), this);
							}else{
								g2d.drawImage(gunIMG,player.getGunLX(), player.getGunLY(), this);
								g2d.drawImage(gunFireIMG,player.getGunRX(), player.getGunRY(), this);
							}
						}
					}
					
					for (int i = 0; i < plasma.size(); i++) {
						g2d.drawImage(plasmaIMG, plasma.get(i).getX(), plasma.get(i).getY(), this);
					}
					
					for (int i = 0; i < bolas.size(); i++) {
						if(bolas.get(i).getPassable() == 0){
							g2d.drawImage(ballIMG,bolas.get(i).getX(), bolas.get(i).getY(), 15, 15, this);
						}else{
							g2d.drawImage(ballFireIMG,bolas.get(i).getX(), bolas.get(i).getY(), 15, 15, this);
						}
					}
					
					g2d.setColor(Color.BLACK);
					for (int i = 0; i < powerup.size(); i++) {
						cycle += 1;
						if(cycle >= 0 && cycle <= 20){
							g2d.setColor(Color.CYAN);
						}else{
							if(cycle >= 0 && cycle <= 40){
								g2d.setColor(Color.WHITE);
							}else{
								if(cycle >= 0 && cycle <= 60){
									g2d.setColor(Color.BLACK);
									cycle = 0;
								}
							}
						}
						switch(powerup.get(i).getPower()){
							case(1):{
								g2d.drawImage(powerQIMG,powerup.get(i).getX(), powerup.get(i).getY(),powerQIco.getIconWidth(),powerQIco.getIconHeight(), this);
								g2d.drawString("TRIPLE BALL!!!", powerup.get(i).getX()-17, powerup.get(i).getY()-2);
							break;
							}
							case(2):{
								g2d.drawImage(powerWIMG,powerup.get(i).getX(), powerup.get(i).getY(),powerWIco.getIconWidth(),powerWIco.getIconHeight(), this);
								g2d.drawString("PLASMA GUNS!!!", powerup.get(i).getX()-20, powerup.get(i).getY()-2);
							break;
							}
							case(3):{
								g2d.drawImage(powerEIMG,powerup.get(i).getX(), powerup.get(i).getY(),powerEIco.getIconWidth(),powerEIco.getIconHeight(), this);
								g2d.drawString("FIRE BALL!!!", powerup.get(i).getX()-20, powerup.get(i).getY()-2);
							break;
							}
							case(4):{
								g2d.drawImage(powerRIMG,powerup.get(i).getX(), powerup.get(i).getY(),powerRIco.getIconWidth(),powerRIco.getIconHeight(), this);
								g2d.drawString("PAD SIZE+!!!", powerup.get(i).getX()-15, powerup.get(i).getY()-2);
							break;
							}
							case(5):{
								g2d.drawImage(powerTIMG,powerup.get(i).getX(), powerup.get(i).getY(),powerRIco.getIconWidth(),powerRIco.getIconHeight(), this);
								g2d.drawString("PAD SIZE-!!!", powerup.get(i).getX()-15, powerup.get(i).getY()-2);
							break;
							}
							case(6):{
								g2d.drawImage(powerYIMG, powerup.get(i).getX(), powerup.get(i).getY(),powerYIco.getIconWidth(),powerYIco.getIconHeight(), this);
								g2d.drawString("EXTRA LIFE!!!", powerup.get(i).getX()-15, powerup.get(i).getY()-2);
							break;
							}
						}
					}
					for (int i = 0; i < walls.size(); i++) {
						g2d.drawImage(wallIMG, walls.get(i).getX(), walls.get(i).getY(), scaleX, scaleY, this);
					}
					sizeRunner = bricks.size();
					try{
						for (int i = 0; i < sizeRunner; i++) {
							if(i < sizeRunner){
								if(bricks.get(i).getHits() == 3){
									g2d.drawImage(brick3RightIMG, bricks.get(i).getBricker().lastElement().getX(), bricks.get(i).getBricker().lastElement().getY(),bricks.get(i).getBricker().lastElement().getWidth(),scaleY, this);
									g2d.drawImage(brick3LeftIMG, bricks.get(i).getBricker().firstElement().getX(), bricks.get(i).getBricker().firstElement().getY(),bricks.get(i).getBricker().firstElement().getWidth(),scaleY, this);
									for (int j = 1; j < bricks.get(i).getBricker().size()-1; j++) {
										g2d.drawImage(brick3MidIMG, bricks.get(i).getBricker().get(j).getX(), bricks.get(i).getBricker().get(j).getY(), bricks.get(i).getBricker().get(j).getWidth(), scaleY, this);
									}
								}else{
									g2d.drawImage(brick2RightIMG, bricks.get(i).getBricker().lastElement().getX(), bricks.get(i).getBricker().lastElement().getY(),bricks.get(i).getBricker().lastElement().getWidth(),scaleY, this);
									g2d.drawImage(brick2LeftIMG, bricks.get(i).getBricker().firstElement().getX(), bricks.get(i).getBricker().firstElement().getY(),bricks.get(i).getBricker().firstElement().getWidth(),scaleY, this);
									if(bricks.get(i).getHits() == 2){
										for (int j = 1; j < bricks.get(i).getBricker().size()-1; j++) {
											g2d.drawImage(brick2MidIMG, bricks.get(i).getBricker().get(j).getX(), bricks.get(i).getBricker().get(j).getY(), bricks.get(i).getBricker().get(j).getWidth(), scaleY, this);
										}
									}else{
										g2d.drawImage(brick1RightIMG, bricks.get(i).getBricker().lastElement().getX(), bricks.get(i).getBricker().lastElement().getY(),bricks.get(i).getBricker().lastElement().getWidth(),scaleY, this);
										g2d.drawImage(brick1LeftIMG, bricks.get(i).getBricker().firstElement().getX(), bricks.get(i).getBricker().firstElement().getY(),bricks.get(i).getBricker().firstElement().getWidth(),scaleY, this);
										for (int j = 1; j < bricks.get(i).getBricker().size()-1; j++) {
											g2d.drawImage(brick1MidIMG, bricks.get(i).getBricker().get(j).getX(), bricks.get(i).getBricker().get(j).getY(), bricks.get(i).getBricker().get(j).getWidth(), scaleY, this);
										}
									}
								}
							}
							sizeRunner = bricks.size();
						}
					}catch(Exception e){
						
					}

					repaint();
					revalidate();
				}else{
					repaint();
					revalidate();
				}
			}else{

				for (int i = 0; i < powerup.size(); i++) {
					powerup.get(i).stop();
				}
				for (int i = 0; i < bolas.size(); i++) {
					bolas.get(i).stop();
				}
				for (int i = 0; i < plasma.size(); i++) {
					plasma.get(i).stop();
				}
				player.stop();
				gunCycler.stop();
				walls.removeAllElements();
				bricks.removeAllElements();
				powerup.removeAllElements();
				bolas.removeAllElements();
				plasma.removeAllElements();
				playerFound = 0;
				brickFound = 0;
				spawn = 0;

				gameover = 0;
				lives = 3;
				loadLevel();
				bolas.add(new Ball(player.getX()+player.getWidth()/3, player.getY()-15, 2, 1));
				repaint();
				revalidate();
				requestFocus();


			}
		}catch(Exception e){
			paint(g);
		}
		
	}

	public static int getScaleX() {
		return scaleX;
	}

	public static void setScaleX(int scaleX) {
		Game.scaleX = scaleX;
	}

	public static int getScaleY() {
		return scaleY;
	}

	public static void setScaleY(int scaleY) {
		Game.scaleY = scaleY;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Rectangle rectTemp = new Rectangle(player.getX(), player.getY(), player.getWidth(), scaleY);
		move = (int) ((arg0.getX() - rectTemp.getCenterX())/scaleX);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	   		case KeyEvent.VK_P:{
	   			if(paused == 0){
	   				paused = 1;
	   			}else paused = 0;
	   		break;
	   		}
	   		case KeyEvent.VK_F11:{
	   			if(fullscreen == 0){
	   				width = (int) screenSize.getWidth();
	   				height = (int) screenSize.getHeight();
	   				scaleX = width/defaultWidth;
	   				scaleY = height/defaultHeight;
	   				
	   				Main.window.setBounds(0,0,width,height);
	   				Main.window.setVisible(false);
	   				Main.window.dispose();
	   				Main.window.setUndecorated(true);
	   				Main.window.setVisible(true);
	   				setBounds(0, 0, width, height);
	   				requestFocus();
	   				fullscreen = 1;
	   				
	   				player.setX((player.getX()/defaultScaleX)*scaleX);
	   				player.setY((player.getY()/defaultScaleY)*scaleY);
	   				player.setWidth((player.getWidth()/defaultScaleX)*scaleX);
	   				
	   				for (int i = 0; i < bolas.size(); i++) {
	   					bolas.get(i).setX((bolas.get(i).getX()/defaultScaleX)*scaleX);
	   					bolas.get(i).setY((bolas.get(i).getY()/defaultScaleY)*scaleY);
	   				}
	   				
	   				for (int i = 0; i < walls.size(); i++) {
	   					walls.get(i).setX((walls.get(i).getX()/defaultScaleX)*scaleX);
	   					walls.get(i).setY((walls.get(i).getY()/defaultScaleY)*scaleY);
	   				}
	   				
	   				for (int i = 0; i < bricks.size(); i++) {
	   					//bricks.get(i).setX((bricks.get(i).getX()/defaultScaleX)*scaleX);
	   					//bricks.get(i).setY((bricks.get(i).getY()/defaultScaleY)*scaleY);
	   				}
	   			}else{
	   				
	   				player.setX((player.getX()/scaleX)*defaultScaleX);
	   				player.setY((player.getY()/scaleY)*defaultScaleY);
	   				player.setWidth((player.getWidth()/scaleX)*defaultScaleX);
	   				
	   				for (int i = 0; i < bolas.size(); i++) {
	   					bolas.get(i).setX((bolas.get(i).getX()/scaleX)*defaultScaleX);
	   					bolas.get(i).setY((bolas.get(i).getY()/scaleY)*defaultScaleY);
	   				}
	   				
	   				for (int i = 0; i < walls.size(); i++) {
	   					walls.get(i).setX((walls.get(i).getX()/scaleX)*defaultScaleX);
	   					walls.get(i).setY((walls.get(i).getY()/scaleY)*defaultScaleY);
	   				}
	   				
	   				for (int i = 0; i < bricks.size(); i++) {
	   					//bricks.get(i).setX((bricks.get(i).getX()/scaleX)*defaultScaleX);
	   					//bricks.get(i).setY((bricks.get(i).getY()/scaleY)*defaultScaleY);
	   				}
	   				width = defaultWidth;
	   				height = defaultHeight;
	   				scaleX = defaultScaleX;
	   				scaleY = defaultScaleY;
	   				
	   				Main.window.setBounds(0,0,(int)(scaleX*width+6),(int)(scaleY*(height+1.9)));
	   				Main.window.setVisible(false);
	   				Main.window.dispose();
	   				Main.window.setUndecorated(false);
	   				Main.window.setVisible(true);
	   				setBounds(0, 0, width, height);
	   				fullscreen = 0;
	   			}
	   			break;
	   		}
	   		case KeyEvent.VK_ESCAPE:{
	   			System.exit(1);
	   			break;
	   		}
	   		case KeyEvent.VK_SPACE:{
	   			spawn = 1;
	   			if(player.getGunActive() == 1){
	   	   			gunCycler.start();
	   			}
	   			try{
	   				for (int i = 0; i < bolas.size(); i++) {
			        	bolas.get(i).start();
					}
					for (int i = 0; i < plasma.size(); i++) {
							plasma.get(i).resume();
					}
					for (int i = 0; i < powerup.size(); i++) {
							powerup.get(i).resume();
					}
	   			}catch(Exception arg0){
		   			
	   			}
	   			break;
	   		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case(KeyEvent.VK_SPACE):{
				gunCycler.stop();
				lastFired = -1;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static int getRightFlag() {
		return rightFlag;
	}

	public static void setRightFlag(int rightFlag) {
		Game.rightFlag = rightFlag;
	}

	public static int getLeftFlag() {
		return leftFlag;
	}

	public static void setLeftFlag(int leftFlag) {
		Game.leftFlag = leftFlag;
	}

	public static int getPaused() {
		return paused;
	}

	public static void setPaused(int paused) {
		Game.paused = paused;
	}

	public static int getGameover() {
		return gameover;
	}

	public static void setGameover(int gameover) {
		Game.gameover = gameover;
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		Game.lives = lives;
	}
}