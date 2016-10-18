package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.dao.LikesDao;
import ru.atom.model.dao.MatchDao;
import ru.atom.model.data.Match;

import java.util.List;
import java.util.stream.Stream;


public class Matcher implements Runnable {
    private static final Logger log = LogManager.getLogger(Matcher.class);

    @Override
    public void run() {
        while (true) {

            // your code here
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                log.error("Matcher was interrupted.", e);
            }
        }
    }
}
