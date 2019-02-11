package info.faljse.server;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;


public class ClientSession {
    private final static Logger logger = LoggerFactory.getLogger(ClientSession.class);
    private final Session session;
    private ByteBuffer byteBuffer;
    private int clientID;
    private volatile boolean alive=true;



    public ClientSession(Session session, byte[] buffer, int clientID) {
        this.session=session;
        byteBuffer=ByteBuffer.wrap(buffer);
        this.clientID=clientID;
        logger.info("new clientSession, id: " + clientID);
    }

    public Session getSession() {
        return session;
    }

    public void send() {
        if(!alive)
            return;
        byteBuffer.rewind();
        try {
        	session.getRemote().sendBytesByFuture(byteBuffer);
        }catch(Exception e){
            alive=false;
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
