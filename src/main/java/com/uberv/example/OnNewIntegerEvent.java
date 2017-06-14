package com.uberv.example;

public class OnNewIntegerEvent extends MyEvent {
    private int mValue;

    public OnNewIntegerEvent(int mValue) {
        this.mValue = mValue;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }
}
