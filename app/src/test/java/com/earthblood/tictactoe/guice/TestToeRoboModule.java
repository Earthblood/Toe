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
package com.earthblood.tictactoe.guice;

import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.CoinTossHelper;
import com.earthblood.tictactoe.helper.HapticFeedbackHelper;
import com.earthblood.tictactoe.helper.HtmlHelper;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.google.inject.AbstractModule;

import org.mockito.Mockito;

public class TestToeRoboModule extends AbstractModule{

    @Override
    protected void configure() {

        bind(HapticFeedbackHelper.class).toInstance(Mockito.mock(HapticFeedbackHelper.class));
        bind(ToeGame.class).toInstance(Mockito.mock(ToeGame.class));
        bind(PreferenceHelper.class).toInstance(Mockito.mock(PreferenceHelper.class));
        bind(CoinTossHelper.class).toInstance(Mockito.mock(CoinTossHelper.class));
        bind(HtmlHelper.class).toInstance(Mockito.mock(HtmlHelper.class));
    }
}
