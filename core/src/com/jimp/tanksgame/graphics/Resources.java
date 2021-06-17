package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class Resources {

    private static Resources instance;

    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));
    private TextureRegion whitePixelTexture;
    private TextureRegion background;
    private Sprite bomb;
    private Sprite leftTankBody;
    private Sprite leftTankTurret;
    private Sprite rightTankBody;
    private Sprite rightTankTurret;
    private Sprite bullet;
    private Sprite cellGreen;
    private Sprite cellYellow;
    private Sprite cellRed;
    private Sprite title;
    private Sprite grass;
    private Sprite dirt;
    private List<Sprite> numbers;
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/skin.json"));

    private Resources() {
        reInit();
    }

    public static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }

    private void reInit() {
        dispose();

        uiSkin = new Skin(Gdx.files.internal("skin/skin.json"));

        atlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));
        whitePixelTexture = atlas.findRegion("white_pixel");

        background = atlas.findRegion("tlo");

        grass = atlas.createSprite("grass");
        dirt = atlas.createSprite("game_board");
        title = atlas.createSprite("title");
        bomb = atlas.createSprite("block_bomb");
        leftTankBody = atlas.createSprite("tank_1_body");
        leftTankTurret = atlas.createSprite("tank_1_turret");
        rightTankBody = atlas.createSprite("tank_2_body");
        rightTankTurret = atlas.createSprite("tank_2_turret");
        bullet = atlas.createSprite("ball");
        cellGreen = atlas.createSprite("block_green");
        cellYellow = atlas.createSprite("block_yellow");
        cellRed = atlas.createSprite("block_red");
        numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(atlas.createSprite(String.valueOf(i)));
        }
    }

    public void dispose() {
        atlas.dispose();
        uiSkin.dispose();
    }

    public Skin getUiSkin() {
        return uiSkin;
    }

    public TextureRegion getWhitePixelTexture() {
        return whitePixelTexture;
    }

    public Sprite getBomb() {
        return bomb;
    }

    public Sprite getLeftTankBody() {
        return leftTankBody;
    }

    public Sprite getLeftTankTurret() {
        return leftTankTurret;
    }

    public Sprite getRightTankBody() {
        return rightTankBody;
    }

    public Sprite getRightTankTurret() {
        return rightTankTurret;
    }

    public Sprite getBullet() {
        return bullet;
    }

    public Sprite getCellGreen() {
        return cellGreen;
    }

    public Sprite getCellYellow() {
        return cellYellow;
    }

    public Sprite getCellRed() {
        return cellRed;
    }

    public List<Sprite> getNumbers() {
        return numbers;
    }

    public TextureRegion getBackground() {
        return background;
    }

    public Sprite getTitle() {
        return title;
    }

    public Sprite getGrass() {
        return grass;
    }

    public Sprite getDirt() {
        return dirt;
    }
}
