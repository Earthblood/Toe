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
package com.earthblood.tictactoe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.CoinTossHelper;
import com.earthblood.tictactoe.helper.HapticFeedbackHelper;
import com.earthblood.tictactoe.helper.HtmlHelper;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;

import java.util.Arrays;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    @InjectView(R.id.skill_spinner)          Spinner skillSpinner;
    @InjectView(R.id.gameplay_one_player)    RadioButton onePlayerButton;
    @InjectView(R.id.gameplay_two_player)    RadioButton twoPlayerButton;
    @InjectView(R.id.message_you_will_be)    TextView messageYouWillBe;
    @InjectView(R.id.turn_display)           TextView turnDisplay;
    @InjectView(R.id.button_new_game)        Button buttonNewGame;
    @InjectView(R.id.coin_toss_button)       Button coinTossButton;

    @Inject ToeGame toeGame;
    @Inject CoinTossHelper coinTossHelper;
    @Inject HapticFeedbackHelper hapticFeedbackHelper;
    @Inject HtmlHelper htmlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(htmlHelper.fromHtml(toeGame.titleHack(getString(R.string.app_name_short), getString(R.string.tic_tac_toe))));
        hapticFeedbackHelper.addFeedbackToButtonList(Arrays.asList(buttonNewGame, coinTossButton, onePlayerButton, twoPlayerButton));
        setupSkill();
    }

    @Override
    protected void onResume(){
        super.onResume();

        toeGame.reset(getContentResolver());
        initializeSkill();
        initializeNumberOfPlayers();
        setWhoGoesFirst(toeGame.getTurn());
    }

    private void setWhoGoesFirst(GameSymbol turn) {
        toeGame.setTurn(turn);
        turnDisplay.setText(getString(R.string.who_goes_first, turn.getValue()));
    }

    private void setPlayers(boolean onePlayerGame){
        if(onePlayerGame){
            skillSpinner.setVisibility(View.VISIBLE);
            messageYouWillBe.setVisibility(View.VISIBLE);
            onePlayerButton.setChecked(true);
            toeGame.setNumOfPlayers(1);
        }
        else{
            skillSpinner.setVisibility(View.INVISIBLE);
            messageYouWillBe.setVisibility(View.INVISIBLE);
            twoPlayerButton.setChecked(true);
            toeGame.setNumOfPlayers(2);
        }
    }

    private void setupSkill() {
        skillSpinner.setAdapter(new ArrayAdapter<Skill>(this, R.layout.skill_spinner_layout, Skill.values()));
        skillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hapticFeedbackHelper.vibrate(HapticFeedbackHelper.VIBE_PATTERN_SHORT, HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
                Skill skill = (Skill) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.lime));
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                toeGame.setSkill(skill);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeNumberOfPlayers() {
        setPlayers(toeGame.getNumOfPlayers() == 1);
    }

    private void initializeSkill() {
        ArrayAdapter adapter = (ArrayAdapter)skillSpinner.getAdapter();
        int position = adapter.getPosition(toeGame.getSkill());
        skillSpinner.setSelection(position);
    }

    /**
     * User Interactions
     */
    public void setNumberOfPlayers(View view){
        setPlayers(R.id.gameplay_one_player == view.getId());
    }
    public void coinToss(View view){
        setWhoGoesFirst(coinTossHelper.coinToss());
    }
    public void newGame(View view){
        toeGame.setGameInProgess();
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }


}
