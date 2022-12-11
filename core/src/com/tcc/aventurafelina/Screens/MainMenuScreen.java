package com.tcc.aventurafelina.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;

public class MainMenuScreen implements Screen {

    private Jogo game;
    private Viewport viewport;
    private Stage stage;

    public MainMenuScreen(Jogo game) {
        this.game = game;
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("AVENTURA FELINA", game.getTextStyleManager().getFontTitle());

        Label newGameLabel = new Label("NOVO JOGO", game.getTextStyleManager().getFont());
        newGameLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                newGame();
                return true;
            }
        });

        Label loadGameLabel = new Label("CARREGAR JOGO", game.getTextStyleManager().getFont());
        loadGameLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                loadGame();
                return true;
            }
        });

        table.add(titleLabel).expandX();
        table.row();

        if (Gdx.files.local("saves/partida1").exists() && Gdx.files.local("saves/partida2").exists() && Gdx.files.local("saves/partida3").exists()) {
            table.add(loadGameLabel).expandX().padTop(50f);
        } else {
            table.add(newGameLabel).expandX().padTop(50f);
            table.row();
            table.add(loadGameLabel).expandX().padTop(10f);
        }

        stage.addActor(table);

        game.getSoundManager().musicPlay(game.getSoundManager().getMenuMusicPath());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        game.getBatch().draw(game.getTextStyleManager().getBackground(), 0, 0, Jogo.V_WIDTH, Jogo.V_HEIGHT);
        game.getBatch().end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void newGame() {
        game.setScreen(new NewMenuScreen(game));
        dispose();
    }

    private void loadGame() {
        game.setScreen(new LoadMenuScreen(game));
        dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public Jogo getGame() {
        return game;
    }

    public void setGame(Jogo game) {
        this.game = game;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // </editor-fold>
}
