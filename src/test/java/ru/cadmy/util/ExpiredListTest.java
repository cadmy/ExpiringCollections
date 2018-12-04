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
            TimeUnit.MILLISECONDS.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }

    @Test
    public void addRemoveTest() {
        ExpiredList<String> expiredList = new ExpiredList<>(1, TimeUnit.SECONDS);
        expiredList.add("a");
        expiredList.remove("a");
        try {
            TimeUnit.MILLISECONDS.sleep(1001);
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
            TimeUnit.MILLISECONDS.sleep(1001);
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
            TimeUnit.MILLISECONDS.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, expiredList.size());
    }
}