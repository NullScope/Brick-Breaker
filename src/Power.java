
public class Power extends Thread{
	int x;
	int y;
	int power;
	int duration;
	
	public Power(int x, int y, int power, int duration) {
		super();
		this.x = x;
		this.y = y;
		this.power = power;
		this.duration = duration;
	}
	
	public void run(){
		while(true){
			if(Game.getPaused()== 0){
				this.y += 1;
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	
	
}
