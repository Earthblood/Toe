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
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;

import java.util.Arrays;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;


/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hapticFeedbackHelper.addFeedbackToButtonList(Arrays.asList(buttonNewGame, coinTossButton, onePlayerButton, twoPlayerButton));
        setupSkill();
        setTitle(getString(R.string.app_name));
        setTitleColor(getResources().getColor(R.color.IndianRed));
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
