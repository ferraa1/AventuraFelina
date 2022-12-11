package com.tcc.aventurafelina.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.PlayScreen;
import com.tcc.aventurafelina.Sprites.Player.Player;

public abstract class Item extends Sprite {
    protected PlayScreen screen;

    World world;
    Body b2body;

    Vector2 velocity;
    private boolean toDestroy;
    boolean destroyed;

    Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / Jogo.PPM, 16 / Jogo.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();

    public abstract void use(Player player);

    public void update(float deltaTime) {
        if (toDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    void destroy() {
        toDestroy = true;
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
