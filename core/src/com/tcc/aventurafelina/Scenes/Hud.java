package com.tcc.aventurafelina.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;

public class Hud implements Disposable {

    private Stage stage;
    private Viewport viewport;

    public Hud(Jogo game, SpriteBatch batch, int level) {
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        String levelName;
        String mundo = "MUNDO";
        if (level < 4) {
            levelName = "1 - " + level;
        } else if (level < 7) {
            levelName = "2 - " + (level - 3);
        } else if (level < 10) {
            levelName = "3 - " + (level - 6);
        } else {
            mundo = "CHEFE";
            levelName = "";
        }

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label worldLabel = new Label(mundo, game.getTextStyleManager().getFont());
        Label levelLabel = new Label(levelName, game.getTextStyleManager().getFont());

        table.add(worldLabel).expandX().padTop(20);
        table.row();
        table.add(levelLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //</editor-fold>
}
