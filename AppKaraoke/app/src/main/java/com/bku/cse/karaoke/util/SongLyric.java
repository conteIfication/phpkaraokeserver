package com.bku.cse.karaoke.util;

import android.net.Uri;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thonghuynh on 5/10/2017.
 */

public class SongLyric {
    private List<Word> arrWords;

    public SongLyric() {
        arrWords = new ArrayList<>();
    }

    //getWord
    public Word getWord(int pos) {return arrWords.get(pos); }

    //getSize()
    public int getSize() {return arrWords.size();}

    //get lyrics
    public List<Word> getLyrics() {return arrWords;}

    //Parse XMl file to get array word
    public void doParse(String url) {
        try {
            XmlPullParserFactory mFactory = XmlPullParserFactory.newInstance();
            XmlPullParser mParser = mFactory.newPullParser();
//            mParser.setInput(getAssets().open("song02.xml"), null);
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);

            mParser.setInput(fis, null);

            int line = -1;
            int event = mParser.getEventType();
            boolean startI = false;
            int time = -1;

            while(event != XmlPullParser.END_DOCUMENT) {

                String name = mParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if (name.equals("param"))
                            ++line;
                        if (name.equals("i")) {
                            startI = true;
                            String strTime = mParser.getAttributeValue(null, "va");
                            time = toMillisecond(strTime);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (startI) {
                            arrWords.add(new Word(time, mParser.getText(), line));
                            //Log.d("Content", mParser.getText() + "\nTime: " + time);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("i"))
                            startI = false;
                        break;
                }

                mParser.next();
                event = mParser.getEventType();
            }

            fis.close();

        } catch (Exception e) {e.printStackTrace();}
    }

    //get lyric on one line
    public String getLyricOnLine(int line) {
        String s = "";
        for (int i = 0; i < arrWords.size(); i++) {
            int currLine = arrWords.get(i).getLine();

            if (currLine == line) {
                s += arrWords.get(i).getContent();
            }
            if (currLine > line)
                return s;
        }
        return s;
    }

    //get line depend on currentTime
    public int getCurrentLine(int currentTime) {
        int sTime = 0, eTime = 0;
        int currLine = 0;
        boolean newLine = false;
        for (int i = 0; i < arrWords.size(); i++) {
            Word w = arrWords.get(i);
            if (currLine != w.getLine()) {
                newLine = true;
                currLine = w.getLine();
                eTime = w.getTimeStart();
            }
            if (newLine) {
                if (currentTime >= sTime && currentTime < eTime) {
                    return currLine - 1;
                }
                sTime = w.getTimeStart();
                newLine = false;
            }
            //end of lyrics
            if (i+1 >= arrWords.size()) {
                if (currentTime >= sTime)
                    return currLine;
            }
        }
        return -1;

    }

    //get index of word depend on currentTime
    public Word getWordAtTime(int currentTime) {
        if (currentTime < arrWords.get(0).getTimeStart()) {
            return arrWords.get(0);
        }
        for (int i = 0; i < arrWords.size(); i++ ) {
            Word w = arrWords.get(i);

            if ( (currentTime - w.getTimeStart() >= 0) && (currentTime - w.getTimeStart() <= 100) ) {
                return w;
            }
        }

        return null;
    }

    public int toMillisecond(String s) {
        String pattern = "(\\d+):(\\d+):(\\d+)";
        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(s);
        if (m.find()) {
            return          Integer.parseInt(m.group(1)) * 60000 +
                    Integer.parseInt(m.group(2)) * 1000 +
                    Integer.parseInt(m.group(3)) ;
        }

        return -1;
    }
}
