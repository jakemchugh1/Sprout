package entities;

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
import static utilities.Artist.*;

public class Player implements Entity {

    private float x;
    private float y;
    private float rotation;
    private float rotationLimit;

    private int height;
    private int width;
    private int crew;

    private int speed;


    private Vector2f pos;
    private Vector2f vel;





    public Player(){
        crew = 5;

        speed = 200;
        width = 64;
        height = 32;
        rotation = 0;
        rotationLimit = 30;


        //initial position vector
        pos = new Vector2f();
        pos.x = x + width/2;
        pos.y = y + height/2;

        //initial velocity;
        vel = new Vector2f();
        vel.x = 0f;
        vel.y = 0f;


    }

    public void Draw() {
    }

    public void setPos() {
        checkInput();
    }

    public Vector2f getPos() {
        return pos;
    }

    public void ApplyForce(Vector2f force) {

    }

    public boolean isRemove() {
        return false;
    }

    public void checkInput(){

    }

    @Override
    public boolean checkColliding(Entity entity) {
        return false;
    }

    public void setRemove() {}

    public int getCrew(){
        return crew;
    }
}
