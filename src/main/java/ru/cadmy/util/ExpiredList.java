package ru.cadmy.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cadmy on 04.12.2018.
 */
public class ExpiredList<E> extends ArrayList<E> {

    private long timeLive = 3600000L;
    Timer timer = new Timer("Timer");

    public ExpiredList() {
    }

    public ExpiredList(long timeLive) {
        this.timeLive = timeLive * 1000;
    }

    public ExpiredList(int timeLive) {
        this.timeLive = timeLive * 1000;
    }

    public ExpiredList(int timeLive, TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                this.timeLive = Math.round(timeLive/1000);
                break;
            case MILLISECONDS:
                this.timeLive = timeLive;
                break;
            case SECONDS:
                this.timeLive = timeLive * 1000;
                break;
            case MINUTES:
                this.timeLive = timeLive * 1000 * 60;
                break;
            case HOURS:
                this.timeLive = timeLive * 1000 * 60 * 60;
                break;
            case DAYS:
                this.timeLive = timeLive * 1000 * 60 * 60 * 24;
                break;
            default:
                this.timeLive = timeLive * 1000;
                break;
        }
    }

    @Override
    public boolean add(E item) {
        boolean result = super.add(item);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute( () -> {
            long startTime = System.currentTimeMillis();
            while (timeLive < 2*60*1000) {
                this.remove(item);
                timeLive = (new Date()).getTime() - startTime;
            }
        } );
        return result;
    }
}
