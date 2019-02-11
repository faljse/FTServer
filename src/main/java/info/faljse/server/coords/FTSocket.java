package info.faljse.server.coords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import info.faljse.server.InputStreamServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTSocket implements WebSocketListener
{
	private static List<Session> sockets=new ArrayList<>();
	private final static Logger logger = LoggerFactory.getLogger(InputStreamServlet.class);

	public static void send(String msg) {
		synchronized (sockets) {

			Iterator<Session> itr = sockets.iterator();
			while (itr.hasNext()) {
				Session s = itr.next();
				try {
					s.getRemote().sendStringByFuture(msg);
				} catch (Exception e) {
					itr.remove();
					System.out.println("remove session " + e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void onWebSocketClose(int arg0, String arg1) {
    	System.out.println("close");
	}

	@Override
	public void onWebSocketConnect(Session arg0) {
		logger.info("new websocket session: " + arg0.getRemoteAddress().toString());
		synchronized (sockets) {
			sockets.add(arg0);	
		}
	}

	@Override
	public void onWebSocketError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
    	System.out.println("onWebSocketBinary"+arg0);
	}

	@Override
	public void onWebSocketText(String arg0) {
    	System.out.println("onWebSocketText"+arg0);
	}
    
    
}