package main;

import befit.RunBefit;
import befit.Status;
import org.apache.log4j.Logger;
import it.sauronsoftware.cron4j.Scheduler;
import org.apache.log4j.PropertyConfigurator;
import tools.Tools;

/**
 * Created by Przemo on 2015-03-01.
 */
public class Main {
    final static Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("log4j.properties");
        log.info("BEFIT SCHEDULER START");

        Scheduler scheduler = new Scheduler();
        Scheduler schedulerStatusReset = new Scheduler();

        scheduler.schedule(Tools.getPattern(), new Runnable() {
            public void run() {
                try {
                    if (!Status.isSigned()) {
                        RunBefit.run();
                    }
                } catch (IllegalArgumentException e) {
                    log.info("Nothing to sign today");
                    Status.setSigned(true);
                } catch (Exception e) {
                    log.error("Unexpected: " + e);
                }
            }
        });

        schedulerStatusReset.schedule(Tools.getPatternStatus(), new Runnable() {
            public void run() {
                Status.setSigned(false);
            }
        });

            scheduler.start();
            schedulerStatusReset.start();

            Thread.sleep(Long.MAX_VALUE);

            scheduler.stop();
            schedulerStatusReset.stop();
        }
    }
