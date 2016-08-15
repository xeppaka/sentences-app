package com.xeppaka.sentence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class BaseEntityTest {
    private BaseEntity baseEntity;

    @Before
    public void setUp() {
        baseEntity = new BaseEntity() { };
    }

    @Test
    public void testInitialStateIdIsUndefined() {
        Assert.assertFalse(baseEntity.hasId());
    }

    @Test(expected = IllegalStateException.class)
    public void testInitialStateIdIsNotGettable() {
        baseEntity.getId();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeIdIsNotAllowed() {
        baseEntity.setId(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeIdIsNotAllowedBig() {
        baseEntity.setId(-100000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeIdIsNotAllowedMin() {
        baseEntity.setId(Long.MIN_VALUE);
    }

    @Test
    public void testIdIsSettable() {
        baseEntity.setId(0);
        Assert.assertEquals(0, baseEntity.getId());
    }

    @Test
    public void testIdIsSettableMax() {
        baseEntity.setId(Long.MAX_VALUE);
        Assert.assertEquals(Long.MAX_VALUE, baseEntity.getId());
    }
}
