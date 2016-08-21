package com.xeppaka.sentence.domain;

/**
 * Interface represents an Entity in the system.
 */
public interface Entity {
    long ID_UNDEFINED = -1;

    /**
     * Gets entity id which should be unique across the system.
     * @return entity id.
     */
    long getId();

    /**
     * Sets entity id. Should NOT be negative.
     * @param id is entity id.
     */
    void setId(long id);

    /**
     * Checks whether entity has id set.
     * @return true if entity has id set, false otherwise.
     */
    boolean hasId();
}
