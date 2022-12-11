package com.tcc.aventurafelina.Sprites.Player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.PlayScreen;

public class Player extends Sprite {

    private PlayScreen screen;

    public enum State {WALKING, STANDING, FALLING, JUMPING, DEAD, WON}
    private State currentState;
    private State previousState;

    private World world;
    private Body b2body;

    private Animation playerWalk;
    private TextureRegion playerStand;
    private TextureRegion playerFall;
    private TextureRegion playerJump;
    private TextureRegion playerDead;

    private float stateTimer;
    private boolean runningLeft;
    private boolean playerIsDead;
    private boolean playerHasWon;
    private float deadRotation;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;

        currentState = previousState = State.STANDING;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), 32, 0, 32, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), 64, 0, 32, 32));
        playerWalk = new Animation(0.2f, frames);
        frames.clear();
        playerStand = new TextureRegion(screen.getAtlas().findRegion("player"), 0, 0, 32, 32);
        playerFall = new TextureRegion(screen.getAtlas().findRegion("player"), 130, 0, 32, 32);
        playerJump = new TextureRegion(screen.getAtlas().findRegion("player"), 98, 0, 32, 32);
        playerDead = new TextureRegion(screen.getAtlas().findRegion("player"), 162, 0, 32, 32);

        definePlayer();
        setBounds(getX(), getY(), 64 / Jogo.PPM, 64 / Jogo.PPM);
        setRegion(playerStand);
    }

    public void update(float deltaTime) {
        setRegion(getFrame(deltaTime));

        if (getState() == State.DEAD && deltaTime != 0) {
            if (b2body.getLinearVelocity().x < 0)
                deadRotation += 5;
            else if (b2body.getLinearVelocity().x > 0)
                deadRotation -= 5;
            setRotation(deadRotation);
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 20 / Jogo.PPM);
    }

    private TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case WALKING:
                region = (TextureRegion) playerWalk.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerFall;
                break;
            case JUMPING:
                region = playerJump;
                break;
            case DEAD:
                region = playerDead;
                break;
            case WON:
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || runningLeft) && region.isFlipX()) {
            region.flip(true, false);
            runningLeft = true;
        } else if ((b2body.getLinearVelocity().x > 0 || !runningLeft) && !region.isFlipX()) {
            region.flip(true, false);
            runningLeft = false;
        }

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (playerIsDead)
            return State.DEAD;
        else if (playerHasWon)
            return State.WON;
        else if (b2body.getLinearVelocity().y > 0.2 || (b2body.getLinearVelocity().y > 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(480 / Jogo.PPM, 32 / Jogo.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Jogo.PPM);
        fdef.filter.categoryBits = Jogo.PLAYER_BIT;
        fdef.filter.maskBits = Jogo.GROUND_BIT | Jogo.WALL_BIT | Jogo.SPIKES_BIT | Jogo.END_BIT | Jogo.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape foot = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-10, -14).scl(1 / Jogo.PPM);
        vertice[1] = new Vector2(10, -14).scl(1 / Jogo.PPM);
        vertice[2] = new Vector2(-10, 14).scl(1 / Jogo.PPM);
        vertice[3] = new Vector2(10, 14).scl(1 / Jogo.PPM);
        foot.set(vertice);

        fdef.shape = foot;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void hit() {
        if (currentState != State.DEAD) {
            screen.getGame().getSoundManager().getManager().get(screen.getGame().getSoundManager().getDeathSoundPath(), Sound.class).play();
            playerIsDead = true;

            Filter filter = new Filter();
            filter.maskBits = Jogo.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);

            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 0);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public void won() {
        playerHasWon = true;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

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

    public Animation getPlayerWalk() {
        return playerWalk;
    }

    public void setPlayerWalk(Animation playerWalk) {
        this.playerWalk = playerWalk;
    }

    public TextureRegion getPlayerStand() {
        return playerStand;
    }

    public void setPlayerStand(TextureRegion playerStand) {
        this.playerStand = playerStand;
    }

    public TextureRegion getPlayerFall() {
        return playerFall;
    }

    public void setPlayerFall(TextureRegion playerFall) {
        this.playerFall = playerFall;
    }

    public TextureRegion getPlayerJump() {
        return playerJump;
    }

    public void setPlayerJump(TextureRegion playerJump) {
        this.playerJump = playerJump;
    }

    public TextureRegion getPlayerDead() {
        return playerDead;
    }

    public void setPlayerDead(TextureRegion playerDead) {
        this.playerDead = playerDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public boolean isRunningLeft() {
        return runningLeft;
    }

    public void setRunningLeft(boolean runningLeft) {
        this.runningLeft = runningLeft;
    }

    public boolean isPlayerIsDead() {
        return playerIsDead;
    }

    public void setPlayerIsDead(boolean playerIsDead) {
        this.playerIsDead = playerIsDead;
    }

    public boolean isPlayerHasWon() {
        return playerHasWon;
    }

    public void setPlayerHasWon(boolean playerHasWon) {
        this.playerHasWon = playerHasWon;
    }

    public float getDeadRotation() {
        return deadRotation;
    }

    public void setDeadRotation(float deadRotation) {
        this.deadRotation = deadRotation;
    }

    //</editor-fold>
}
