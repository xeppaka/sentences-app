package com.xeppaka.sentence.domain;

public interface Entity {
    long ID_UNDEFINED = -1;

    long getId();
    void setId(long id);
    boolean hasId();
}
