package com.earthblood.tictactoe.util;


import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.R;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum Skill {

    EASY  (1,R.string.skill_easy),
    NORMAL(2,R.string.skill_normal),
    HARD  (3,R.string.skill_hard);

    private int id;
    private int resourceId;

    private Skill(int id, int resourceId){
        this.id = id;
        this.resourceId = resourceId;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return Toe.getResourceString(resourceId);
    }

    public static Skill byId(int id){
        for (Skill skill : Skill.values()) {
            if(id == skill.id){
                return skill;
            }
        }
        return null;
    }
}
