package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

// абстрактный класс нужен для того, чтобы во-первых объединение общих переменных и методов
// во-вторых - чтобы никто не смог создать объект этого класса

public abstract class PersonsFunction {
    // ссылка на игру необходима для врага, чтобы знать, где находится боец
    GameScreen gameScreen;
    Texture texture;
    Texture textureHealth;
    Texture textureHealtghOutter;
    //вектор движения персонажа (группировка х и у)
    Vector2 position;
    // speed = px per sec
    float speed;

    float health, healthMax;

    float damageTimer;
    float attackTimer;

    Weapon weapon;

    public boolean isAlive() {
        return health > 0;
    }
    public Vector2 getPosition() {
        return position;
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch, BitmapFont font14){
        if (damageTimer > 0.0f) {
            batch.setColor(1, 1 - damageTimer, 1- damageTimer, 1);
        }

        batch.draw(texture, position.x - 40, position.y - 40);
        batch.setColor(1, 1, 1, 1);

        batch.setColor(1, 0, 0, 1);
        batch.draw(textureHealth, position.x - 5 - 40, position.y + 80 - 40, 0, 0, health/healthMax*100, 20, 1, 1, 0, 0, 0, 100, 20, false, false);
        batch.setColor(1, 1, 1, 1);
        batch.draw(textureHealtghOutter, position.x - 45, position.y + 40);
        font14.draw(batch, String.valueOf((int) health), position.x-45, position.y + 80 -40, 80, 1, false);

    }

    //проверка границ экрана (не выходит ли боец и/или враг за пределы экрана)
    public void checkBoundsOfScreen() {
        if (position.x > 1280.0f)
            position.x = 1280.0f;
        if (position.x < 0.0f)
            position.x = 0.0f;
        if (position.y > 720.0f)
            position.y = 720.0f;
        if (position.y < 0.0f)
            position.y = 0.0f;
    }

    public void takeDamage(float amount) {
        health -= amount;
        damageTimer += 0.5f;
        if (damageTimer > 1.0f)
            damageTimer = 1.0f;
    }
}
