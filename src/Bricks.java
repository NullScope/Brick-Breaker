import java.util.Vector;

public class Bricks {

	int power;
	int hits;
	Vector<Bricker> bricker = new Vector<Bricker>();
	
	public Bricks(int hits) {
		super();
		this.hits = hits;
	}

	public Vector<Bricker> getBricker() {
		return bricker;
	}
	
	public void setBricker(Vector<Bricker> bricks) {
		this.bricker = bricks;
	}

	public void addBricker(int x, int y, int width) {
		this.bricker.add(new Bricker(x, y, width));
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
