package com.xeppaka.sentence;

/**
 *
 */
public abstract class BaseEntity implements Entity {
    private long id = ID_UNDEFINED;

    @Override
    public long getId() {
        if (!hasId()) {
            throw new IllegalStateException("There is no id assigned.");
        }

        return id;
    }

    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be positive.");
        }

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
