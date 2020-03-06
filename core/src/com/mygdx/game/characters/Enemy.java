package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Enemy extends PersonsFunction {

    // вектор направления врага
    private Vector2 direction;
    // таймер подсчета времени для изменения направления движения врага
    private float moveTimer;
    //радиус видимости врага
    private float activeRadius;
    // временный вектор
    private Vector2 tmp;

    public Enemy(GameScreen gameScreen) {
        this.texture = new Texture("enemy.png");
        this.textureHealth = new Texture("bar.png");
        this.textureHealtghOutter = new Texture("bar_outter.png");
        this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        this.direction = new Vector2(0, 0);
        this.tmp = new Vector2(0, 0);
        this.speed = 40.0f;
        this.gameScreen = gameScreen;
        this.activeRadius = 200.0f;
        this.healthMax = 40;
        this.health = this.healthMax;
        this.weapon = new Weapon("Shout", 50.0f, 0.8f, 5.0f);

    }

    @Override
    public void update(float dt) {
        damageTimer -= dt;
        if (damageTimer < 0.0f)
            damageTimer = 0.0f;

        float destination = gameScreen.getFighter().getPosition().dst(this.position);
        // проверяем движение врага
        // позиция бойца относительно врага меньше активного радиуса врага
        if (destination < activeRadius) {
            tmp.set(gameScreen.getFighter().getPosition()).sub(this.position).nor();
            position.mulAdd(tmp, speed * dt);
        } else {
            // mul (умножение)
            //к позиции прибавляем движение со скоростью speed*dt
            position.mulAdd(direction, speed * dt);
            moveTimer -= dt;
            if (moveTimer < 0.0f) {
                // бросается "кубик" выкидывается число от 3 до 4, выпавшее число - время движения врага в
                // одном направлении
                moveTimer = MathUtils.random(1.0f, 4.0f);
                // когда таймер обнуляется, враг выбирает направление движения по х и у
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
                // нормировка вектора, он должен быть равен всегда 1
                direction.nor();
            }
        }
        // если враг настиг бойца, то уменьшает его здоровье
        if (destination < weapon.getAttackRadius()) {
            attackTimer += dt;
            if (attackTimer >= weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                gameScreen.getFighter().takeDamage(weapon.getDamage());
            }
        }
        checkBoundsOfScreen();
    }
}
