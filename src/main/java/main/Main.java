package main;

import befit.RunBefit;
import org.apache.log4j.Logger;
import it.sauronsoftware.cron4j.Scheduler;
import org.apache.log4j.PropertyConfigurator;
import tools.Tools;

import java.io.IOException;

/**
 * Created by Przemo on 2015-03-01.
 */
public class Main {
    final static Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {

        PropertyConfigurator.configure("log4j.properties");

        log.info("BEFIT SCHEDULER START");

        Scheduler s = new Scheduler();
        s.schedule(Tools.getPattern(), new Runnable() {
            public void run() {
                try {
                    RunBefit.run();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        });

            s.start();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            log.error("StackTrace: ", e);
        }

            s.stop();

        }
    }
