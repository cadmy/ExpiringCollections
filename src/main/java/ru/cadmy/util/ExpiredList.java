package ru.cadmy.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cadmy on 04.12.2018.
 */
public class ExpiredList<E> extends ArrayList<E> {

    private long timeLive = 3600000L;
    Timer timer;
    Map<Long, E> items = new HashMap<>();

    public ExpiredList() {
        createTimer();
    }

    public ExpiredList(long timeLive) {
        this.timeLive = timeLive * 1000;
        createTimer();
    }

    public ExpiredList(int timeLive) {
        this.timeLive = timeLive * 1000;
        createTimer();
    }

    private void createTimer() {
        timer = new Timer("Timer");
        timer.schedule(new TimerTask() {
            public void run() {
                for (Long expiringTime : items.keySet()) {
                    if (expiringTime >=  System.currentTimeMillis()) {
                        ExpiredList.super.remove(items.get(expiringTime));
                    }
                }
            }
        }, 1);
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
        createTimer();
    }

    @Override
    public boolean add(E item) {
        items.put(System.currentTimeMillis() + timeLive, item);
        super.add(item);
        return true;
    }
}
