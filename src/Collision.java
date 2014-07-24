import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class Collision extends Thread{
	javax.swing.Timer clock;
	Vector<Ball> bolas = new Vector<>();
	Vector<Wall> walls = new Vector<>();
	Vector<Bricks> bricks = new Vector<>();
	Vector<Power> powerup = new Vector<>();
	Player player;
	int scaleX;
	int scaleY;
	int diff;
	int sector;
	int type;
	int detected = 0;
	int brickHit = 0;
	int typeHit = 0;
	int k = 0;
	int j = 0;
	
	public Collision(Vector<Ball> bolas, Vector<Wall> walls,Vector<Bricks> bricks,Vector<Power> powerup,final Player player, int scaleX, int scaleY) {
		super();
		this.bolas = bolas;
		this.walls = walls;
		this.bricks = bricks;
		this.player = player;
		this.powerup = powerup;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		clock = new javax.swing.Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.setHit(0);
				clock.stop();
			}
		});
	}

	public void run(){
		while(true && Game.getPaused() == 0){
			
			for (int i = 0; i < bolas.size(); i++) {
				Rectangle bolaRect = new Rectangle(bolas.get(i).getX(), bolas.get(i).getY(), 15, 15);
				
				Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getWidth(), scaleY);
				sector = (int) ((playerRect.getMaxX()-playerRect.getMinX())/24);
				checkPlayerCollision(bolaRect, playerRect, i);
				
				for (int j = 0; j < powerup.size(); j++) {
					Rectangle tempRect = new Rectangle(powerup.get(j).getX(), powerup.get(j).getY(), scaleX, scaleY);
					checkPowerCollision(playerRect, tempRect, j);
				}
				type = 1;
				for (j = 0; j < walls.size(); j++) {
					Rectangle tempRect = new Rectangle(walls.get(j).getX(), walls.get(j).getY(), scaleX, scaleY);
					checkBallCollision(bolaRect, tempRect, i, j);
					checkWallCollision(playerRect, tempRect);
				}
				type = 2;
				
				for (k = 0; k < bricks.size(); k++) {
					Rectangle tempRect = new Rectangle();
					tempRect.setLocation(bricks.get(k).getBricker().firstElement().getX(), bricks.get(k).getBricker().firstElement().getY());
					for (j = 0; j < bricks.get(k).getBricker().size(); j++) {
						tempRect.setSize((int) (tempRect.getWidth()+bricks.get(k).getBricker().get(j).getWidth()), scaleY);
					}
					checkBallCollision(bolaRect, tempRect, i, k);
				}
				
				
			}
		}
	}

	private void checkPowerCollision(Rectangle playerRect, Rectangle tempRect, int ID) {
		if(playerRect.intersects(tempRect)){
			switch (powerup.get(ID).getPower()) {
			case(1):{
				bolas.add(new Ball(bolas.get(ID).getX(), bolas.get(ID).getY(), 1, 1));
				bolas.lastElement().start();
				bolas.add(new Ball(bolas.get(ID).getX(), bolas.get(ID).getY(), 2, 1));
				bolas.lastElement().start();
			break;
			}
			}
			powerup.remove(ID);
		}
	}

	private void checkWallCollision(Rectangle playerRect, Rectangle tempRect) {
		if(playerRect.intersects(tempRect)){
			if(playerRect.getX() < tempRect.getCenterX()){
				Game.setRightFlag(1);
				Game.setLeftFlag(0);
			}else{
				Game.setLeftFlag(1);
				Game.setRightFlag(0);
			}
		}
	}

	private void checkPlayerCollision(Rectangle bolaRect, Rectangle playerRect,int currentBall) {
		if(bolaRect.intersects(playerRect)){
			player.setHit(1);
			clock.start();
			Rectangle interRect = bolaRect.intersection(playerRect);
			//System.out.println("X SIZE: "+(int) (interRect.getMaxX()-interRect.getMinX())+" Y SIZE: "+(int) (interRect.getMaxY()-interRect.getMinY()));
			//System.out.println("bola Y: "+bolaRect.getY()+" wall Y: "+tempRect.getCenterY());
			if((int)(interRect.getMaxX()-interRect.getMinX()) > (int)(interRect.getMaxY()-interRect.getMinY())){
				
				if(bolaRect.getY() > playerRect.getCenterY()){
					//System.out.println("BATEU EM BAIXO");
				
					bolas.get(currentBall).setDirection(2);
				}else{
					//System.out.println("BATEU EM CIMA");
					
					bolas.get(currentBall).setDirection(1);
				}
			}
			for (int i = 0; i < 24; i++) {
				diff = sector*i;
				Rectangle tempRect = new Rectangle((int)(playerRect.getMinX()+diff), (int)(playerRect.getY()), 10, 10);
				if(bolaRect.intersects(tempRect)){
					switch(i){
					//LADO ESQUERDO
						case(0):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(6);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(18);
						break;
						}
						case(1):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(5);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(15);
						break;
						}
						case(2):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(4);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(12);
						break;
						}
						case(3):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(3);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(10);
						break;
						}
						case(4):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(2);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(7);
						break;
						}
						case(5):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(5);
						break;
						}
						case(6):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(2);
							bolas.get(currentBall).setSpeed(7);
						break;
						}
						case(7):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(3);
							bolas.get(currentBall).setSpeed(10);
						break;
						}
						case(8):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(4);
							bolas.get(currentBall).setSpeed(13);
						break;
						}
						case(9):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(5);
							bolas.get(currentBall).setSpeed(15);
						break;
						}
						case(10):{
							bolas.get(currentBall).setAngle(1);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(6);
							bolas.get(currentBall).setSpeed(18);
						break;
						}
						//LADO DIREITO
						case(11):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(6);
							bolas.get(currentBall).setSpeed(18);
						break;
						}
						case(12):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(5);
							bolas.get(currentBall).setSpeed(15);
						break;
						}
						
						case(13):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(4);
							bolas.get(currentBall).setSpeed(12);
						break;
						}
						case(14):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(3);
							bolas.get(currentBall).setSpeed(10);
						break;
						}
						case(15):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(2);
							bolas.get(currentBall).setSpeed(7);
						break;
						}
						case(16):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(1);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(5);
						break;
						}
						case(17):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(2);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(7);
						break;
						}
						case(18):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(3);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(10);
						break;
						}
						case(19):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(4);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(12);
						break;
						}
						case(20):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(5);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(15);
						break;
						}
						case(21):{
							bolas.get(currentBall).setAngle(2);
							bolas.get(currentBall).setDx(6);
							bolas.get(currentBall).setDy(1);
							bolas.get(currentBall).setSpeed(18);
						break;
						}
					}
				}
			}
		}

	}

	private void checkBallCollision(Rectangle bolaRect, Rectangle tempRect,int currentBall,int ID) {
		if(detected == 0){
			if(bolaRect.intersects(tempRect)){
				detected = 1;
				brickHit = ID;
				typeHit = type;
				if(type == 2){
					bricks.get(ID).setHits(bricks.get(ID).getHits()-1);
					if(bricks.get(ID).getHits() == 0){
						if(bricks.get(ID).getPower() > 0){
							powerup.add(new Power(bolas.get(currentBall).getX(), bolas.get(currentBall).getY(), bricks.get(ID).getPower(), 30));
							powerup.lastElement().start();
							System.out.println(powerup.size());
						}
						detected = 0;
						bricks.remove(ID);
						k = 0;
						j = 0;
					}
				}
				Rectangle interRect = bolaRect.intersection(tempRect);
				//System.out.println("X SIZE: "+(int) (interRect.getMaxX()-interRect.getMinX())+" Y SIZE: "+(int) (interRect.getMaxY()-interRect.getMinY()));
				//System.out.println("bola Y: "+bolaRect.getY()+" wall Y: "+tempRect.getCenterY());
				if((int)(interRect.getMaxX()-interRect.getMinX()) < (int)(interRect.getMaxY()-interRect.getMinY())){
					if(bolaRect.getX() > tempRect.getCenterX()){
						System.out.println("BATEU A DIREITA");
						bolas.get(currentBall).setAngle(2);
					}else{
						System.out.println("BATEU A ESQUERDA");
						bolas.get(currentBall).setAngle(1);
					}
				}else{
					if((int)(interRect.getMaxX()-interRect.getMinX()) > (int)(interRect.getMaxY()-interRect.getMinY())){
						if(bolaRect.getY() > tempRect.getCenterY()){
							System.out.println("BATEU EM BAIXO");
							bolas.get(currentBall).setDirection(2);
						}else{
							System.out.println("BATEU EM CIMA");
							bolas.get(currentBall).setDirection(1);
						}
						if(bolaRect.getX() > tempRect.getCenterX()){
							System.out.println("BATEU A DIREITA");
							bolas.get(currentBall).setAngle(2);
						}else{
							System.out.println("BATEU A ESQUERDA");
							bolas.get(currentBall).setAngle(1);
						}
					}else{
						if((int)(interRect.getMaxX()-interRect.getMinX()) == (int)(interRect.getMaxY()-interRect.getMinY())){
							if(bolaRect.getX() > tempRect.getCenterX() && bolaRect.getY() > tempRect.getCenterY()){
								System.out.println("BATEU EM BAIXO E A DIREITA");	
								bolas.get(currentBall).setAngle(2);
							}
							if(bolaRect.getX() < tempRect.getCenterX() && bolaRect.getY() > tempRect.getCenterY()){
								System.out.println("BATEU EM BAIXO E A ESQUERDA");
								bolas.get(currentBall).setAngle(1);
							}
							if(bolaRect.getX() > tempRect.getCenterX() && bolaRect.getY() < tempRect.getCenterY()){
								System.out.println("BATEU EM CIMA E A DIREITA");
								bolas.get(currentBall).setAngle(2);
							}
							if(bolaRect.getX() < tempRect.getCenterX() && bolaRect.getY() < tempRect.getCenterY()){
								System.out.println("BATEU EM CIMA E A ESQUERDA");		
								bolas.get(currentBall).setAngle(1);
								bolas.get(currentBall).setDirection(1);
							}
						}
					}
				}
			}
		}else{
			if(typeHit == 1){
				tempRect = new Rectangle(walls.get(brickHit).getX(),walls.get(brickHit).getY(), scaleX, scaleY);
			}else{
				tempRect = new Rectangle(bricks.get(brickHit).getBricker().firstElement().getX(),bricks.get(brickHit).getBricker().firstElement().getY(), bricks.get(brickHit).getBricker().size()*scaleX, scaleY);
			}
			if(!(bolaRect.intersects(tempRect))){
				detected = 0;
			}
		}
	}
}
