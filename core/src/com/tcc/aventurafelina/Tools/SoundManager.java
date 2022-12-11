package com.tcc.aventurafelina.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private AssetManager manager;
    private Music music;

    private String menuMusicPath, gameMusicPath, bossMusicPath, jumpSoundPath, stompSoundPath, deathSoundPath;

    public SoundManager() {
        menuMusicPath = "audio/music/musicmenu.ogg";
        gameMusicPath = "audio/music/musicgame.ogg";
        bossMusicPath = "audio/music/musicboss.ogg";
        jumpSoundPath = "audio/sounds/soundjump.wav";
        stompSoundPath = "audio/sounds/soundstomp.wav";
        deathSoundPath = "audio/sounds/sounddeath.wav";

        manager = new AssetManager();
        manager.load(menuMusicPath, Music.class);
        manager.load(gameMusicPath, Music.class);
        manager.load(bossMusicPath, Music.class);
        manager.load(jumpSoundPath, Sound.class);
        manager.load(stompSoundPath, Sound.class);
        manager.load(deathSoundPath, Sound.class);
        manager.finishLoading();

        music = manager.get(menuMusicPath, Music.class);
    }

    public void musicPlay(String path) {
        if (music != null) {
            if (!music.isPlaying()) {
                music = manager.get(path, Music.class);
                music.play();
                music.setLooping(true);
                music.setVolume(0.8f);
            }
        }
    }

    public void dispose() {
        manager.dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public AssetManager getManager() {
        return manager;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public String getMenuMusicPath() {
        return menuMusicPath;
    }

    public void setMenuMusicPath(String menuMusicPath) {
        this.menuMusicPath = menuMusicPath;
    }

    public String getGameMusicPath() {
        return gameMusicPath;
    }

    public void setGameMusicPath(String gameMusicPath) {
        this.gameMusicPath = gameMusicPath;
    }

    public String getBossMusicPath() {
        return bossMusicPath;
    }

    public void setBossMusicPath(String bossMusicPath) {
        this.bossMusicPath = bossMusicPath;
    }

    public String getJumpSoundPath() {
        return jumpSoundPath;
    }

    public void setJumpSoundPath(String jumpSoundPath) {
        this.jumpSoundPath = jumpSoundPath;
    }

    public String getStompSoundPath() {
        return stompSoundPath;
    }

    public void setStompSoundPath(String stompSoundPath) {
        this.stompSoundPath = stompSoundPath;
    }

    public String getDeathSoundPath() {
        return deathSoundPath;
    }

    public void setDeathSoundPath(String deathSoundPath) {
        this.deathSoundPath = deathSoundPath;
    }

    // </editor-fold>
}
