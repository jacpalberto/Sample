package com.example.qualtopgroup.sample;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isEven() throws Exception{
        Utils a = new Utils();
        assertEquals(false, a.isEven(5));
    }
    @Test
    public void test2() throws Exception{
        ActivityMenu s = new ActivityMenu();
        assertEquals(false, s.isEven(2));
    }
}