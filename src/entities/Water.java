package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static entities.EntityManager.getTexture;
import static org.newdawn.slick.Color.red;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.DrawQuadTexRotExpand;

public class Water {

    private float liters;
    private Vector2f position;
    private Font awtFont;
    private TrueTypeFont font;

    private float offset1;
    private float change;
    private float rotation;

    private boolean expand;



    public Water(float x, float y, float liters){
        awtFont = new Font(null, Font.PLAIN, 12);
        font = new TrueTypeFont(awtFont, true);
        position = new Vector2f();
        position.x = x;
        position.y = y;
        this.liters = liters;
        offset1 = 0;
        change = 1;
        rotation = 0;
        change = liters/20;
        expand = true;
    }

    public void Draw(){
        rotation = rotation + 1;
        if(rotation == 360) rotation = 0;
        change = liters/20;
        if(expand){
            offset1 = offset1 + change/20;
            if(offset1 > change) expand = false;
        }else{
            offset1 = offset1 - change/20;
            if(offset1 < -change) expand = true;
        }
        DrawQuadTexRotExpand(getTexture("WATER"), position.x, position.y, liters/2, liters/2, offset1, -offset1, 0);
        if(liters > 10){
            font.drawString(position.x+liters/2, position.y+liters/2, Math.round(liters) + "L" );
        }else{
            font.drawString(position.x+liters/2, position.y+liters/2, Math.round(liters*1000) + "mL");
        }
        liters = liters - 0.005f;

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
        liters = liters - 0.01f;
    }
}
