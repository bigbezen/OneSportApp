package com.example.matan.onesportapp.Util;

public class Pair<f,s> {

    private f first;
    private s second;

    public Pair(f first, s second){
        this.first = first;
        this.second = second;
    }

    public f getFirst() {
        return first;
    }

    public s getSecond() {
        return second;
    }

    public void setSecond(s second) {
        this.second = second;
    }

    public void setFirst(f first) {
        this.first = first;
    }
}
