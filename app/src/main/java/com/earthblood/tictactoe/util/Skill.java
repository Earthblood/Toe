package com.earthblood.tictactoe.util;


import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategyEasy;
import com.earthblood.tictactoe.strategy.ToeStrategyHard;
import com.earthblood.tictactoe.strategy.ToeStrategyNormal;
import com.earthblood.tictactoe.strategy.ToeStrategyVeryHard;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum Skill {

    EASY     (1,R.string.skill_easy),
    NORMAL   (2,R.string.skill_normal),
    HARD     (3,R.string.skill_hard),
    VERYHARD (4,R.string.skill_very_hard);


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

    public ToeStrategy strategy(int[] selectedXBoxIds, int [] selectedOBoxIds, GameSymbol androidSymbol){
        switch (this){
            case EASY:
                return new ToeStrategyEasy(selectedXBoxIds, selectedOBoxIds, androidSymbol);
            case NORMAL:
                //TODO: Implement ToeStrategyNormal
                return new ToeStrategyNormal(selectedXBoxIds, selectedOBoxIds, androidSymbol);
            case HARD:
                //TODO: Implement ToeStrategyHard
                return new ToeStrategyHard(selectedXBoxIds, selectedOBoxIds, androidSymbol);
            case VERYHARD:
                //TODO: Implement ToeStrategyVeryHard
                return new ToeStrategyVeryHard(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
        return null;
    }
}
