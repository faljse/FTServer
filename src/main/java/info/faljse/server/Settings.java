package info.faljse.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Settings {
    private final static Logger logger = LoggerFactory.getLogger(Settings.class);
    public static int port;
    public static String ftCmd[]=new String[10];
    public static String publicURL;
    public static boolean sendCamPos;

    static {
        read();
    }

    private Settings(){

    }
    public static void read() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            port=Integer.parseInt(prop.getProperty("port"));
            publicURL=prop.getProperty("publicURL", "http://127.0.0.1");
            sendCamPos=Boolean.parseBoolean(prop.getProperty("sendCamPos", "false"));

            List<String> cmds=new ArrayList<String>();
            for(int i=1;i<10;i++){
                String val = prop.getProperty("ftCmd" + i);
                cmds.add(val);
            }
            ftCmd= cmds.toArray(new String[0]);
        } catch (IOException ex) {
            logger.error("Cant load config", ex);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
