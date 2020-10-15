package com.meli.lv1.utils;

import org.junit.Before;
import org.junit.Test;

import static com.meli.lv1.utils.DirectionCountHelper.Direction.HORIZONTAL;
import static com.meli.lv1.utils.DirectionCountHelper.Direction.VERTICAL;
import static org.junit.Assert.assertEquals;

public class DirectionCountHelperTest {

    private DirectionCountHelper directionCountHelper;

    @Before
    public void setUp() {
        directionCountHelper = new DirectionCountHelper(3);
    }

    @Test
    public void testCheckAndCoincidences() {
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');

        int result = directionCountHelper.getCoincidences();

        assertEquals(2, result);
    }

    @Test
    public void testCheckAndCoincidencesWithReset() {
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.resetLoop();
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');


        int result = directionCountHelper.getCoincidences();

        assertEquals(0, result);
    }

    @Test
    public void testCheckAndCoincidencesWithReset_2() {
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.check(VERTICAL, 'L');
        directionCountHelper.check(HORIZONTAL, 'L');
        directionCountHelper.resetLoop();
        directionCountHelper.check(VERTICAL, 'L');


        int result = directionCountHelper.getCoincidences();

        assertEquals(1, result);
    }

}