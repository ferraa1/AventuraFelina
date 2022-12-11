package com.tcc.aventurafelina.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveManager {

    private FileHandle handle;

    private int id, level;
    private String path, nome, data;

    public SaveManager() {
        path = "saves/partida";
    }

    public void save(int id, int level, String nome) {
        handle = Gdx.files.local(path + id);
        handle.writeString( new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(Calendar.getInstance().getTime()) + "=" + level + "=" + nome, false);
    }

    public void load(int id) {
        handle = Gdx.files.local(path + id);
        if (handle.exists()) {
            String[] wordsArray = handle.readString().split("=");
            this.id = id;
            level = Integer.parseInt(wordsArray[1]);
            nome = wordsArray[2];
            data = wordsArray[0];
        } else {
            nome = "VAZIO";
            data = "";
        }
    }

    public void delete(int id) {
        handle = Gdx.files.local(path + id);
        handle.delete();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public FileHandle getHandle() {
        return handle;
    }

    public void setHandle(FileHandle handle) {
        this.handle = handle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // </editor-fold>
}
