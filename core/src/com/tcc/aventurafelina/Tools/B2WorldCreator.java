package com.tcc.aventurafelina.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.PlayScreen;
import com.tcc.aventurafelina.Sprites.Enemies.Enemy;
import com.tcc.aventurafelina.Sprites.Enemies.Enemy1;
import com.tcc.aventurafelina.Sprites.Enemies.Enemy2;

public class B2WorldCreator {

    private World world;
    private TiledMap map;
    private BodyDef bdef;
    private PolygonShape shape;
    private FixtureDef fdef;

    private Array<Enemy> enemies;

    public B2WorldCreator(PlayScreen screen) {
        world = screen.getWorld();
        map = screen.getMap();
        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();
        enemies = new Array<Enemy>();

        //Ground
        createSimpleRect("Ground", Jogo.GROUND_BIT);

        //Wall
        createSimpleRect("Wall", Jogo.WALL_BIT);

        //Spikes
        createSimpleRect("Spikes", Jogo.SPIKES_BIT);

        //Barrier
        createSimpleRect("Barrier", Jogo.BARRIER_BIT);

        //End
        createSimpleRect("End", Jogo.END_BIT);

        //Enemy1
        Array<Enemy1> enemies1 = new Array<Enemy1>();
        for (MapObject object : map.getLayers().get("Enemy1").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies1.add(new Enemy1(screen, rect.getX() / Jogo.PPM, rect.getY() / Jogo.PPM));
        }
        for (Enemy enemy : enemies1)
            enemies.add(enemy);

        //Enemy2
        Array<Enemy2> enemies2 = new Array<Enemy2>();
        for (MapObject object : map.getLayers().get("Enemy2").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies2.add(new Enemy2(screen, rect.getX() / Jogo.PPM, rect.getY() / Jogo.PPM));
        }
        for (Enemy enemy : enemies2)
            enemies.add(enemy);
    }

    private void createSimpleRect(String name, final short bit) {
        for (MapObject object : map.getLayers().get(name).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Jogo.PPM, (rect.getY() + rect.getHeight() / 2) / Jogo.PPM);

            Body body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Jogo.PPM, rect.getHeight() / 2 / Jogo.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = bit;
            body.createFixture(fdef);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public BodyDef getBdef() {
        return bdef;
    }

    public void setBdef(BodyDef bdef) {
        this.bdef = bdef;
    }

    public PolygonShape getShape() {
        return shape;
    }

    public void setShape(PolygonShape shape) {
        this.shape = shape;
    }

    public FixtureDef getFdef() {
        return fdef;
    }

    public void setFdef(FixtureDef fdef) {
        this.fdef = fdef;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(Array<Enemy> enemies) {
        this.enemies = enemies;
    }

    // </editor-fold>
}
