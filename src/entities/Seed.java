package entities;

import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;

public class Seed {

    private float x;
    private float y;

    private float width;
    private float height;

    public Seed(){
        x = 642;
        y = 100;

        width = 32;
        height = 32;
    }

    public void Draw(){
        DrawQuadTex(getTexture("SEED"), x, y, width, height);
    }
}
