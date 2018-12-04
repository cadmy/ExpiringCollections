package ru.cadmy.util;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cadmy on 04.12.2018.
 */
public class ExpiredList<E> extends ArrayList<E> {

    private long timeLive = 3600000L;

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
    public boolean add(E e) {
        Thread deleteElementsThread = new DeleteElementsThread(this, e);
        deleteElementsThread.start();
        return super.add(e);
    }

    class DeleteElementsThread extends Thread {
        private E item;
        private ArrayList<E> list;

        DeleteElementsThread(ArrayList<E> list, E item) {
            this.list = list;
            this.item = item;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(timeLive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.remove(item);
        }
    }
}
