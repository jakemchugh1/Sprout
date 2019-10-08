package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static entities.EntityManager.getTexture;
import static game.Game.*;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.DrawQuadTexRot;

public class Root {


    private Vector2f start;
    private Vector2f end;

    private float width;
    private float height;

    private float midX;
    private float midY;

    private float rotation;

    private boolean placed;
    private boolean expand;

    private float MAX_LENGTH;


    public Root(float xIn, float yIn){
        placed = false;
        start = new Vector2f();
        end = new Vector2f();
        start.x = xIn;
        start.y = yIn;

        end.x = 642;
        end.y = 100;

        width = 32;
        height = 128;
        rotation = 0;
        MAX_LENGTH = 200;
    }

    public void Draw(){
        if(!placed) {
            end.x = Mouse.getX();
            end.y = 960 - Mouse.getY();

            float deltaX = end.x - start.x;
            float deltaY = end.y - start.y;

            float magnitude = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if(magnitude > MAX_LENGTH){
                deltaX = deltaX / magnitude;
                deltaY = deltaY / magnitude;
                deltaX = deltaX * MAX_LENGTH;
                deltaY = deltaY * MAX_LENGTH;
                magnitude = MAX_LENGTH;
                end.x = start.x + deltaX;
                end.y = start.y + deltaY;
            }

            midX = start.x + deltaX/3;
            midY = start.y + deltaY/3;

            deltaX = deltaX / magnitude;
            deltaY = deltaY / magnitude;

            height = -magnitude/2;

            width = 200 / height;

            if(width > 6) width = 6;
            if(width < -6) width = -6;


            if (deltaX > 0) {
                rotation = (float) -(Math.toDegrees(Math.atan(((deltaY) / deltaX)))) + 90 + 180;

            } else {
                rotation = (float) -(Math.toDegrees(Math.atan(((deltaY) / deltaX)))) + 90;
                rotation = 180 + rotation + 180;

            }
        }
        if(!placed && Mouse.isButtonDown(0) && !mousePressed){
            place();
            leaf.play(1.2f,0.5f);
            mousePressed = true;
            nodeSelected = false;
        }if(!Mouse.isButtonDown(0) && mousePressed){
            mousePressed = false;
        }if(placed){
            if(expand) {

                DrawQuadTex(getTexture("CIRCLE"), end.x - 9, end.y - 9, 18, 18);
            }else{
                DrawQuadTex(getTexture("CIRCLE"), end.x - 8, end.y - 8, 16, 16);
            }
        }




        DrawQuadTexRot(getTexture("ROOT"), midX, midY, width, height, rotation);
    }

    public void place(){
        placed = true;
    }

    public Vector2f getEnd(){
        return end;
    }

    public void setExpand(boolean expand){
        this.expand = expand;
    }

    public boolean getPlaced(){
        return placed;
    }

}
