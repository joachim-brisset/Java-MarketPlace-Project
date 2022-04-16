package marketplace.utils;

public class Dimension {
	private int x;
	private int y;
	private int z;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		if (x<=0) throw new IllegalArgumentException("Dimension must be positive");
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		if (y<=0) throw new IllegalArgumentException("Dimension must be positive");
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
		if (z<=0) throw new IllegalArgumentException("Dimension must be positive");
	}
	public Dimension(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
