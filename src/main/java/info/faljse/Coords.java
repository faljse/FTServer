package info.faljse;

public class Coords {
	public boolean cam;
	public int id;
	public float x;
	public float y;
	public float z;
	public float rx;
	public float ry;
	public float rz;
	public long timestampMS;
	
	public static void apply(Coords c, boolean cam, int id, float x, float y, float z, float rx, float ry, float rz, long ts) {
		c.cam=cam;
		c.id=id;
		c.x=x;
		c.y=y;
		c.z=z;
		c.rx=rx;
		c.ry=ry;
		c.rz=rz;
		c.timestampMS=ts;
	}

}
