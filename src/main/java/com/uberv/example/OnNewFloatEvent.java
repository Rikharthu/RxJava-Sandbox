package com.uberv.example;

public class OnNewFloatEvent extends MyEvent {
    private float mValue;

    public OnNewFloatEvent(float mValue) {
        this.mValue = mValue;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }
}
