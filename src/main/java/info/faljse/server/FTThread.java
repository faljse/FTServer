package info.faljse.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FTThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(FTThread.class);
    private final String cmd;

    public FTThread(String cmd){
        this.cmd=cmd;
    }
    @Override
    public void run() {
        while(true) {
            try {
                runftag();
            } catch (Exception e) {
                logger.warn("fftag cmd failed, respawning in 5s", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void runftag() throws IOException {
        ProcessBuilder pb =
                new ProcessBuilder(cmd.split("\\s* \\s*"));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
        InputStream in = p.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        String line=null;
        //ffmpeg sends all output to stdout
        int cnt=0; //display first x lines for debuging
        while((line=br.readLine())!=null){
            if(cnt<50){
                cnt++;
                logger.info(line);
            }
            else if(cnt==50){
                cnt++;
                logger.info("Hiding ffmpeg output.");
            }
        }

    }
}