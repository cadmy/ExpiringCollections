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
    private Map<E, Long> items = new ConcurrentHashMap<>();

    public ExpiredList() {
    }

    public ExpiredList(long timeLive) {
        this.timeLive = timeLive * 1000 * 1000;
    }

    public ExpiredList(int timeLive) {
        this.timeLive = timeLive * 1000 * 1000;
    }

    public ExpiredList(int timeLive, TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                this.timeLive = timeLive/1000;
                break;
            case MICROSECONDS:
                this.timeLive = timeLive;
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
    }

    @Override
    public boolean add(E item) {
        long now = getNow();
        items.put(item, now + timeLive);
        return super.add(item);
    }

    @Override
    public E get(int index) {
        long now = getNow();
        if (now > items.get(this.get(index))) {
            return get(index-1);
        } else {
            return super.get(index);
        }
    }

    @Override
    public int size() {
        int size = 0;
        long now = getNow();
        for (long time : items.values()) {
            if (time > now) {
                size++;
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() < 1;
    }

    private long getNow() {
        LocalDateTime now = LocalDateTime.now();
        return now.toEpochSecond(ZoneOffset.UTC)  * 1000 * 1000
                + now.getLong(ChronoField.MILLI_OF_SECOND)
                + now.getLong(ChronoField.MICRO_OF_SECOND);
    }
}
