package com.tcc.aventurafelina.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class LoadMenuScreen implements Screen {

    private Jogo game;
    private Viewport viewport;
    private Stage stage;

    public LoadMenuScreen(Jogo game) {
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

        Label titleLabel = new Label("CARREGAR JOGO", game.getTextStyleManager().getFontTitle());

        String slot1 = "VAZIO";
        String data1 = "";
        String slot2 = "VAZIO";
        String data2 = "";
        String slot3 = "VAZIO";
        String data3 = "";

        if (Gdx.files.local("saves/partida1").exists()) {
            game.getSaveManager().load(1);
            slot1 = game.getSaveManager().getNome();
            data1 = game.getSaveManager().getData();
        }
        if (Gdx.files.local("saves/partida2").exists()) {
            game.getSaveManager().load(2);
            slot2 = game.getSaveManager().getNome();
            data2 = game.getSaveManager().getData();
        }
        if (Gdx.files.local("saves/partida3").exists()) {
            game.getSaveManager().load(3);
            slot3 = game.getSaveManager().getNome();
            data3 = game.getSaveManager().getData();
        }

        Label jogar1Label = new Label(slot1, game.getTextStyleManager().getFont());
        jogar1Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida1").exists()) {
                    play(1);
                }
                return true;
            }
        });
        Label delete1Label = new Label("X", new Label.LabelStyle(game.getTextStyleManager().getFont().font, Color.RED));
        delete1Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                delete(1);
                return true;
            }
        });
        Label data1Label = new Label(data1, game.getTextStyleManager().getFont());
        data1Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida1").exists()) {
                    play(1);
                }
                return true;
            }
        });

        Label jogar2Label = new Label(slot2, game.getTextStyleManager().getFont());
        jogar2Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida2").exists()) {
                    play(2);
                }
                return true;
            }
        });
        Label delete2Label = new Label("X", new Label.LabelStyle(game.getTextStyleManager().getFont().font, Color.RED));
        delete2Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                delete(2);
                return true;
            }
        });
        Label data2Label = new Label(data2, game.getTextStyleManager().getFont());
        data2Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida2").exists()) {
                    play(2);
                }
                return true;
            }
        });

        Label jogar3Label = new Label(slot3, game.getTextStyleManager().getFont());
        jogar3Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida3").exists()) {
                    play(3);
                }
                return true;
            }
        });
        Label delete3Label = new Label("X", new Label.LabelStyle(game.getTextStyleManager().getFont().font, Color.RED));
        delete3Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                delete(3);
                return true;
            }
        });
        Label data3Label = new Label(data3, game.getTextStyleManager().getFont());
        data3Label.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.files.local("saves/partida3").exists()) {
                    play(3);
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
        table.add(jogar1Label).padTop(50f);
        if (Gdx.files.local("saves/partida1").exists()) {
            table.add(delete1Label).padTop(50f).padLeft(-285f);
        }
        table.row();
        table.add(data1Label);
        table.row();
        table.add(jogar2Label).padTop(10f);
        if (Gdx.files.local("saves/partida2").exists()) {
            table.add(delete2Label).padTop(10f).padLeft(-285f);
        }
        table.row();
        table.add(data2Label);
        table.row();
        table.add(jogar3Label).padTop(10f);
        if (Gdx.files.local("saves/partida3").exists()) {
            table.add(delete3Label).padTop(10f).padLeft(-285f);
        }
        table.row();
        table.add(data3Label);
        table.row();
        table.add(backLabel).expandX().padTop(10f);

        /*tableBack.add().expandX().height(titleLabel.getHeight());
        tableBack.row();
        tableBack.add(emptyBackground1).expandX().height(60f).width(550f).padTop(45f);
        tableBack.row();
        tableBack.add(emptyBackground2).expandX().height(60f).width(550f).padTop(10f);
        tableBack.row();
        tableBack.add(emptyBackground3).expandX().height(60f).width(550f).padTop(10f);
        tableBack.row();
        tableBack.add().expandX().height(backLabel.getHeight()).padTop(10f);*/

        //stage.addActor(tableBack);
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

    private void play(int id) {
        game.getSaveManager().load(id);
        game.getSoundManager().getMusic().stop();
        game.setScreen(new PlayScreen(game, game.getSaveManager().getId(), game.getSaveManager().getLevel(), game.getSaveManager().getNome()));
        dispose();
    }

    private void delete(int id) {
        game.getSaveManager().delete(id);
        game.setScreen(new LoadMenuScreen(game));
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
