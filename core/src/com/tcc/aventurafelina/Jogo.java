package com.tcc.aventurafelina;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcc.aventurafelina.Screens.MainMenuScreen;
import com.tcc.aventurafelina.Tools.SaveManager;
import com.tcc.aventurafelina.Tools.SoundManager;
import com.tcc.aventurafelina.Tools.TextStyleManager;

public class Jogo extends Game {

    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 416;
    public static final float PPM = 200;

    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short WALL_BIT = 2;
    public static final short SPIKES_BIT = 4;
    public static final short BARRIER_BIT = 8;
    public static final short END_BIT = 16;
    public static final short PLAYER_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ITEM_BIT = 128;

    private SpriteBatch batch;

    private TextStyleManager textStyleManager;
    private SoundManager soundManager;
    private SaveManager saveManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        textStyleManager = new TextStyleManager();
        soundManager = new SoundManager();
        saveManager = new SaveManager();

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        soundManager.dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public TextStyleManager getTextStyleManager() {
        return textStyleManager;
    }

    public void setTextStyleManager(TextStyleManager textStyleManager) {
        this.textStyleManager = textStyleManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public void setSaveManager(SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    // </editor-fold>
}