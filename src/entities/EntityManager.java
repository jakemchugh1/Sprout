package entities;

import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;

import static utilities.Artist.LoadTexture;

public class EntityManager {

    private static HashMap<String,Texture> textureBank;

    public EntityManager(){
        textureBank = new HashMap<>();

        load("DIRT");
        load("SEED");
        load("ROOT");
        load("POINTER");
        load("CIRCLE");
        load("WATER");
        load("GRASS");
        load("SKY");
        load("BACKGROUND");
        load("POOP");
        load("GREY");
        load("BLUE");
        load("GREEN");
        load("TREE");
        load("SAPLING");
        load("SPROUT");
        load("LEFTFLOWERS");
        load("RIGHTFLOWERS");
        load("BEEFLAPUP");
        load("BEEFLAPDOWN");
        load("SQUIRREL");
        load("HIVE");
        load("NUT");
        load("BEARSTEP1");
        load("BEARSTEP2");
        load("BIRD_ROOST");
        load("BIRD_FLYING");


    }

    public void load(String name) {
        textureBank.put(name, LoadTexture(name));
        System.out.println(name + " loaded");
    }

    public static Texture getTexture(String key){
        Texture temp = textureBank.get(key);
        return temp;
    }
}
