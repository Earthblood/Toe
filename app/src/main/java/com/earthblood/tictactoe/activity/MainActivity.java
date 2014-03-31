package com.earthblood.tictactoe.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.util.Skill;

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

    @InjectView(R.id.skill_spinner)
    Spinner skillSpinner;
    @InjectView(R.id.gameplay_one_player)
    RadioButton onePlayerButton;
    @InjectView(R.id.gameplay_two_player)
    RadioButton twoPlayerButton;
    @InjectView(R.id.difficulty_label)
    TextView difficultyLabel;


    @Inject
    ToeGame toeGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSkill();
    }

    @Override
    protected void onResume(){
        super.onResume();
        initializeSkill();
        initializeNumberOfPlayers();
    }


    private void setPlayers(boolean onePlayerGame){
        if(onePlayerGame){
            skillSpinner.setVisibility(View.VISIBLE);
            difficultyLabel.setVisibility(View.VISIBLE);
            onePlayerButton.setChecked(true);
            toeGame.setNumOfPlayers(1);
        }
        else{
            skillSpinner.setVisibility(View.INVISIBLE);
            difficultyLabel.setVisibility(View.INVISIBLE);
            twoPlayerButton.setChecked(true);
            toeGame.setNumOfPlayers(2);
        }
    }

    private void setupSkill() {
        skillSpinner.setAdapter(new ArrayAdapter<Skill>(this, android.R.layout.simple_list_item_1, Skill.values()));
        skillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
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



}
