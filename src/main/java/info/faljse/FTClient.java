package info.faljse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class FTClient implements Runnable {
	private Thread t;
	private volatile boolean _running = true;
	private HashMap<Integer, Coords> coords = new HashMap<>();
	private ITrackData listener;
	private final boolean fake=false;
	Random r=new java.util.Random();

	public FTClient() {

	}

	public void start() {
		t = new Thread(this);
		t.start();
	}

	void stop() {
		_running = false;
	}

	public HashMap<Integer, Coords> getCoords() {
		synchronized (coords) {
			HashMap<Integer, Coords> clone = new HashMap<Integer, Coords>(coords);
			return clone; // return flat copy of coords for thread safety
		}
	}


	private void fakeCoords(){
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int id = r.nextInt(10);
		float x = r.nextFloat()*10;
		float y = r.nextFloat()*10;
		float z = r.nextFloat()*10;
		float rx = (float) (r.nextFloat()*Math.PI);
		float ry = (float) (r.nextFloat()*Math.PI);
		float rz = (float) (r.nextFloat()*Math.PI);
		synchronized (coords) {
			Coords c = coords.get(id);
			long currentTime = System.currentTimeMillis();
			if (c != null) {
				Coords.apply(c, false, id, x, y, z, rx, ry, rz, currentTime);
			} else {
				c = new Coords();
				Coords.apply(c, false, id, x, y, z, rx, ry, rz, currentTime);
				coords.put(id, c);
			}
			Iterator<Coords> itr = coords.values().iterator();
			while (itr.hasNext()) {
				Coords coord = itr.next();
				if (coord.timestampMS + 2000 < currentTime)
					itr.remove();
			}
			ITrackData l = listener;
			if(l!=null) l.received(c);
		}
	}

	private void readCoords() {
		ServerSocket welcomeSocket = null;
		try {

			welcomeSocket = new ServerSocket(1956);
			while (_running) {
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				while (_running) {
					String line = inFromClient.readLine();
					if (line == null)
						break;
					if(line.length() == 0)
						continue;
					if (line.startsWith("info"))
						continue;
					String[] parts = line.split(" ");
					boolean cam= Boolean.parseBoolean(parts[0]);
					int id = Integer.parseInt(parts[1]);
					float x = Float.parseFloat(parts[2]);
					float y = Float.parseFloat(parts[3]);
					float z = Float.parseFloat(parts[4]);
					float rx = Float.parseFloat(parts[5]);
					float ry = Float.parseFloat(parts[6]);
					float rz = Float.parseFloat(parts[7]);

					synchronized (coords) {
						Coords c = coords.get(id);
						long currentTime = System.currentTimeMillis();
						if (c != null) {
							Coords.apply(c, cam, id, x, y, z, rx, ry, rz, currentTime);
						} else {
							c = new Coords();
							Coords.apply(c, cam, id, x, y, z, rx, ry, rz, currentTime);
							coords.put(id, c);
						}
						Iterator<Coords> itr = coords.values().iterator();
						while (itr.hasNext()) {
							Coords coord = itr.next();
							if (coord.timestampMS + 2000 < currentTime)
								itr.remove();
						}
						ITrackData l = listener;
						if (l != null) l.received(c);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			if (welcomeSocket != null)
				try {
					welcomeSocket.close();
				} catch (IOException e) {
				}
		}
	}

	@Override
	public void run() {
		while (_running) {
			if(fake==true) fakeCoords();
			else readCoords();
		}
	}

	public void onData(ITrackData listener) {
		this.listener=listener;
		
	}

}
