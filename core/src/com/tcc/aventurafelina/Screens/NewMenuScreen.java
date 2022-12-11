package com.tcc.aventurafelina.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;

public class NewMenuScreen implements Screen {

    private Jogo game;
    private Viewport viewport;
    private Stage stage;

    public NewMenuScreen(Jogo game) {
        this.game = game;
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Table tableBack = new Table();
        tableBack.center();
        tableBack.setFillParent(true);

        Label titleLabel = new Label("NOVO JOGO", game.getTextStyleManager().getFontTitle());

        final TextField nameField = new TextField("insira um nome", game.getTextStyleManager().getTextFieldStyle());
        nameField.setAlignment(1);
        nameField.setMaxLength(16);
        nameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                nameField.setText("");
                return true;
            }
        });

        Label jogarLabel = new Label("JOGAR", game.getTextStyleManager().getFont());
        jogarLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (nameField.getText().length() > 0 && !nameField.getText().equals("insira um nome")) {
                    play(nameField.getText());
                }
                return true;
            }
        });

        Label backLabel = new Label("VOLTAR", game.getTextStyleManager().getFont());
        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back();
                return true;
            }
        });

        table.add(titleLabel).expandX();
        table.row();
        table.add(nameField).expandX().width(350f).padTop(50f);
        table.row();
        table.add(jogarLabel).expandX().padTop(10f);
        table.row();
        table.add(backLabel).expandX().padTop(10f);

        tableBack.add().expandX().height(titleLabel.getHeight());
        tableBack.row();
        tableBack.add(new Image(game.getTextStyleManager().getEmptyBackground())).expandX().width(450f).padTop(50f);
        tableBack.row();
        tableBack.add().expandX().height(jogarLabel.getHeight()).padTop(10f);
        tableBack.row();
        tableBack.add().expandX().height(backLabel.getHeight()).padTop(10f);

        stage.addActor(tableBack);
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

        stage.getBatch().begin();
        stage.getBatch().draw(game.getTextStyleManager().getBackground(), 0, 0, Jogo.V_WIDTH, Jogo.V_HEIGHT);
        stage.getBatch().end();

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

    private void play(String nome) {
        game.getSoundManager().getMusic().stop();
        int id = 0;
        for (int i = 1; i < 4; ++i) {
            if (!Gdx.files.local(game.getSaveManager().getPath() + i).exists()) {
                id = i;
                break;
            }
        }
        game.setScreen(new PlayScreen(game, id, 1, nome));
        dispose();
    }

    private void back() {
        game.setScreen(new MainMenuScreen(game));
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
