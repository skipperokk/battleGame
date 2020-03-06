package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Управление приложением
 */

public class MyGdxGame extends ApplicationAdapter {
    //batch - область отрисовки
    private SpriteBatch batch;
    private GameScreen gameScreen;

    //инициализация проекта
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.gameScreen = new GameScreen(batch);
        this.gameScreen.create();
    }

    @Override
    public void render() {
        gameScreen.render();
    }

    //освобождение ресурсов
    @Override
    public void dispose() {
        batch.dispose();
    }
}
