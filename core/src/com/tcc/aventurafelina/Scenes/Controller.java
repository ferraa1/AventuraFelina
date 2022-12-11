package com.tcc.aventurafelina.Scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;

public class Controller implements Disposable {

    private Stage stage;
    private Viewport viewport;
    private boolean pausePressed, leftPressed, rightPressed, aPressed, aPressing, bPressed, bPressing;

    public Controller(SpriteBatch batch) {
        viewport = new FitViewport(Jogo.V_WIDTH, Jogo.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table tableTop = new Table();
        tableTop.left().top();
        tableTop.setFillParent(true);

        Image pauseImg = new Image(new Texture("controller/pause.png"));
        pauseImg.setSize(50, 50);
        pauseImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = true;
                return true;
            }
        });

        tableTop.add(pauseImg).size(pauseImg.getWidth(), pauseImg.getHeight()).padLeft(20).padTop(20);

        stage.addActor(tableTop);

        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            Table tableBottom = new Table();
            tableBottom.bottom();
            tableBottom.setFillParent(true);

            Image leftImg = new Image(new Texture("controller/left.png"));
            leftImg.setSize(95, 95);
            leftImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    leftPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    leftPressed = false;
                }
            });

            Image rightImg = new Image(new Texture("controller/right.png"));
            rightImg.setSize(95, 95);
            rightImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    rightPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    rightPressed = false;
                }
            });

            Image aImg = new Image(new Texture("controller/a.png"));
            aImg.setSize(95, 95);
            aImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    aPressed = aPressing = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    aPressed = false;
                }
            });

            Image bImg = new Image(new Texture("controller/b.png"));
            bImg.setSize(95, 95);
            bImg.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    bPressed = bPressing = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    bPressed = false;
                }
            });

            tableBottom.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padBottom(20).padLeft(20);
            tableBottom.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padBottom(20).padLeft(20);
            tableBottom.add().expandX();
            tableBottom.add(aImg).size(aImg.getWidth(), aImg.getHeight()).padBottom(20).padRight(20);
            //tableBottom.add(bImg).size(bImg.getWidth(), bImg.getHeight()).padBottom(20).padRight(20);
            tableBottom.row();

            stage.addActor(tableBottom);
        }
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

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public boolean isPausePressed() {
        return pausePressed;
    }

    public void setPausePressed(boolean pausePressed) {
        this.pausePressed = pausePressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public void setaPressed(boolean aPressed) {
        this.aPressed = aPressed;
    }

    public boolean isaPressing() {
        return aPressing;
    }

    public void setaPressing(boolean aPressing) {
        this.aPressing = aPressing;
    }

    public boolean isbPressed() {
        return bPressed;
    }

    public void setbPressed(boolean bPressed) {
        this.bPressed = bPressed;
    }

    public boolean isbPressing() {
        return bPressing;
    }

    public void setbPressing(boolean bPressing) {
        this.bPressing = bPressing;
    }

    //</editor-fold>
}
