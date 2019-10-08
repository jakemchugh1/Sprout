package entities;

import org.lwjgl.util.vector.Vector2f;

import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.DrawQuadTexRot;

public class Tree {

    private float width;
    private float height;
    private float tHeight;

    public Tree(){
        height = 0;
    }

    public void Draw(){
        if(height < 500) {
            DrawQuadTexRot(getTexture("SPROUT"), 640 - tHeight / 8, 935 - tHeight/2, tHeight/2, tHeight/2, 0);
        }else if(height < 1000){
            DrawQuadTexRot(getTexture("SAPLING"), 640 - tHeight / 8, 925 - tHeight/2, tHeight/2, tHeight/2, 0);
        }else{
            DrawQuadTexRot(getTexture("TREE"), 640 - tHeight / 8, 915 - tHeight/2, tHeight/2, tHeight/2, 0);
        }
    }

    public void setTreeHeight(float heightIn){
        height = heightIn;
        tHeight = 0.25f*height;
    }
}
