package com.earthblood.tictactoe.helper;

import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import java.util.List;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class HapticFeedbackHelper {

    public static final long[] VIBE_PATTERN_SHORT = {0, 50, 1000 };
    public static final long[] VIBE_PATTERN_WIN = new long[]{0,200,50,200,50,200};
    public static final long[] VIBE_PATTERN_ANDROID_WIN = new long[]{0,100,50,100,50,100};
    public static final int    VIBE_PATTERN_NO_REPEAT = -1;

    private static boolean vibeFlag = true;

    @Inject Vibrator vibrator;

    public void vibrate(long[] pattern, int repeat){
        vibrator.vibrate(pattern, repeat);
    }
    public long[] getWinningPattern(boolean androidWon){
        return androidWon ? VIBE_PATTERN_ANDROID_WIN : VIBE_PATTERN_WIN;
    }
    public void addFeedbackToButtonList(List<Button> buttons){
        for (Button button : buttons) {
               button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        if(vibeFlag) vibrator.vibrate(VIBE_PATTERN_SHORT, VIBE_PATTERN_NO_REPEAT);
                        vibeFlag = false;
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        vibeFlag = true;
                    }
                    return false;
                }
            });
        }
    }
    public void addFeedbackToButton(Button button, final long[] pattern, final int repeat){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(vibeFlag) vibrator.vibrate(pattern, repeat);
                    vibeFlag = false;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    vibeFlag = true;
                }
                return false;
            }
        });
    }
}
