/**
 * @author John Piser developer@earthblood.com
 *
 * Copyright (C) 2014 EARTHBLOOD, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.earthblood.tictactoe.helper;

import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import java.util.List;

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
