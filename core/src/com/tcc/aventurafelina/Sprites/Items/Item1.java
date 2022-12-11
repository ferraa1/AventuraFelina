package com.tcc.aventurafelina.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Screens.PlayScreen;
import com.tcc.aventurafelina.Sprites.Player.Player;

public class Item1 extends Item {//TODO: Ball

    public Item1(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("enemy1"), 0, 0, 32, 32);
        velocity = new Vector2(0.6f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Jogo.PPM);
        fdef.filter.categoryBits = Jogo.ITEM_BIT;
        fdef.filter.maskBits = Jogo.GROUND_BIT | Jogo.ENEMY_BIT | Jogo.PLAYER_BIT | Jogo.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        destroy();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            velocity.y = b2body.getLinearVelocity().y;
            b2body.setLinearVelocity(velocity);
        }
    }
}
