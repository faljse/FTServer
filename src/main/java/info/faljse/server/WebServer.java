package info.faljse.server;

import info.faljse.Coords;
import info.faljse.ITrackData;
import info.faljse.FTClient;
import info.faljse.SDNotify.SDNotify;
import info.faljse.server.coords.FTSocket;
import info.faljse.server.coords.FTSocketServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.fusesource.jansi.AnsiConsole;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebServer implements ITrackData {
    public static void main(String... args) {
        AnsiConsole.systemInstall();
        WebServer ws = new WebServer();
        ws.start();
    }

    public static Map<String, WSSessions> list = new HashMap<>();

    public void start() {
        FTClient otc = new FTClient();
        otc.start();
        otc.onData(this);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(Settings.port);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        for (int i = 0; i < Settings.ftCmd.length; i++) {
            String cmd = Settings.ftCmd[i];
            if (cmd == null)
                continue;
            cmd = cmd.trim();
            String id = String.valueOf(i + 1);
            list.put(id, new WSSessions(id));
            ServletHolder holder = context.addServlet(InputStreamServlet.class, "/stream/input/" + (i + 1));
            holder.setInitParameter("cmd", cmd);
            holder.setInitParameter("id", id);
            holder.setInitOrder((i + 1));
            ServletHolder holderOutput = context.addServlet(StreamWebSocketServlet.class, "/stream/output/" + (i + 1));
            holderOutput.setInitParameter("id", id);
            holderOutput.setInitOrder((i + 1));
        }
        ServletHolder holderFTag = new ServletHolder(FTSocketServlet.class);
        context.addServlet(holderFTag, "/ftag/*");

        ServletHolder staticHolder = new ServletHolder(new DefaultServlet());
        staticHolder.setInitParameter("resourceBase", "./webroot");
        context.addServlet(staticHolder, "/*");


        server.setHandler(context);
        try {
            server.start();
            server.dump(System.err);
            SDNotify.sendNotify();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    @Override
    public void received(Coords c) {
        if (Settings.sendCamPos == true) {
            FTSocket.send(String.format(Locale.ENGLISH, "%d %d %f %f %f %f %f %f", c.cam, c.id, c.x, c.y, c.z, c.rx, c.ry, c.rz));
        } else {
            if (c.cam == false) {
                FTSocket.send(String.format(Locale.ENGLISH, "%d %f %f %f %f %f %f", c.id, c.x, c.y, c.z, c.rx, c.ry, c.rz));
            }
        }

    }
}
