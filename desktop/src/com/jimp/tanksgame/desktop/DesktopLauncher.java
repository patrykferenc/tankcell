package com.jimp.tanksgame.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jimp.tanksgame.TanksGame;

import static com.jimp.tanksgame.graphics.ScreenProperties.HEIGHT;
import static com.jimp.tanksgame.graphics.ScreenProperties.WIDTH;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Tanks");
        config.setWindowedMode(WIDTH, HEIGHT);
        new Lwjgl3Application(new TanksGame(), config);
    }
}
