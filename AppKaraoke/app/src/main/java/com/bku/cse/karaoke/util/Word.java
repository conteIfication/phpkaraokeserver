package com.bku.cse.karaoke.util;

/**
 * Created by apink on 5/4/2017.
 */

public class Word {
    private int timeStart; //miliseconds
    private String content;
    private int line;

    public Word(int timeStart, String content, int line) {
        this.timeStart = timeStart;
        this.content = content;
        this.line = line;
    }
    public void setTimeStart(int time) {
        this.timeStart = time;
    }
    public int getTimeStart() {return timeStart;}
    public void setContent(String s) {
        this.content = s;
    }
    public String getContent() {return this.content;}
    public void setLine(int line) {
        this.line = line;
    }
    public int getLine() {return this.line;};

    @Override
    public String toString() {
        return content + " - " + timeStart + " - " + line;
    }
}
