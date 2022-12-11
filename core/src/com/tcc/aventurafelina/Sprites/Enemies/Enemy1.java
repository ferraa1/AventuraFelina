package com.tcc.aventurafelina.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.PlayScreen;
import com.tcc.aventurafelina.Sprites.Player.Player;

public class Enemy1 extends Enemy {

    public Enemy1(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        currentState = previousState = State.STANDING;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy1"), 32, 0, 32, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy1"), 64, 0, 32, 32));
        enemyWalk = new Animation(0.2f, frames);
        frames.clear();
        enemyStand = new TextureRegion(screen.getAtlas().findRegion("enemy1"), 0, 0, 32, 32);
        enemyDead = new TextureRegion(screen.getAtlas().findRegion("enemy1"), 96, 0, 32, 32);

        defineEnemy();
        setBounds(getX(), getY(), 64 / Jogo.PPM, 64 / Jogo.PPM);
        setRegion(enemyStand);
    }

    @Override
    protected TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = enemyDead;
                break;
            case WALKING:
                region = (TextureRegion) enemyWalk.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = enemyStand;
                break;
        }

        if (velocity.x > 0 && !region.isFlipX())
            region.flip(true, false);
        if (velocity.x < 0 && region.isFlipX())
            region.flip(true, false);

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;
        return region;
    }

    @Override
    protected State getState() {
        if (setToDestroy)
            return State.DEAD;
        else if (b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }

    @Override
    public void update(float deltaTime) {
        setRegion(getFrame(deltaTime));
        if (setToDestroy && !destroyed) {
            destroyed = true;
            stateTimer = 0;
            Filter filter = new Filter();
            filter.maskBits = Jogo.GROUND_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 20 / Jogo.PPM);
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Jogo.PPM);
        fdef.filter.categoryBits = Jogo.ENEMY_BIT;
        fdef.filter.maskBits = Jogo.GROUND_BIT | Jogo.WALL_BIT | Jogo.SPIKES_BIT | Jogo.BARRIER_BIT | Jogo.END_BIT | Jogo.PLAYER_BIT | Jogo.ENEMY_BIT | Jogo.ITEM_BIT;

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

    @Override
    public void hitEnemy(Player player) {
        if (!setToDestroy) {
            if (player.getCurrentState() == Player.State.FALLING) {
                screen.getGame().getSoundManager().getManager().get(screen.getGame().getSoundManager().getStompSoundPath(), Sound.class).play();
                setToDestroy = true;
                player.getB2body().setLinearVelocity(player.getB2body().getLinearVelocity().x, 2.5f);
            } else {
                player.hit();
            }
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
