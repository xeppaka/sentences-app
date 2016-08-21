package com.xeppaka.sentence.domain;

/**
 * Class represents base for all Entity classes in the system.
 * Implements basic manipulation methods with Entity id.
 */
public abstract class BaseEntity extends AssertionConcern implements Entity {
    private long id = ID_UNDEFINED;

    @Override
    public long getId() {
        assertStateTrue(hasId(), "There is no id assigned.");

        return id;
    }

    public void setId(long id) {
        assertArgumentRange(id, 0, Long.MAX_VALUE, "id must be positive.");

        this.id = id;
    }

    protected BaseEntity() { }

    public BaseEntity(long id) {
        setId(id);
    }

    @Override
    public boolean hasId() {
        return id != ID_UNDEFINED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
