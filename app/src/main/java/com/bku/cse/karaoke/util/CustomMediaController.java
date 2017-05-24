package com.bku.cse.karaoke.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.MediaController;

/**
 * Created by quangthanh on 5/13/2017.
 */

public class CustomMediaController extends FrameLayout {
    private MediaController.MediaPlayerControl mPlayer;
    private Context mContext;

    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}