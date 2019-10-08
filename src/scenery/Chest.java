package scenery;

import entities.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import particles.Particle;

import java.util.HashSet;
import java.util.Random;

import static entities.EntityManager.getTexture;
import static game.Game.mainParticles;
import static org.lwjgl.opengl.GL11.*;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.LoadTexture;
import static utilities.Artist.getFrameTimeSeconds;

public class Chest implements Scenery{

    private float x;
    private float y;

    private int height;
    private int width;

    private int frame;


    private Random rand;

    private boolean open;


    public Chest(float x, float y, int width, int height){
        frame = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rand = new Random();
        open = false;

    }

    public void Draw() {
        if(frame < 40){
            DrawQuadTex(getTexture("chest1"), x, y, width, height);
            frame = frame + 1;
        }else if(frame < 48 && open){
            DrawQuadTex(getTexture("chest2"), x, y, width, height);
            frame = frame + 1;
        }else{
            open = false;
            DrawQuadTex(getTexture("chest1"), x, y, width, height);
            frame = 0;
        }
        if(frame == 40 && rand.nextInt(10) == 1){
            open = true;
            mainParticles.add(new Particle(x+68,y+30,0,-1,7,32,32,"bubble_1", 2));
        }
        x = x - 4;

    }

    public int getFrame(){
        return frame;
    }

    public float getX(){
        return x;
    }
    public float getY() {return y-40;}
}
