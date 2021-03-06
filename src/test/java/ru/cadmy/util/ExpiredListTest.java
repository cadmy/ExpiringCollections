package ru.cadmy.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Cadmy on 04.12.2018.
 */
@RunWith(JUnit4.class)
public class ExpiredListTest {

    private final int timeout = 1000;

    @Test
    public void createExpiredListTest() {
        ExpiredList<String> expiredList = new ExpiredList<>();
    }

    @Test
    public void addTest() {
        ExpiredList<String> expiredList = new ExpiredList<>();
        expiredList.add("a");
    }

    @Test
    public void checkItemRemovedAfterOneSecond() {
        ExpiredList<String> expiredList = new ExpiredList<>(1, TimeUnit.SECONDS);
        expiredList.add("a");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }

    @Test
    public void checkItemIsNotRemovedBeforeOneSecond() {
        ExpiredList<String> expiredList = new ExpiredList<>(1, TimeUnit.SECONDS);
        expiredList.add("a");
        try {
            TimeUnit.MILLISECONDS.sleep(999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, expiredList.size());
    }

    @Test
    public void addRemoveTest() {
        ExpiredList<String> expiredList = new ExpiredList<>(1, TimeUnit.SECONDS);
        expiredList.add("a");
        expiredList.remove("a");
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }

    @Test
    public void checkItemRemovedAfterOneSecondLongValueConstructor() {
        ExpiredList<String> expiredList = new ExpiredList<>(1L);
        expiredList.add("a");
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }

    @Test
    public void checkItemRemovedAfterOneSecondIntegerValueConstructor() {
        ExpiredList<String> expiredList = new ExpiredList<>(1);
        expiredList.add("a");
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }

    @Test
    public void checkMultipleItems() {
        ExpiredList<String> expiredList = new ExpiredList<>(1, TimeUnit.SECONDS);
        expiredList.add("a");
        expiredList.add("b");
        expiredList.add("c");
        expiredList.add("d");
        expiredList.add("e");
        expiredList.add("f");
        expiredList.add("g");
        expiredList.add("h");
        expiredList.add("i");
        expiredList.add("j");
        expiredList.add("k");
        expiredList.add("l");
        expiredList.add("m");
        expiredList.add("n");
        expiredList.add("o");
        expiredList.add("p");
        expiredList.add("q");
        expiredList.add("r");
        expiredList.add("s");
        expiredList.add("t");
        expiredList.add("u");
        expiredList.add("v");
        expiredList.add("w");
        expiredList.add("x");
        expiredList.add("y");
        expiredList.add("z");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }
}