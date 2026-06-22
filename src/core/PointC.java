package core;

public class PointC {

	private int id;
	private int x;
	private int y;
	
	public PointC() {}
	
	public PointC(int id, int y, int x) {
		this.id = id;
		this.y = y;
		this.x = x;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "ID:" + id + " " + x + "x" + y;
	}
}
