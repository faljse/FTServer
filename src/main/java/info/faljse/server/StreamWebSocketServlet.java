package info.faljse.server;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class StreamWebSocketServlet extends WebSocketServlet implements WebSocketCreator
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(StreamWebSocketServlet.class);
    private StreamWebSocket mySocket;
	private String id;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        id = config.getInitParameter("id");
        mySocket=new StreamWebSocket(id);
    }
    
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.setCreator(this);
		logger.info("configure " + getClass().toString());
        // set a 10 second timeout
        factory.getPolicy().setIdleTimeout(10000);
	}

	@Override
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        for (String subProtocol : req.getSubProtocols())
        {
            if ("binary".equals(subProtocol))
            {
                resp.setAcceptedSubProtocol(subProtocol);
            }
            if ("text".equals(subProtocol))
            {
                resp.setAcceptedSubProtocol(subProtocol);
            }
        }
        resp.setAcceptedSubProtocol("null");
        logger.info("WebSocket subProtocol: " + req.getSubProtocols());
		return mySocket;
	}
}
