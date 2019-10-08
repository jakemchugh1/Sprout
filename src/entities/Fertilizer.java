package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;

public class Fertilizer {

    private float liters;
    private Vector2f position;
    private Font awtFont;
    private TrueTypeFont font;



    public Fertilizer(float x, float y, float liters){
        awtFont = new Font(null, Font.PLAIN, 12);
        font = new TrueTypeFont(awtFont, true);
        position = new Vector2f();
        position.x = x;
        position.y = y;
        this.liters = liters;
    }

    public void Draw(){
        DrawQuadTex(getTexture("POOP"), position.x-liters, position.y-liters, liters*2, liters*2);
        if(liters > 1){
            font.drawString(position.x+liters/2, position.y+liters/2, Math.round(liters) + "G" );
        }
        liters = liters - 0.01f;

    }

    public boolean empty(){
        if(liters <= 0){
            return true;
        }else return false;
    }

    public Vector2f getPosition(){
        return position;
    }

    public float getLiters(){
        return liters;
    }

    public void suckWater(){
        liters = liters - 2/60;
    }
}
