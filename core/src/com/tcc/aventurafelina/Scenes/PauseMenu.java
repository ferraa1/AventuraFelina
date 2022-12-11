package com.tcc.aventurafelina.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.MainMenuScreen;
import com.tcc.aventurafelina.Screens.PlayScreen;

public class PauseMenu implements Disposable {

    private Jogo game;
    private PlayScreen screen;
    private Stage stage;
    private Viewport viewport;
    private boolean returnPressed;

    public PauseMenu(Jogo game, PlayScreen screen, Batch batch) {
        this.game = game;
        this.screen = screen;
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table tableCenter = new Table();
        tableCenter.center();
        tableCenter.setFillParent(true);

        Label titleLabel = new Label("JOGO PAUSADO", game.getTextStyleManager().getFontTitle());

        Label returnLabel = new Label("CONTINUAR", game.getTextStyleManager().getFont());
        returnLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                returnPressed = true;
                return true;
            }
        });

        Label saveLabel = new Label("SALVAR", game.getTextStyleManager().getFont());
        saveLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                saveGame();
                return true;
            }
        });

        Label saveExitLabel = new Label("SALVAR E SAIR", game.getTextStyleManager().getFont());
        saveExitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                saveGame();
                exitGame();
                return true;
            }
        });

        Label exitLabel = new Label("SAIR", game.getTextStyleManager().getFont());
        exitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitGame();
                return true;
            }
        });

        tableCenter.add(titleLabel).expandX();
        tableCenter.row();
        tableCenter.add(returnLabel).expandX().padTop(50f);
        tableCenter.row();
        tableCenter.add(saveLabel).expandX().padTop(10f);
        tableCenter.row();
        tableCenter.add(saveExitLabel).expandX().padTop(10f);
        tableCenter.row();
        tableCenter.add(exitLabel).expandX().padTop(10f);

        stage.addActor(tableCenter);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void saveGame() {
        game.getSaveManager().save(screen.getId(), screen.getLevel(), screen.getNome());
    }

    private void exitGame() {
        game.getSoundManager().getMusic().stop();
        game.setScreen(new MainMenuScreen(game));
        dispose();
        screen.dispose();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public Jogo getGame() {
        return game;
    }

    public void setGame(Jogo game) {
        this.game = game;
    }

    public PlayScreen getScreen() {
        return screen;
    }

    public void setScreen(PlayScreen screen) {
        this.screen = screen;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public boolean isReturnPressed() {
        return returnPressed;
    }

    public void setReturnPressed(boolean returnPressed) {
        this.returnPressed = returnPressed;
    }
    //</editor-fold>
}
