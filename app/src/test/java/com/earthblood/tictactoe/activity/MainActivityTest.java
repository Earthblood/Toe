package com.earthblood.tictactoe.activity;

import android.widget.Spinner;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.util.Skill;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.ANDROID.assertThat;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
    }

    @Test
    public void shouldNotBeNull() {

        assertThat(activity).isNotNull();

        Spinner spinner = (Spinner) activity.findViewById(R.id.skill_spinner);
        assertThat(spinner).isNotNull();
    }


    @Test
    public void shouldSelectCorrectSkillLevelFromSkillSpinner(){

        Spinner spinner = (Spinner) activity.findViewById(R.id.skill_spinner);

        spinner.setSelection(0);
        Object itemAtPosition = spinner.getItemAtPosition(0);
        Assert.assertEquals(Skill.EASY, (Skill)itemAtPosition);

        spinner.setSelection(1);
        itemAtPosition = spinner.getItemAtPosition(1);
        Assert.assertEquals(Skill.NORMAL, (Skill)itemAtPosition);

        spinner.setSelection(2);
        itemAtPosition = spinner.getItemAtPosition(2);
        Assert.assertEquals(Skill.HARD, (Skill)itemAtPosition);

    }

}
