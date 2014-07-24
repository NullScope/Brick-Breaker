import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends Thread{
	javax.swing.Timer gunClock;
	private int x;
	private int y;
	private int defaultWidth;
	private int width;
	private int hit = 0;
	private int direction = 0;
	private int ballSize = 0;
	private int gunLX;
	private int gunRX;
	private int gunLY;
	private int gunRY;
	private int gunCycle = 0;
	private int gunActive = 0;
	private int type = 0;
	private int sizeRunner;
	
	private Rectangle playerRect;
	
	
	public Player(int x, int y, int defaultWidth) {
		super();
		this.x = x;
		this.y = y;
		this.defaultWidth = defaultWidth;
		this.width = this.defaultWidth;
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		while(true){
			if(Game.getPaused() == 0){
				for (int i = 0; i < Game.bolas.size(); i++) {
					if(Game.bolas.get(i).getX() == 0 || Game.bolas.get(i).getX() == Game.width*Game.getScaleX() || Game.bolas.get(i).getY() == 0 || Game.bolas.get(i).getY() == Game.height*Game.getScaleY() ||Game.bolas.get(i).getY() > y+15){
						Game.bolas.get(i).stop();
						Game.bolas.remove(i);
					}
				}
				for (int i = 0; i < Game.powerup.size(); i++) {
					if(Game.powerup.get(i).getY() > y+15){
						Game.powerup.get(i).stop();
						Game.powerup.remove(i);
						
					}
				}
				playerRect = new Rectangle(x, y, width, Game.getScaleY());
				for (int i = 0; i < Game.powerup.size(); i++) {
					Rectangle tempRect = new Rectangle(Game.powerup.get(i).getX(), Game.powerup.get(i).getY(), Game.getScaleX(), Game.getScaleY());
					checkPowerCollision(tempRect, i);
				}
				playerRect = new Rectangle(x-15, y, width+30, Game.getScaleY());
				for (int i = 0; i < Game.walls.size(); i++) {	
					Rectangle tempRect = new Rectangle(Game.walls.get(i).getX(), Game.walls.get(i).getY(), Game.getScaleX(), Game.getScaleY());
					checkWallCollision(playerRect, tempRect);
				}
				
				playerRect = new Rectangle(x, y, width, Game.getScaleY());
				
				for (int i = 0; i < Game.plasma.size(); i++) {
					type = 1;
					
					Rectangle plasmaRect = new Rectangle(Game.plasma.get(i).getX(), Game.plasma.get(i).getY(), 6, 10);
					for (int j = 0; j < Game.walls.size(); j++) {
						Rectangle tempRect = new Rectangle(Game.walls.get(j).getX(), Game.walls.get(j).getY(), Game.getScaleX(), Game.getScaleY());
						checkBrickCollision(plasmaRect, tempRect, j, i);
					}
					
					type = 2;
					sizeRunner = Game.bricks.size();
					for (int j = 0; j < sizeRunner; j++) {
						if(j < sizeRunner){
							Rectangle tempRect = new Rectangle(Game.bricks.get(j).getBricker().firstElement().getX(),Game.bricks.get(j).getBricker().firstElement().getY(), Game.bricks.get(j).getBricker().size()*Game.getScaleX(), Game.getScaleY());
							checkBrickCollision(plasmaRect, tempRect, j, i);
						}
						sizeRunner = Game.bricks.size();
					}
				}
				
				guns();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void checkBrickCollision(Rectangle plasmaRect, Rectangle tempRect, int brickID, int plasmaID) {
		if(plasmaRect.intersects(tempRect)){
			if(type == 2){
				Game.bricks.get(brickID).setHits(Game.bricks.get(brickID).getHits()-1);
				if(Game.bricks.get(brickID).getHits() == 0){
					if(Game.bricks.get(brickID).getPower() > 0){
						Game.powerup.add(new Power(Game.plasma.get(plasmaID).getX(), Game.plasma.get(plasmaID).getY(), Game.bricks.get(brickID).getPower(), 20));
						Game.powerup.lastElement().start();
					}
					Game.bricks.remove(brickID);
				}
				Game.plasma.get(plasmaID).stop();
				Game.plasma.remove(plasmaID);
			}else{
				Game.plasma.get(plasmaID).stop();
				Game.plasma.remove(plasmaID);
			}
		}
	}
	
	private void checkWallCollision(Rectangle playerRect, Rectangle tempRect) {
		if(playerRect.intersects(tempRect)){
			if(playerRect.getX() < tempRect.getCenterX()){
				Game.setRightFlag(1);
				Game.setLeftFlag(0);
			}else{
				if(playerRect.getX() > tempRect.getCenterX()){
				Game.setLeftFlag(1);
				Game.setRightFlag(0);
				}
			}
		}
	}
	
	private void checkPowerCollision(Rectangle tempRect, int ID) {
		ballSize = Game.bolas.size();
		if(playerRect.intersects(tempRect)){
			for (int i = 0; i < ballSize; i++) {
				switch (Game.powerup.get(ID).getPower()) {
				case(1):{
					if(ballSize == 1){
						Game.bolas.add(new Ball(Game.bolas.get(i).getX(), Game.bolas.get(i).getY(), 1, 1));
						Game.bolas.lastElement().start();
						Game.bolas.add(new Ball(Game.bolas.get(i).getX(), Game.bolas.get(i).getY(), 2, 1));
						Game.bolas.lastElement().start();
					}
				break;
				}
				case(2):{
					gunActive = 1;
					gunClock = new javax.swing.Timer(Game.powerup.get(ID).getDuration()*1000, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							gunActive = 0;
							gunClock.stop();
						}
					});
					gunClock.start();
				break;
				}
				case(3):{
					for (int j = 0; j < Game.bolas.size(); j++) {
						Game.bolas.get(j).setPassable(1);
					}
					gunClock = new javax.swing.Timer(Game.powerup.get(ID).getDuration()*1000, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							for (int j = 0; j < Game.bolas.size(); j++) {
								Game.bolas.get(j).setPassable(0);
							}
						}
					});
					gunClock.start();
				break;
				}
				case(4):{
					if(this.width <= this.defaultWidth){
						this.width = this.width+30;
					}
				break;
				}
				case(5):{
					if(this.width != this.defaultWidth/2){
						this.width = this.defaultWidth/2;
					}
				break;
				}
				case(6):{
					Game.setLives(Game.getLives()+1);
				break;
				}
			}
			
			}
			Game.powerup.remove(ID);
		}
	}
	public void guns(){
		if(gunActive == 1){
			gunLX = x-10;
			gunLY = y;
			gunRX = x+width;
			gunRY = y;
		}
	}
	
	public int getGunLX() {
		return gunLX;
	}

	public void setGunLX(int gunLX) {
		this.gunLX = gunLX;
	}

	public int getGunRX() {
		return gunRX;
	}

	public void setGunRX(int gunRX) {
		this.gunRX = gunRX;
	}

	public int getGunLY() {
		return gunLY;
	}

	public void setGunLY(int gunLY) {
		this.gunLY = gunLY;
	}

	public int getGunRY() {
		return gunRY;
	}

	public void setGunRY(int gunRY) {
		this.gunRY = gunRY;
	}

	public int getGunCycle() {
		return gunCycle;
	}

	public void setGunCycle(int gunCycle) {
		this.gunCycle = gunCycle;
	}

	public int getGunActive() {
		return gunActive;
	}

	public void setGunActive(int gunActive) {
		this.gunActive = gunActive;
	}

	public int getDirection() {
		return direction;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}


	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.defaultWidth = width;
	}

	public int getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(int defaultWidth) {
		this.defaultWidth = defaultWidth;
	}
	
	
}
