package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Landscape {
    public static final int CELLS_X = 16;
    public static final int CELLS_Y = 9;

    private Texture textureSpace;
    private Texture textureWall;
    private byte[][] data;


    public Landscape() {
        data = new byte[CELLS_X][CELLS_Y];
        data[2][2] = 1;
        textureSpace = new Texture("space.jpg");
        textureWall = new Texture("wall.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureSpace,0,0);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (data[i][j] == 1) {
                    batch.draw(textureWall, i*80, j*80);
                }
            }
        }
    }

}
