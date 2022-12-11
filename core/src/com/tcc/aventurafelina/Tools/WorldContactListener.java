package com.tcc.aventurafelina.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tcc.aventurafelina.Jogo;
import com.tcc.aventurafelina.Sprites.Enemies.Enemy;
import com.tcc.aventurafelina.Sprites.Items.Item;
import com.tcc.aventurafelina.Sprites.Player.Player;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case Jogo.PLAYER_BIT | Jogo.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.PLAYER_BIT)
                    ((Enemy)fixB.getUserData()).hitEnemy((Player) fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).hitEnemy((Player) fixB.getUserData());
                break;
            case Jogo.PLAYER_BIT | Jogo.SPIKES_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit();
                else
                    ((Player) fixB.getUserData()).hit();
                break;
            case Jogo.PLAYER_BIT | Jogo.END_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.PLAYER_BIT)
                    ((Player) fixA.getUserData()).won();
                else
                    ((Player) fixB.getUserData()).won();
                break;
            case Jogo.ENEMY_BIT | Jogo.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Jogo.ENEMY_BIT | Jogo.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Jogo.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Jogo.ITEM_BIT:
                ((Item)fixA.getUserData()).reverseVelocity(true, false);
                ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Jogo.ENEMY_BIT | Jogo.ITEM_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                }
                break;
            case Jogo.ITEM_BIT | Jogo.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Jogo.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
