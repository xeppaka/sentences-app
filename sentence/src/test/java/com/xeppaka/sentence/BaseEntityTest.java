package com.xeppaka.sentence;

import com.xeppaka.sentence.BaseEntity;
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
    public void testInitialStateIsUndefinedNotAttached() {
        Assert.assertEquals(Entity.ID_UNDEFINED, baseEntity.getId());
        Assert.assertFalse(baseEntity.isAttached());
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
        Assert.assertTrue(baseEntity.isAttached());
    }

    @Test
    public void testIdIsSettableMax() {
        baseEntity.setId(Long.MAX_VALUE);
        Assert.assertEquals(Long.MAX_VALUE, baseEntity.getId());
        Assert.assertTrue(baseEntity.isAttached());
    }
}
