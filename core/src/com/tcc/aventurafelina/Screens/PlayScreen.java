package com.tcc.aventurafelina.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer; //TODO: remove debug import
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Scenes.Controller;
import com.tcc.aventurafelina.Scenes.Hud;
import com.tcc.aventurafelina.Scenes.PauseMenu;
import com.tcc.aventurafelina.Sprites.Enemies.Enemy;
import com.tcc.aventurafelina.Sprites.Items.Item;
import com.tcc.aventurafelina.Sprites.Items.Item1;
import com.tcc.aventurafelina.Sprites.Items.ItemDef;
import com.tcc.aventurafelina.Sprites.Player.Player;
import com.tcc.aventurafelina.Tools.B2WorldCreator;
import com.tcc.aventurafelina.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {

    private Jogo game;
    private Viewport gamePort;

    private TextureAtlas atlas;
    private OrthographicCamera gameCam;

    private Hud hud;
    private Controller controller;
    private PauseMenu menu;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    //private Box2DDebugRenderer b2dr;//TODO: remove debug thing1
    private B2WorldCreator creator;

    private Player player;
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    private int id, level;
    private String nome;
    private boolean paused;

    public PlayScreen(Jogo game, int id, int level, String nome) {
        this.game = game;
        this.id = id;
        this.level = level;
        this.nome = nome;

        atlas = new TextureAtlas("sprites/sprites.pack");

        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(Jogo.V_WIDTH / Jogo.PPM, Jogo.V_HEIGHT / Jogo.PPM, gameCam);

        hud = new Hud(game, game.getBatch(), level);
        controller= new Controller(game.getBatch());
        menu = new PauseMenu(game, this, game.getBatch());

        TmxMapLoader mapLoader = new TmxMapLoader();
        if (level == 10)
            map = mapLoader.load("maps/mapboss.tmx");
        else
            map = mapLoader.load("maps/map" + this.level + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Jogo.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -9.8f), true);
        //b2dr = new Box2DDebugRenderer();//TODO: remove debug thing2

        creator = new B2WorldCreator(this);

        player = new Player(this);

        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        if (level == 10)
            game.getSoundManager().musicPlay(game.getSoundManager().getBossMusicPath());
        else
            game.getSoundManager().musicPlay(game.getSoundManager().getGameMusicPath());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        if (paused)
            deltaTime = 0;

        update(deltaTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gameCam.combined);//TODO: remove debug renderer

        game.getBatch().setProjectionMatrix(gameCam.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.getBatch());
        for (Item item : items)
            item.draw(game.getBatch());
        game.getBatch().end();

        if (!paused) {
            game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().draw();
            game.getBatch().setProjectionMatrix(controller.getStage().getCamera().combined);
            controller.getStage().draw();
            Gdx.input.setInputProcessor(controller.getStage());
        } else {
            game.getBatch().setProjectionMatrix(menu.getStage().getCamera().combined);
            menu.getStage().draw();
            Gdx.input.setInputProcessor(menu.getStage());
        }

        if (gameOver()) {
            game.getSoundManager().getMusic().stop();
            game.setScreen(new GameOverScreen(game, this));
            dispose();
        } else if (nextLevel()) {
            game.getSoundManager().getMusic().stop();
            level++;
            /*long time = System.currentTimeMillis();
            while (System.currentTimeMillis() < time + 1000){}*/
            if (level > 10) {
                game.setScreen(new EndingScreen(game));
                dispose();
            } else {
                game.setScreen(new PlayScreen(game, id, level, nome));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        if (paused)
            menu.resize(width, height);
        else {
            controller.resize(width, height);
            hud.resize(width, height);
        }
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();//TODO: remove debug dispose
        hud.dispose();
        controller.dispose();
        menu.dispose();
    }

    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    private void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Item1.class) {
                items.add(new Item1(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void update(float deltaTime) {
        handleInput();
        handleSpawningItems();

        world.step(deltaTime, 6, 2);

        player.update(deltaTime);

        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(deltaTime);
            if (enemy.getX() < player.getX() + 448 / Jogo.PPM)
                enemy.getB2body().setActive(true);
        }

        for (Item item : items)
            item.update(deltaTime);

        if (player.getB2body().getPosition().y < 0 && player.getCurrentState() != Player.State.DEAD)
            player.hit();

        if (player.getCurrentState() != Player.State.DEAD)
            gameCam.position.x = player.getB2body().getPosition().x;
        else
            game.getSoundManager().getMusic().stop();

        if (paused && game.getSoundManager().getMusic().isPlaying()) {
            game.getSoundManager().getMusic().pause();
        } else if (!paused && !game.getSoundManager().getMusic().isPlaying() && player.getCurrentState() != Player.State.DEAD) {
            game.getSoundManager().getMusic().play();
        }

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput() {
        if (!paused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || controller.isPausePressed()) {
                pause();
                menu.setReturnPressed(false);
            }

            if (player.getCurrentState() != Player.State.WON && player.getCurrentState() != Player.State.DEAD) {

                //Keyboard
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && player.getB2body().getLinearVelocity().y == 0) {
                        player.getB2body().applyLinearImpulse(new Vector2(0, 3.5f), player.getB2body().getWorldCenter(), true);
                        game.getSoundManager().getManager().get(game.getSoundManager().getJumpSoundPath(), Sound.class).play();
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2body().getLinearVelocity().x <= 1.5)
                        player.getB2body().applyLinearImpulse(new Vector2(0.1f, 0), player.getB2body().getWorldCenter(), true);
                    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getLinearVelocity().x >= -1.5)
                        player.getB2body().applyLinearImpulse(new Vector2(-0.1f, 0), player.getB2body().getWorldCenter(), true);
                    if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) {

                    }
                }

                //Mobile
                if (controller.isaPressed() && controller.isaPressing() && player.getB2body().getLinearVelocity().y == 0) {
                    player.getB2body().applyLinearImpulse(new Vector2(0, 3.5f), player.getB2body().getWorldCenter(), true);
                    controller.setaPressing(false);
                    game.getSoundManager().getManager().get(game.getSoundManager().getJumpSoundPath(), Sound.class).play();
                }
                if (controller.isRightPressed() && player.getB2body().getLinearVelocity().x <= 1.5)
                    player.getB2body().applyLinearImpulse(new Vector2(0.1f, 0), player.getB2body().getWorldCenter(), true);
                else if (controller.isLeftPressed() && player.getB2body().getLinearVelocity().x >= -1.5)
                    player.getB2body().applyLinearImpulse(new Vector2(-0.1f, 0), player.getB2body().getWorldCenter(), true);
                if (controller.isbPressed() && controller.isbPressing()) {

                    controller.setbPressing(false);
                }
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || menu.isReturnPressed()) {
            resume();
            controller.setPausePressed(false);
        }
    }

    private boolean gameOver() {
        return player.getCurrentState() == Player.State.DEAD && player.getStateTimer() > 3;
    }

    private boolean nextLevel() {
        return player.getCurrentState() == Player.State.WON;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public Jogo getGame() {
        return game;
    }

    public void setGame(Jogo game) {
        this.game = game;
    }

    public Viewport getGamePort() {
        return gamePort;
    }

    public void setGamePort(Viewport gamePort) {
        this.gamePort = gamePort;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public void setGameCam(OrthographicCamera gameCam) {
        this.gameCam = gameCam;
    }

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public PauseMenu getMenu() {
        return menu;
    }

    public void setMenu(PauseMenu menu) {
        this.menu = menu;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public B2WorldCreator getCreator() {
        return creator;
    }

    public void setCreator(B2WorldCreator creator) {
        this.creator = creator;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Array<Item> getItems() {
        return items;
    }

    public void setItems(Array<Item> items) {
        this.items = items;
    }

    public LinkedBlockingQueue<ItemDef> getItemsToSpawn() {
        return itemsToSpawn;
    }

    public void setItemsToSpawn(LinkedBlockingQueue<ItemDef> itemsToSpawn) {
        this.itemsToSpawn = itemsToSpawn;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // </editor-fold>
}
