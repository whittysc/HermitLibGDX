package com.hmtcbhs.libgdx.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.hmtcbhs.libgdx.HermitClubhouseLibGDX;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1080, 720);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new HermitClubhouseLibGDX();
        }
}