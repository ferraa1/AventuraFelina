package com.tcc.aventurafelina.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tcc.aventurafelina.Screens.PlayScreen;
import com.tcc.aventurafelina.Sprites.Player.Player;

public abstract class Enemy extends Sprite {

    PlayScreen screen;

    World world;
    Body b2body;

    Vector2 velocity;
    float stateTimer;

    public enum State {WALKING, STANDING, DEAD}
    State currentState;
    State previousState;

    Animation enemyWalk;
    TextureRegion enemyStand;
    TextureRegion enemyDead;

    boolean setToDestroy;
    boolean destroyed;

    Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0.5f, 0);
        b2body.setActive(false);
    }

    public abstract void update(float deltaTime);

    protected abstract TextureRegion getFrame(float deltaTime);

    protected abstract State getState();

    protected abstract void defineEnemy();

    public abstract void hitEnemy(Player player);

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public PlayScreen getScreen() {
        return screen;
    }

    public void setScreen(PlayScreen screen) {
        this.screen = screen;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getB2body() {
        return b2body;
    }

    public void setB2body(Body b2body) {
        this.b2body = b2body;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public Animation getEnemyWalk() {
        return enemyWalk;
    }

    public void setEnemyWalk(Animation enemyWalk) {
        this.enemyWalk = enemyWalk;
    }

    public TextureRegion getEnemyStand() {
        return enemyStand;
    }

    public void setEnemyStand(TextureRegion enemyStand) {
        this.enemyStand = enemyStand;
    }

    public TextureRegion getEnemyDead() {
        return enemyDead;
    }

    public void setEnemyDead(TextureRegion enemyDead) {
        this.enemyDead = enemyDead;
    }

    public boolean isSetToDestroy() {
        return setToDestroy;
    }

    public void setSetToDestroy(boolean setToDestroy) {
        this.setToDestroy = setToDestroy;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    //</editor-fold>
}
