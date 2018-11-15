package com.jkdys.doctor.event;

public class UpdateTabCountEvent {
    public int index, count;
    public UpdateTabCountEvent(int index, int count) {
        this.index = index;
        this.count = count;
    }
}
