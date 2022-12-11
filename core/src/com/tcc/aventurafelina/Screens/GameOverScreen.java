package com.tcc.aventurafelina.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;

public class GameOverScreen implements Screen {

    private Jogo game;
    private Viewport viewport;
    private Stage stage;

    private PlayScreen screen;

    public GameOverScreen(Jogo game, PlayScreen screen) {
        this.game = game;
        this.screen = screen;
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("FIM DE JOGO", game.getTextStyleManager().getFontTitle());
        Label playAgainLabel;
        if (Gdx.app.getType() != Application.ApplicationType.Desktop)
            playAgainLabel = new Label("toque na tela para tentar novamente", game.getTextStyleManager().getFont());
        else
            playAgainLabel = new Label("pressione qualquer tecla para tentar novamente", game.getTextStyleManager().getFont());

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(50f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new PlayScreen(game, screen.getId(), screen.getLevel(), screen.getNome()));
            dispose();
        }
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

    public PlayScreen getScreen() {
        return screen;
    }

    public void setScreen(PlayScreen screen) {
        this.screen = screen;
    }
    // </editor-fold>
}
