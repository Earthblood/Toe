package com.earthblood.tictactoe.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.earthblood.tictactoe.application.EarthbloodApp;
import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.util.Skill;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSkill();
        initializeNumberOfPlayers();
    }

    private void initializeNumberOfPlayers() {

        switch (EarthbloodApp.getToeGame().getNumOfPlayers()){
            case 1:
                RadioButton radioButton = (RadioButton)findViewById(R.id.gameplay_one_player);
                radioButton.setChecked(true);
                break;
            case 2:
                RadioButton radioButton2 = (RadioButton)findViewById(R.id.gameplay_two_player);
                radioButton2.setChecked(true);
                break;
        }
    }

    private void initializeSkill() {

        //Skill Spinner
        Spinner spinner = (Spinner) findViewById(R.id.skill_spinner);
        spinner.setAdapter(new ArrayAdapter<Skill>(this, android.R.layout.simple_list_item_1, Skill.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Skill skill = (Skill) parent.getItemAtPosition(position);
                EarthbloodApp.getToeGame().setSkill(skill);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Set Spinner Value
        ArrayAdapter adapter = (ArrayAdapter)spinner.getAdapter();
        int position = adapter.getPosition(EarthbloodApp.getToeGame().getSkill());
        spinner.setSelection(position);
    }

    public void onSetNumberOfPlayers(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.gameplay_one_player:
                if (checked)
                    EarthbloodApp.getToeGame().setNumOfPlayers(1);
                    break;
            case R.id.gameplay_two_player:
                if (checked)
                    EarthbloodApp.getToeGame().setNumOfPlayers(2);
                    break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
