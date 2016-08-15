package com.xeppaka.sentence;

public interface Entity {
    long ID_UNDEFINED = -1;

    long getId();
    void setId(long id);
    boolean hasId();
}
