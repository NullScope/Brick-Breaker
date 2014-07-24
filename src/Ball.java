import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ball extends Thread{
	private int x;
	private int y;
	private int dx = 1;
	private int dy = 1;
	private int speed = 2;
	private int angle;
	private int direction;
	private int diff;
	private int sector;
	private int detected;
	private int typeHit;
	private int type;
	private int brickHit;
	private int brickRunner;
	private int sizeRunner;
	private int passable = 0;
	
	Rectangle bolaRect;
	javax.swing.Timer clock;
	
	public Ball(int x, int y, int angle, int direction) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.direction = direction;
		clock = new javax.swing.Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.player.setHit(0);
				clock.stop();
			}
		});
	}
	
	public void run(){
		while(true){
			if (Game.getPaused() == 0) {
				if(this.direction==1){
					this.y=this.y-this.dy;
					
					if(this.angle==1){
						this.x = this.x-this.dx;
					}else{
						this.x = this.x+this.dx;
					}
				}else{
					this.y=this.y+this.dy;
					
					if(this.angle==1){
						this.x = this.x-this.dx;
					}else{
						this.x = this.x+this.dx;
					}
				}
				bolaRect = new Rectangle(x, y, 15, 15);
				if(x <= 15 && y <= 15){
					if(direction == 1){
						direction = 2;
					}else{
						direction = 1;
					}
					
					if(angle == 1){
						angle = 2;
					}else{
						angle = 1;
					}
				}else{
					if(x >= Game.width*Game.getScaleX()-15 && y <=15){
						if(direction == 1){
							direction = 2;
						}else{
							direction = 1;
						}
						
						if(angle == 1){
							angle = 2;
						}else{
							angle = 1;
						}
					}else{
						if(x >= Game.width*Game.getScaleX()-15 && y >= Game.height*Game.getScaleY()-15){
							if(direction == 1){
								direction = 2;
							}else{
								direction = 1;
							}
							
							if(angle == 1){
								angle = 2;
							}else{
								angle = 1;
							}
						}else{
							if(x <= 15 && y >= Game.height*Game.getScaleY()-15){
								if(direction == 1){
									direction = 2;
								}else{
									direction = 1;
								}
								
								if(angle == 1){
									angle = 2;
								}else{
									angle = 1;
								}
							}else{
								if(x <= 15){
									if(angle == 1){
										angle = 2;
									}else{
										angle = 1;
									}
								}else{
									if(y <= 15){
										if(direction == 1){
											direction = 2;
										}else{
											direction = 1;
										}
									}else{
										if(x >= Game.width*Game.getScaleX()-15){
											if(angle == 1){
												angle = 2;
											}else{
												angle = 1;
											}
										}else{
											if(y >= Game.height*Game.getScaleY()-15){
												if(direction == 1){
													direction = 2;
												}else{
													direction = 1;
												}
											}else{
												type = 1;
												sizeRunner = Game.walls.size();
												for (brickRunner = 0; brickRunner < sizeRunner; brickRunner++) {
													Rectangle tempRect = new Rectangle(Game.walls.get(brickRunner).getX(), Game.walls.get(brickRunner).getY(), Game.getScaleX(), Game.getScaleY());
													if(brickRunner < sizeRunner){
														checkBallCollision(tempRect, brickRunner);
													}
													sizeRunner = Game.walls.size();
												}
											}
										}
									}
								}
							}
						}
					}
				}
				type = 2;
				sizeRunner = Game.bricks.size();
				for (brickRunner = 0; brickRunner < sizeRunner; brickRunner++) {
					if(brickRunner < sizeRunner){
						Rectangle tempRect = new Rectangle(Game.bricks.get(brickRunner).getBricker().firstElement().getX(),Game.bricks.get(brickRunner).getBricker().firstElement().getY(), Game.bricks.get(brickRunner).getBricker().size()*Game.getScaleX(), Game.getScaleY());
						checkBallCollision(tempRect, brickRunner);
					}
					sizeRunner = Game.bricks.size();
				}

				checkPlayerCollision();
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void checkPlayerCollision() {
		Rectangle playerRect = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.getScaleY());
		if(bolaRect.intersects(playerRect)){
			Game.player.setHit(1);
			clock.start();
			Rectangle interRect = bolaRect.intersection(playerRect);
			//System.out.println("X SIZE: "+(int) (interRect.getMaxX()-interRect.getMinX())+" Y SIZE: "+(int) (interRect.getMaxY()-interRect.getMinY()));
			//System.out.println("bola Y: "+bolaRect.getY()+" wall Y: "+tempRect.getCenterY());
			if((int)(interRect.getMaxX()-interRect.getMinX()) > (int)(interRect.getMaxY()-interRect.getMinY())){
				
				if(bolaRect.getY() > playerRect.getCenterY()){
					//System.out.println("BATEU EM BAIXO");

					direction = 2;
				}else{
					//System.out.println("BATEU EM CIMA");
					
					direction = 1;
				}
			}
			sector = (int) ((playerRect.getMaxX()-playerRect.getMinX())/22);
			for (int i = 0; i < 22; i++) {
				diff = sector*i;
				Rectangle tempRect = new Rectangle((int)(playerRect.getMinX()+diff), (int)(playerRect.getY()), 15, 15);
				if(bolaRect.intersects(tempRect)){
					switch(i){
						case(0):{
							dx = 6;
							dy = 1;
							speed = 11;
						break;
						}
						case(1):{
							dx = 5;
							dy = 1;
							speed = 9;
						break;
						}
						case(2):{
							
							dx = 4;
							dy = 1;
							speed = 8;
						break;
						}
						case(3):{
							
							dx = 3;
							dy = 1;
							speed = 6;
						break;
						}
						case(4):{
							
							dx = 2;
							dy = 1;
							speed = 5;
						break;
						}
						case(5):{
							
							dx = 1;
							dy = 1;
							speed = 3;
						break;
						}
						case(6):{
							
							dx = 1;
							dy = 2;
							speed = 5;
						break;
						}
						case(7):{
							
							dx = 1;
							dy = 3;
							speed = 6;
						break;
						}
						case(8):{
							
							dx = 1;
							dy = 4;
							speed = 8;
						break;
						}
						case(9):{
							
							dx = 1;
							dy = 5;
							speed = 9;
						break;
						}
						case(10):{
							
							dx = 1;
							dy = 6;
							speed = 11;
						break;
						}
						case(11):{
							
							dx = 1;
							dy = 6;
							speed = 11;
						break;
						}
						case(12):{
							
							dx = 1;
							dy = 5;
							speed = 9;
						break;
						}
						case(13):{
							
							dx = 1;
							dy = 4;
							speed = 8;
						break;
						}
						case(14):{
							
							dx = 1;
							dy = 3;
							speed = 6;
						break;
						}
						case(15):{
							
							dx = 1;
							dy = 2;					
							speed = 5;
						break;
						}
						case(16):{
							
							dx = 1;
							dy = 1;
							speed = 3;
						break;
						}
						case(17):{
							
							dx = 2;
							dy = 1;
							speed = 5;
						break;
						}
						case(18):{
							
							dx = 3;
							dy = 1;
							speed = 6;
						break;
						}
						case(19):{
							
							dx = 4;
							dy = 1;
							speed = 8;
						break;
						}
						case(20):{
							
							dx = 5;
							dy = 1;
							speed = 9;
						break;
						}
						case(21):{
							
							dx = 6;
							dy = 1;
							speed = 11;
						break;
						}
					}
				}
			}
		}
	}
	
	private void checkBallCollision(Rectangle tempRect, int ID) {
		if(detected == 0){
			if(bolaRect.intersects(tempRect)){
				detected = 1;
				brickHit = ID;
				typeHit = type;
				if(type == 2){
					Game.bricks.get(ID).setHits(Game.bricks.get(ID).getHits()-1);
					if(Game.bricks.get(ID).getHits() == 0 || passable == 1){
						if(Game.bricks.get(ID).getPower() > 0){
							Game.powerup.add(new Power(x, y, Game.bricks.get(ID).getPower(), 20));
							Game.powerup.lastElement().start();
						}
						detected = 0;
						Game.bricks.remove(ID);
						brickRunner = 0;
					}
				}
				if(passable == 0 || type == 1){
					Rectangle interRect = bolaRect.intersection(tempRect);
					//System.out.println("X SIZE: "+(int) (interRect.getMaxX()-interRect.getMinX())+" Y SIZE: "+(int) (interRect.getMaxY()-interRect.getMinY()));
					//System.out.println("bola Y: "+bolaRect.getY()+" wall Y: "+tempRect.getCenterY());
					if(interRect.getWidth() < interRect.getHeight()){
						if(bolaRect.getX() > tempRect.getCenterX()){
							System.out.println("BATEU A DIREITA");
							angle = 2;
						}else{
							System.out.println("BATEU A ESQUERDA");
							angle = 1;
						}
					}else{
						if(interRect.getWidth() > interRect.getHeight()){
							if(bolaRect.getY() > tempRect.getCenterY()){
								System.out.println("BATEU EM BAIXO");
								if(direction == 1){
									direction = 2;
								}else{
									if(angle == 1){
										angle = 2;
									}else{
										angle = 1;
									}
								}
							}else{
								System.out.println("BATEU EM CIMA");
								if(direction == 2){
									direction = 1;
								}else{
									if(angle == 1){
										angle = 2;
									}else{
										angle = 1;
									}
								}
							}
						}else{
							if(interRect.getWidth() == interRect.getHeight()){
								if(bolaRect.getX() > tempRect.getCenterX() && bolaRect.getY() > tempRect.getCenterY()){
									System.out.println("BATEU EM BAIXO E A DIREITA");	
									angle = 2;
								}
								if(bolaRect.getX() < tempRect.getCenterX() && bolaRect.getY() > tempRect.getCenterY()){
									System.out.println("BATEU EM BAIXO E A ESQUERDA");
									angle = 1;
								}
								if(bolaRect.getX() > tempRect.getCenterX() && bolaRect.getY() < tempRect.getCenterY()){
									System.out.println("BATEU EM CIMA E A DIREITA");
									angle = 2;
								}
								if(bolaRect.getX() < tempRect.getCenterX() && bolaRect.getY() < tempRect.getCenterY()){
									System.out.println("BATEU EM CIMA E A ESQUERDA");		
									angle = 1;
								}
							}
						}
					}
				}
			}
		}else{
			if(typeHit == 1){
				tempRect = new Rectangle(Game.walls.get(brickHit).getX(),Game.walls.get(brickHit).getY(), Game.getScaleX(), Game.getScaleY());
			}else{
				tempRect = new Rectangle(Game.bricks.get(brickHit).getBricker().firstElement().getX(),Game.bricks.get(brickHit).getBricker().firstElement().getY(), Game.bricks.get(brickHit).getBricker().size()*Game.getScaleX(), Game.getScaleY());
			}
			if(!(bolaRect.intersects(tempRect))){
				detected = 0;
			}
		}
	}
	
	public int getPassable() {
		return passable;
	}

	public void setPassable(int passable) {
		this.passable = passable;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
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
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
