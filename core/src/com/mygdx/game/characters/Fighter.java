package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Fighter extends PersonsFunction {

    public Fighter(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("fighter.png");
        this.textureHealth = new Texture("bar.png");
        this.textureHealtghOutter = new Texture("bar_outter.png");
        this.position = new Vector2(200, 200);
        this.healthMax = 100.0f;
        this.health = this.healthMax;
        this.speed = 100.0f;
        this.weapon = new Weapon("Меч", 50.0f, 0.5f, 4.0f);
    }

    @Override
    public void update(float dt) {
        damageTimer -= dt;
        if (damageTimer < 0.0f)
            damageTimer = 0.0f;

        Enemy nearestEnemy = null;
        float minDist = Float.MAX_VALUE;
        for (int i = 0; i < gameScreen.getAllEnemies().size(); i++) {
            float dist = gameScreen.getAllEnemies().get(i).getPosition().dst(this.position);
            if (dist < minDist) {
                minDist = dist;
                nearestEnemy = gameScreen.getAllEnemies().get(i);
            }
        }

        if ( nearestEnemy != null && minDist < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                nearestEnemy.takeDamage(weapon.getDamage());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            position.x += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            position.x -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            position.y += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            position.y -= speed * dt;
        checkBoundsOfScreen();
    }
}
