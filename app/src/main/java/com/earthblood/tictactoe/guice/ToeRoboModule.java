package com.earthblood.tictactoe.guice;

import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.helper.PreferenceHelperImpl;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeRoboModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PreferenceHelper.class).to(PreferenceHelperImpl.class);
    }
}
