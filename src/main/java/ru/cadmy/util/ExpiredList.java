package ru.cadmy.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cadmy on 04.12.2018.
 */
public class ExpiredList<E> extends CopyOnWriteArrayList<E> {

    private long timeLive = 3600000000L;
    private Map<Long, E> items =new ConcurrentHashMap<>();

    public ExpiredList() {
        createTimer();
    }

    public ExpiredList(long timeLive) {
        this.timeLive = timeLive * 1000 * 1000;
        createTimer();
    }

    public ExpiredList(int timeLive) {
        this.timeLive = timeLive * 1000 * 1000;
        createTimer();
    }

    public ExpiredList(int timeLive, TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                this.timeLive = timeLive/1000;
                break;
            case MILLISECONDS:
                this.timeLive = timeLive * 1000;
                break;
            case SECONDS:
                this.timeLive = timeLive * 1000 * 1000;
                break;
            case MINUTES:
                this.timeLive = timeLive * 1000 * 1000 * 60;
                break;
            case HOURS:
                this.timeLive = timeLive * 1000 * 1000 * 60 * 60;
                break;
            case DAYS:
                this.timeLive = timeLive * 1000 * 1000 * 60 * 60 * 24;
                break;
            default:
                this.timeLive = timeLive * 1000 * 1000;
                break;
        }
        createTimer();
    }

    private void createTimer() {
        Timer timer = new Timer("Timer");
        timer.schedule(new TimerTask() {
            public void run() {
            for (Long expiringTime : items.keySet()) {
                LocalDateTime now = LocalDateTime.now();
                if (expiringTime < now.toEpochSecond(ZoneOffset.UTC) * 1000 * 1000
                        + LocalDateTime.now().getLong(ChronoField.MILLI_OF_SECOND)
                        + LocalDateTime.now().getLong(ChronoField.MICRO_OF_SECOND)) {
                    ExpiredList.super.remove(items.get(expiringTime));
                }
            }
            }
        }, 0, 1);
    }

    @Override
    public boolean add(E item) {
        LocalDateTime now = LocalDateTime.now();
        items.put(now.toEpochSecond(ZoneOffset.UTC) * 1000 * 1000
                + now.getLong(ChronoField.MILLI_OF_SECOND)
                + now.getLong(ChronoField.MICRO_OF_SECOND)
                + (timeLive), item);
        return super.add(item);
    }
}
