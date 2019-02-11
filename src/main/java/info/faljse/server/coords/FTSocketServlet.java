package info.faljse.server.coords;

import org.eclipse.jetty.websocket.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class FTSocketServlet extends WebSocketServlet implements WebSocketCreator
{
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(FTSocketServlet.class);
    private FTSocket mySocket;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mySocket=new FTSocket();
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
