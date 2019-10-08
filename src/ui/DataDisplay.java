package ui;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;

import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;

public class DataDisplay {

    private float x;
    private float y;
    private ArrayList<String> lines;

    private Font awtFont;
    private TrueTypeFont font;

    public DataDisplay(){
        x = 700;
        y = 0;
        lines = new ArrayList<>();

        awtFont = new Font("times new roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);
    }

    public void draw(){
        DrawQuadTex(getTexture("GREY"), x, y, 100, 400);
        int i = 0;
        for(String s : lines){
            font.drawString(x, 24*i, s);
            i = i + 1;
        }
    }

    public void addLine(String lineIn){
        lines.add(lineIn);
    }
}
