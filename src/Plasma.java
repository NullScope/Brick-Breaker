import java.awt.Rectangle;


public class Plasma extends Thread{
	int x;
	int y;
	Rectangle plasmaRect;

	public Plasma(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void run(){
		while(true){
			if(Game.getPaused() == 0 && Game.getGameover() == 0){
				this.y = this.y-1;
				plasmaRect = new Rectangle(this.x, this.y, 6, 10);
				/*for (int i = 0; i < Game.bricks.size(); i++) {
					Rectangle tempRect = new Rectangle(Game.bricks.get(i).getBricker().firstElement().getX(),Game.bricks.get(i).getBricker().firstElement().getY(), Game.bricks.get(i).getBricker().size()*Game.getScaleX(), Game.getScaleY());
					//checkBrickCollision(tempRect, i);
				}*/
			}
			
			try {
				sleep(5);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}

	/*private void checkBrickCollision(Rectangle tempRect, int ID) {
		if(plasmaRect.intersects(tempRect)){
			Game.bricks.get(ID).setHits(Game.bricks.get(ID).getHits()-1);
			if(Game.bricks.get(ID).getHits() == 0){
				if(Game.bricks.get(ID).getPower() > 0){
					Game.powerup.add(new Power(x, y, Game.bricks.get(ID).getPower(), 20));
					Game.powerup.lastElement().start();
					System.out.println(Game.powerup.size());
				}
			
			}
			Game.bricks.remove(ID);
		}
	}*/

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
}
