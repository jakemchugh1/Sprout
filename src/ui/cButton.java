package ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.HEIGHT;

public class cButton {

    private String text;
    private float width;
    private float height;
    private float x;
    private float y;
    private float offset;
    private Font awtFont;
    private TrueTypeFont font;
    private DataDisplay data;

    public cButton(String text, float width, float height, float x, float y){
        System.out.println("cButton " + text);
        this.text = text;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        offset = 0;
        data = new DataDisplay();
        data.addLine("Hello");
        data.addLine("World");

        awtFont = new Font("times new roman", Font.BOLD, (int)height);
        font = new TrueTypeFont(awtFont, true);
    }

    public void draw(){

        font.drawString(x+offset, y, text);
    }

    public boolean hovering(){
        if(Mouse.getX() > x && Mouse.getX() < x + width && HEIGHT-Mouse.getY() > y && HEIGHT-Mouse.getY() < y+height){
            //DrawQuadTex(getTexture("fill_teal"), x, y, 24, height);
            //data.draw();

            return true;
        }else{
            return false;
        }
    }
    public void reset(){
        offset = 0;
    }

    public void addData(String in){
        data.addLine(in);
    }
}
