package org.guideme.guideme.model;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class DelayTest {

    private Delay sut;

    @Before
    public void setUp() throws Exception {
        sut = new Delay();
    }

    @Test
    public void getPeriodWithOnlySeconds() {
        sut.setPeriodInSeconds(10);
        assertEquals("0:10", sut.getPeriod());
    }

    @Test
    public void getPeriodWithMinutes() {
        sut.setPeriodInSeconds(85);
        assertEquals("1:25", sut.getPeriod());
    }

    @Test
    public void getPeriodWithHours() {
        sut.setPeriodInSeconds(2 * 60 * 60 + 3 * 60 + 4);
        assertEquals("2:03:04", sut.getPeriod());
    }

    @Test
    public void setFormattedPeriodSeconds() {
        sut.setPeriod("0:00:04");
        assertEquals(4, sut.getPeriodInSeconds());
    }

    @Test
    public void setFormattedPeriodMinutes() {
        sut.setPeriod("0:03:04");
        assertEquals(3 * 60 + 4, sut.getPeriodInSeconds());
    }

    @Test
    public void setFormattedPeriodHours() {
        sut.setPeriod("2:03:04");
        assertEquals(2 * 60 * 60 + 3 * 60 + 4, sut.getPeriodInSeconds());
    }

}
