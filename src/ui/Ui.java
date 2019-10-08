package ui;

import game.Game;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import particles.Particle;

import java.awt.*;

import static entities.EntityManager.getTexture;
import static game.Game.mainParticles;
import static utilities.Artist.*;
import static utilities.Artist.DrawQuadTexRot;

public class Ui {

    private Font awtFont;
    private TrueTypeFont font;
    private boolean resetGame;
    private int menuSelect;



    private boolean buttonHold;
    private float textRotator;

    private int frame;



    public Ui(){
        awtFont = new Font("times new roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);
        resetGame = false;
        menuSelect = 0;
        textRotator = 0;
        frame = 0;
        //main menu buttons



        buttonHold = false;

    }
    public void run(Game game){
        game.play();
    }

    public void customPlayer(){

    }

    public void mainMenu(){

        textRotator = textRotator + 0.125f;
        if(textRotator == 360) textRotator = 0;



    }


    public void inGame(Game game){

    }
    public boolean getReset(){
        return resetGame;
    }
    public void newGame(){
        resetGame = false;
    }
    public int getMenuSelect(){
        return menuSelect;
    }
}
