package com.xeppaka.sentence.domain;

/**
 *
 */
public class AssertionConcern {
    protected AssertionConcern() {
        super();
    }

    protected void assertArgumentEquals(Object object1, Object object2, String message) {
        if (!object1.equals(object2)) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentFalse(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentLength(String str, int maxLength, String message) {
        int length = str.length();
        if (length > maxLength) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentLength(String str, int minLength, int maxLength, String message) {
        int length = str.length();
        if (length < minLength || length > maxLength) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentNotEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentNotEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentNotEquals(Object object1, Object object2, String message) {
        if (object1.equals(object2)) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentRange(double value, double minimum, double maximum, String message) {
        if (value < minimum || value > maximum) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentRange(float value, float minimum, float maximum, String message) {
        if (value < minimum || value > maximum) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentRange(int value, int minimum, int maximum, String message) {
        if (value < minimum || value > maximum) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentRange(long value, long minimum, long maximum, String message) {
        if (value < minimum || value > maximum) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertStateFalse(boolean value, String message) {
        if (value) {
            throw new IllegalStateException(message);
        }
    }

    protected void assertStateTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalStateException(message);
        }
    }
}
