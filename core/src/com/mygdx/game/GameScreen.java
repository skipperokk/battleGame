package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.characters.Enemy;
import com.mygdx.game.characters.Fighter;
import com.mygdx.game.characters.PersonsFunction;

import java.util.*;

public class GameScreen {

    private SpriteBatch batch;
    private BitmapFont font14;
    private Fighter fighter;
    private Texture grass;
    private Landscape landscape;
    private List<PersonsFunction> allPersons;
    private List<Enemy> allEnemies;

    private Comparator<PersonsFunction> drawOrderComparator;

    public List<Enemy> getAllEnemies() {
        return allEnemies;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public void create() {
        landscape = new Landscape();
        allPersons = new ArrayList<>();
        allEnemies = new ArrayList<>();
        fighter = new Fighter(this);
        allPersons.addAll(Arrays.asList(
                fighter,
                new Enemy(this),
                new Enemy(this)
        ));
        for (int i = 0; i < allPersons.size(); i++) {
            if (allPersons.get(i) instanceof Enemy) {
                allEnemies.add((Enemy) allPersons.get(i));
            }
        }
        font14 = new BitmapFont(Gdx.files.internal("font.fnt"));
        grass = new Texture("space.jpg");

        // компаратор для сортировки персонажей по позиции (верх или низ)
        drawOrderComparator = new Comparator<PersonsFunction>() {
            @Override
            public int compare(PersonsFunction t1, PersonsFunction t2) {
                return (int) (t2.getPosition().y - t1.getPosition().y);
            }
        };
    }

    //отрисовка картинки (рендер 30 fps), работает циклично
    // сперва идет наша логика через метод update(); , потом происходит отрисовка
    // dt - дельта тайм, переменная необходимая для определения скорости передвижения персa при нестабильном FPS
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 1.0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
                landscape.render(batch);

        //отрисовка персонажа (если снизу, то на переднем плане, если сверху - на заднем)
        Collections.sort(allPersons, drawOrderComparator);
        for (int i = 0; i < allPersons.size(); i++) {
            allPersons.get(i).render(batch, font14);
        }
        batch.end();
    }

    public void update(float dt) {
        for (int i = 0; i < allPersons.size(); i++) {
            allPersons.get(i).update(dt);
        }
        // если монстра убили, мы его убираем из списка монстров и всех персов
        for (int i = 0; i < allEnemies.size(); i++) {
            Enemy currentEnemy = allEnemies.get(i);
            if (!currentEnemy.isAlive()) {
                allEnemies.remove(currentEnemy);
                allPersons.remove(currentEnemy);
            }
        }
    }
}
