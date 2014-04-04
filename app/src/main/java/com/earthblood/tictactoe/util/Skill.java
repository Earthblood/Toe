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
package com.earthblood.tictactoe.util;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategyEasy;
import com.earthblood.tictactoe.strategy.ToeStrategyHard;
import com.earthblood.tictactoe.strategy.ToeStrategyNormal;
import com.earthblood.tictactoe.strategy.ToeStrategyVeryHard;

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
                return new ToeStrategyNormal(selectedXBoxIds, selectedOBoxIds, androidSymbol);
            case HARD:
                return new ToeStrategyHard(selectedXBoxIds, selectedOBoxIds, androidSymbol);
            case VERYHARD:
                return new ToeStrategyVeryHard(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
        return null;
    }
}
