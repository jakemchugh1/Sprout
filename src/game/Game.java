package game;

import entities.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import particles.Particle;
import ui.cButton;

import java.awt.Font;
import java.util.HashSet;
import java.util.Random;

import static engine.GameEngine.exitGame;
import static entities.EntityManager.getTexture;
import static utilities.Artist.DrawQuadTex;
import static utilities.Artist.DrawQuadTexRot;


public class Game {
    public static EntityManager entityManager;

    public static HashSet<Particle> mainParticles = new HashSet<>();

    private int score;


    private boolean gameOver;
    public static boolean mousePressed;
    public static boolean nodeSelected;


    private Random rand;

    HashSet<Particle> removeP;
    public HashSet<Root> roots;
    public static HashSet<Root> addRoots;
    public HashSet<Water> water;
    public HashSet<Water> removeWater;
    public HashSet<Fertilizer> poop;
    public HashSet<Fertilizer> removePoop;

    private Seed seed;

    private float seedWater;
    private Font awtFont;
    private TrueTypeFont font;
    private Font tempawtFont;
    private TrueTypeFont tempfont;

    private float treeHeight;
    private float sustainmentCost;

    private boolean treeMenu;
    private boolean tPressed;

    private Tree tree;

    private float fertilizer;

    private boolean hasMature;

    private boolean hasStrongBranches;

    private boolean hasNuts;

    private boolean hasSquirrels;

    private boolean hasBirds;

    private boolean hasFlowers;

    private boolean hasBees;

    private boolean hasBears;

    private int maturityLevel;

    private boolean cheats;

    private boolean beeFlap;

    private boolean beeDirection;

    private float beeDistance;

    private boolean squirrelDirection;

    private float squirrelDistance;

    private boolean bearDirection;

    private float bearDistance;

    private int bearFrame;

    private float birdDistance;

    private boolean dormant;

    public static Music ambientSounds;
    public static Sound pop;
    public static Sound leaf;
    public static Sound poot;
    public static Sound splash;





    public Game(){
        resetGame();
    }

    public void play(){
        if(seedWater <=0) dormant = true;

        ////cheats///////
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            cheats = true;
            System.out.println("Cheats Enabled");
        }
        if(cheats)cheats();
        ////////
        if(treeMenu){
            treeMenu();
        }else{
            inGame();
        }
        DrawQuadTex(getTexture("GREY"), 0, 0, 320, 110);
        if(treeHeight > 100) {
            font.drawString(0, 0, "Tree Height: " + Math.round(treeHeight) / 100 + "m");
        }else{
            font.drawString(0, 0, "Tree Height: " + treeHeight  + "cm");
        }

        if(sustainmentCost > 10){
            font.drawString(0,20, "Water Cost: -" + Math.round(sustainmentCost) + "L");
        }else{
            font.drawString(0,20, "Water Cost: -" + Math.round(sustainmentCost*1000) + "mL");
        }
        if(seedWater > 10){
            font.drawString(0,40, "Stored Water: " + Math.round(seedWater) + "L");
        }else{
            font.drawString(0, 40, "Stored Water: " + Math.round(seedWater*1000) + "mL");
        }
        font.drawString(0, 60, "Fertilizer: " + Math.round(fertilizer) + "g");
        font.drawString(0, 80, "Tree Maturity Level: " + maturityLevel);
        DrawQuadTex(getTexture("GREY"), 540, 0, 100, 30);
        if(Mouse.getX() > 540 && Mouse.getX() < 640 && 960-Mouse.getY() < 30){
            DrawQuadTex(getTexture("BLUE"), 540, 0, 100, 30);
            if(!mousePressed && Mouse.isButtonDown(0)){
                resetGame();
            }
        }
        font.drawString(550, 0, "Restart");
        DrawQuadTex(getTexture("GREY"), 640, 0, 100, 30);
        if(Mouse.getX() > 640 && Mouse.getX() < 740 && 960-Mouse.getY() < 30){
            DrawQuadTex(getTexture("BLUE"), 640, 0, 100, 30);
            if(!mousePressed && Mouse.isButtonDown(0)){
                exitGame = true;
            }
        }
        font.drawString(660, 0, "Quit");
        if(!dormant)font.drawString(0, 930, "Press 'T' to toggle between tree menu and ground" );
        DrawQuadTex(getTexture("POINTER"), Mouse.getX(), 960-Mouse.getY(), 16, 16);
        if(dormant) {
            treeMenu = true;
            if(treeHeight > 100) {
                font.drawString(320, 80, "Congratulations! Your tree reached the height of " + Math.round(treeHeight) / 100 + "m");
            }else{
                font.drawString(320, 80, "Congratulations! Your tree reached the height of " + treeHeight  + "cm");
            }
            font.drawString(320, 100, "Due to a lack of water, your tree has gone dormant." );
            font.drawString(320, 120, "Please restart or enjoy the environment you have created." );
        }

    }

    public void resetGame(){

        dormant = false;
        beeDirection = true;
        beeDistance = 0;
        squirrelDirection = true;
        squirrelDistance = -100;
        bearDirection = false;
        bearDistance = 0;
        bearFrame = 0;
        birdDistance = 0;
        boolean hasMature = false;

        boolean hasStrongBranches = false;

        boolean hasNuts = false;

        boolean hasSquirrels = false;

        boolean hasBirds = false;

        boolean hasFlowers = false;

        boolean hadBees = false;

        boolean hasBears = false;
        maturityLevel = 1;
        cheats = false;


        rand = new Random();
        seedWater = 2;
        treeHeight = 0.0006f;
        treeMenu = true;
        tree = new Tree();
        nodeSelected = false;
        poop = new HashSet<>();
        removePoop = new HashSet<>();

        awtFont = new Font(null, Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);

        tempawtFont = new Font(null, Font.PLAIN, 12);
        tempfont = new TrueTypeFont(tempawtFont, true);


        entityManager = new EntityManager();
        roots = new HashSet<>();
        addRoots = new HashSet<>();
        mousePressed = false;
        water = new HashSet<>();
        removeWater = new HashSet<>();

        beeFlap = true;



        ambientSounds = null;
        pop = null;
        leaf = null;
        poot = null;
        splash = null;

        try {
            ambientSounds = new Music("res/audio/ambience.wav");
            pop = new Sound("res/audio/pop.wav");
            leaf = new Sound("res/audio/leaf.wav");
            poot = new Sound("res/audio/poot.wav");
            splash = new Sound("res/audio/water.wav");


        } catch (SlickException e) {
            e.printStackTrace();
        }



        seed = new Seed();

        roots.add(new Root(658, 115));

        rand = new Random();

        score = 0;
        fertilizer = 10;
        gameOver = false;


        removeP = new HashSet<>();


        ambientSounds.loop();



    }

    private void checkEnds(Root rootIn){
        ///////checking mouse
        float deltaX = rootIn.getEnd().x - Mouse.getX();
        float deltaY = rootIn.getEnd().y - (960-Mouse.getY());
        float magnitude = (float) Math.sqrt((deltaX*deltaX + deltaY*deltaY));
        if(magnitude < 16 && !nodeSelected){
            rootIn.setExpand(true);
            if(Mouse.isButtonDown(0) && !mousePressed && fertilizer > 0) {
                pop.play();
                addRoots.add(new Root(rootIn.getEnd().x, rootIn.getEnd().y));
                mousePressed = true;
                nodeSelected = true;
                fertilizer = fertilizer - 1;
            }
        }else{
            rootIn.setExpand(false);
        }
        ////////checking if roots can absorb water
        for(Water w : water){
            float dX = w.getPosition().x - rootIn.getEnd().x;
            float dY = w.getPosition().y - rootIn.getEnd().y;
            float m = (float) Math.sqrt(dX * dX + dY * dY);



            if(rootIn.getPlaced()){
                if (m <= 16) {
                    w.suckWater();
                    seedWater = seedWater + 0.01f;

                }else{
                    dX = dX/m;
                    dY = dY/m;

                    dX = dX*16;
                    dY = dY*16;

                    if(rootIn.getEnd().x + dX > w.getPosition().x-(w.getLiters()/2) && rootIn.getEnd().x + dX < w.getPosition().x + (w.getLiters()/2) && rootIn.getEnd().y + dY > w.getPosition().y - (w.getLiters()/2) && rootIn.getEnd().y + dY < w.getPosition().y + (w.getLiters()/2)){
                        w.suckWater();
                        seedWater = seedWater + 0.01f;
                    }
                }
            }
        }
        for(Fertilizer w : poop) {
            float dX = w.getPosition().x - rootIn.getEnd().x;
            float dY = w.getPosition().y - rootIn.getEnd().y;
            float m = (float) Math.sqrt(dX * dX + dY * dY);


            if (rootIn.getPlaced()) {
                if (m <= 32) {
                    w.suckWater();
                    fertilizer = fertilizer + 0.017f;

                } else {
                    dX = dX / m;
                    dY = dY / m;

                    dX = dX * 16;
                    dY = dY * 16;

                    if (rootIn.getEnd().x + dX > w.getPosition().x - (w.getLiters()) && rootIn.getEnd().x + dX < w.getPosition().x + (w.getLiters()) && rootIn.getEnd().y + dY > w.getPosition().y - (w.getLiters()) && rootIn.getEnd().y + dY < w.getPosition().y + (w.getLiters())) {
                        w.suckWater();
                        fertilizer = fertilizer + 0.017f;
                    }
                }
            }
        }

    }
    private void tree(){
        ////growth
        if(seedWater > 2) {
                treeHeight = treeHeight * (1+(0.0006f/maturityLevel));
        }
        /////sustainment cost
            sustainmentCost =  (0.0006f/maturityLevel) * treeHeight;

        seedWater = seedWater - sustainmentCost;
    }

    private void inGame(){
        ////////Adding game elements//////
        if(rand.nextInt(100) == 20){
            water.add(new Water(90+(float)rand.nextInt(1100), (float)100+rand.nextInt(800), (float)rand.nextInt(100)));
            splash.play(1, 0.5f);
        }
        if(rand.nextInt(500) == 25){
            poop.add(new Fertilizer(90+(float)rand.nextInt(1100), (float)100+rand.nextInt(800), (float)rand.nextInt(20)));
            poot.play(1.2f, 0.5f);
        }
        if(hasSquirrels){
            if(rand.nextInt(500) == 30){
                poop.add(new Fertilizer(90+(float)rand.nextInt(1100), (float)100+rand.nextInt(800), (float)rand.nextInt(20)));
                poot.play(1.2f, 0.5f);
            }
        }
        if(hasBirds){
            if(rand.nextInt(500) == 40){
                poop.add(new Fertilizer(90+(float)rand.nextInt(1100), (float)100+rand.nextInt(800), (float)rand.nextInt(20)));
                poot.play(1.2f, 0.5f);
            }
        }
        if(hasBears){
            if(rand.nextInt(500) == 50){
                poop.add(new Fertilizer(90+(float)rand.nextInt(1100), (float)100+rand.nextInt(800), (float)rand.nextInt(100)));
                poot.play(0.8f, 0.5f);
            }
        }




        //////////Background/////////////
        for(int i = 0 ; i < 40 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                DrawQuadTex(getTexture("DIRT"), i*32, j*32, 64, 64);
            }
        }
        /////////ROOT//////////////
        for(Root r : roots){
            r.Draw();
            checkEnds(r);

        }
        for(Root r : addRoots){
            roots.add(r);
        }addRoots.clear();

        //////////SEED///////
        seed.Draw();

        //////////Water//////
        for(Water w : water){
            w.Draw();
            if(w.empty()) removeWater.add(w);
        }
        for(Water w : removeWater){
            water.remove(w);
        }removeWater.clear();
        /////////Fertilizer/////
        for(Fertilizer w : poop){
            w.Draw();
            if(w.empty()) removePoop.add(w);
        }
        for(Fertilizer w : removePoop){
            poop.remove(w);
        }removePoop.clear();




        ////reset mouse if not being pressed
        if(!Mouse.isButtonDown(0) && mousePressed){
            mousePressed = false;
        }



        ///////Tree growth and water consumption

        tree();
        if(Keyboard.isKeyDown(Keyboard.KEY_T) && !tPressed && !dormant){
            treeMenu = true;
            tPressed = true;
        }
        else{
            if(tPressed && !Keyboard.isKeyDown(Keyboard.KEY_T) ){
                tPressed = false;
            }
        }
    }

    private void treeMenu(){
        //////////Ground/////////////
        /*for(int i = 0 ; i < 40 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                DrawQuadTex(getTexture("SKY"), i*32, j*32, 64, 64);
            }
        }*/
        DrawQuadTex(getTexture("SKY"), 0, 0, 1280, 960);
        DrawQuadTex(getTexture("BACKGROUND"), 0, 128, 2200, 959);


        if(hasSquirrels){
            DrawQuadTexRot(getTexture("TREE"), 1000, 720, 32, 32, -10);
            DrawQuadTexRot(getTexture("TREE"), 940, 740, 32, 32, 5);
            DrawQuadTexRot(getTexture("TREE"), 910, 760, 32, 32, 0);
            DrawQuadTexRot(getTexture("TREE"), 860, 740, 32, 64, 0);

            DrawQuadTexRot(getTexture("SAPLING"), 1100, 700, 32, 32, 0);
            DrawQuadTexRot(getTexture("SAPLING"), 820, 740, 32, 32, 0);
            DrawQuadTexRot(getTexture("SAPLING"), 950, 720, 32, 32, 0);
            DrawQuadTexRot(getTexture("SAPLING"), 820, 740, 32, 80, 3);
            DrawQuadTexRot(getTexture("TREE"), 790, 720, 64, 80, -3);
            DrawQuadTexRot(getTexture("TREE"), 740, 740, 64, 90, 0);

            DrawQuadTexRot(getTexture("SPROUT"), 1150, 700, 16, 16, 0);
            DrawQuadTexRot(getTexture("SPROUT"), 1210, 720, 16, 16, 0);
            DrawQuadTexRot(getTexture("SPROUT"), 1220, 690, 16, 16, 0);

            DrawQuadTexRot(getTexture("SAPLING"), 140, 740, 128, 256, 0);
            DrawQuadTexRot(getTexture("SAPLING"), 290, 700, 128, 256, 2);
            DrawQuadTexRot(getTexture("TREE"), 230, 740, 128, 256, -3);
            DrawQuadTexRot(getTexture("TREE"), 100, 740, 128, 256, 0);
            DrawQuadTexRot(getTexture("SPROUT"), 420, 880, 64, 64, 0);
            DrawQuadTexRot(getTexture("SPROUT"), 490, 885, 64, 64, 0);

            DrawQuadTexRot(getTexture("SPROUT"), 970, 810, 54, 54, 0);
            DrawQuadTexRot(getTexture("SPROUT"), 940, 820, 54, 54, 0);
        }if(hasBees){
            DrawQuadTexRot(getTexture("HIVE"), 1120, 900, 32, 52, 0);
            if(beeFlap){
                if(beeDirection){
                    DrawQuadTexRot(getTexture("BEEFLAPDOWN"), 980+beeDistance, (float) (880+2*Math.sin(beeDistance)), 8, 8, 0);
                    beeDistance = beeDistance + 0.3f;
                }else{
                    DrawQuadTexRot(getTexture("BEEFLAPDOWN"), 980+beeDistance, (float) (880+2*Math.sin(beeDistance)), -8, 8, 0);
                    beeDistance = beeDistance - 0.3f;
                }                beeFlap = false;

            }else{
                if(beeDirection){
                    DrawQuadTexRot(getTexture("BEEFLAPUP"), 980+beeDistance, (float) (880+2*Math.sin(beeDistance)), 8, 8, 0);
                    beeDistance = beeDistance + 0.3f;
                }else{
                    DrawQuadTexRot(getTexture("BEEFLAPUP"), 980+beeDistance, (float) (880+2*Math.sin(beeDistance)), -8, 8, 0);
                    beeDistance = beeDistance - 0.3f;
                }
                beeFlap = true;
            }
            if(beeDistance >280) beeDirection = false;
            else if(beeDistance < 0) beeDirection = true;




        }

        if(hasFlowers){
            DrawQuadTex(getTexture("LEFTFLOWERS"), -10, 810, 256, 128);
            DrawQuadTex(getTexture("RIGHTFLOWERS"), 1000, 813, 256, 128);
            DrawQuadTex(getTexture("RIGHTFLOWERS"), 970, 818, 256, 128);

        }
        if(hasNuts){
            DrawQuadTexRot(getTexture("NUT"), 760, 910, 8, 8, 0);
            DrawQuadTexRot(getTexture("NUT"), 365, 925, 8, 8, 90);
            DrawQuadTexRot(getTexture("NUT"), 755, 918, 8, 8, -15);
            DrawQuadTexRot(getTexture("NUT"), 765, 918, 8, 8, 15);
        }
        if(squirrelDistance >1380) squirrelDirection = false;
        else if(squirrelDistance < -100) squirrelDirection = true;
        if(hasSquirrels){
            if(squirrelDirection){
                DrawQuadTexRot(getTexture("SQUIRREL"), -100+squirrelDistance, 905, 32, 32, 0);
                squirrelDistance = squirrelDistance + 1f;
            }else{
                DrawQuadTexRot(getTexture("SQUIRREL"), -100+squirrelDistance, 905, -32, 32, 0);
                squirrelDistance = squirrelDistance - 1f;
            }
        }if(hasBears){
            if(bearDirection){
                if(bearFrame < 30)DrawQuadTexRot(getTexture("BEARSTEP1"), -100+bearDistance, 882, 64, 64, 0);
                else if(bearFrame <= 60)DrawQuadTexRot(getTexture("BEARSTEP2"), -100+bearDistance, 882, 64, 64, 0);
                bearDistance = bearDistance + 1.2f;

            }else{
                if(bearFrame < 30)DrawQuadTexRot(getTexture("BEARSTEP1"), -100+bearDistance, 882, -64, 64, 0);
                else if(bearFrame <= 60)DrawQuadTexRot(getTexture("BEARSTEP2"), -100+bearDistance, 882, -64, 64, 0);

                bearDistance = bearDistance - 1.2f;
            }
            if(bearFrame < 60) bearFrame = bearFrame + 1;
            else bearFrame = 0;
        }
        if(bearDistance >1380) bearDirection = false;
        else if(bearDistance < -100) bearDirection = true;
        if(hasBirds){
            DrawQuadTexRot(getTexture("BIRD_FLYING"), -100+birdDistance, 300, 32, 32, -24);
        } birdDistance = birdDistance + 2;
        if(birdDistance > 2000){
            birdDistance = -200;
        }







        for(int i = 0 ; i < 40 ; i++){
            DrawQuadTex(getTexture("GRASS"), i*32, 29*32, 64, 64);

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_T) && !tPressed){
            ambientSounds.stop();;
            treeMenu = false;
            tPressed = true;
        }
        else{
            if(tPressed && !Keyboard.isKeyDown(Keyboard.KEY_T) ){
                ambientSounds.loop();
                tPressed = false;
            }
        }
        tree.setTreeHeight(treeHeight);
        tree.Draw();


        ///////Upgrade Panel////////
        DrawQuadTex(getTexture("GREY"), 1000, 0, 280, 190);
        //////Highlight hovering
        float mouseX = Mouse.getX();
        float mouseY = 960 - Mouse.getY();

        if(hasMature){
            DrawQuadTex(getTexture("GREEN"), 1000, 30, 280, 20);
        }if(hasStrongBranches){
            DrawQuadTex(getTexture("GREEN"), 1000, 50, 280, 20);
        }if(hasNuts){
            DrawQuadTex(getTexture("GREEN"), 1000, 70, 280, 20);
        }if(hasSquirrels){
            DrawQuadTex(getTexture("GREEN"), 1000, 90, 280, 20);
        }if(hasBirds){
            DrawQuadTex(getTexture("GREEN"), 1000, 110, 280, 20);
        }if(hasFlowers){
            DrawQuadTex(getTexture("GREEN"), 1000, 130, 280, 20);
        }if(hasBees){
            DrawQuadTex(getTexture("GREEN"), 1000, 150, 280, 20);
        }if(hasBears){
            DrawQuadTex(getTexture("GREEN"), 1000, 170, 280, 20);
        }


        if(mouseX > 1000) {



            if(mouseY < 190){
                DrawQuadTex(getTexture("GREY"), 800, 0, 200, 290);
                font.drawString(800, 0, "Description:");
                font.drawString(800, 140, "Requirements:");
            }
            if (mouseY > 30 && mouseY < 50) {
                if(!hasMature){
                    DrawQuadTex(getTexture("BLUE"), 1000, 30, 280, 20);
                    if(Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 500 && fertilizer >= maturityLevel*100){
                        fertilizer = fertilizer - 100*(maturityLevel - 1);
                        maturityLevel = maturityLevel + 1;
                        mousePressed = true;
                    }
                }

                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Mature");
                tempfont.drawString(800, 60, "-Increases tree maturity level.");
                tempfont.drawString(800, 80, " Your tree will focus less on growth");
                tempfont.drawString(800, 100, " and conserve more water.");
                tempfont.drawString(800, 120, " Can be repeated, at greater cost.");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: "+maturityLevel*100+" Fertilizer");
                tempfont.drawString(800, 200, "-Height > 5m");
                tempfont.drawString(800, 220, "");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");



            } else if (mouseY > 50 && mouseY < 70) {
                if(!hasStrongBranches){
                    DrawQuadTex(getTexture("BLUE"), 1000, 50, 280, 20);
                    if(Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 1000 && fertilizer >= 50){
                        hasStrongBranches = true;
                        fertilizer = fertilizer - 50;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Strong Branches");
                tempfont.drawString(800, 60, "-Your tree's branches become very ");
                tempfont.drawString(800, 80, " strong, they will be able to support ");
                tempfont.drawString(800, 100, " bird nests and nuts");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 50 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 10m");
                tempfont.drawString(800, 220, "");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");

            } else if (mouseY > 70 && mouseY < 90) {
                if(!hasNuts){
                    DrawQuadTex(getTexture("BLUE"), 1000, 70, 280, 20);
                    if(hasStrongBranches && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 1500 && fertilizer >= 50){
                        hasNuts = true;
                        fertilizer = fertilizer - 50;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Nuts");
                tempfont.drawString(800, 60, "-Your tree will sprout nuts that can");
                tempfont.drawString(800, 80, " attract squirrels.");
                tempfont.drawString(800, 100, "");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 50 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 15m");
                tempfont.drawString(800, 220, "-Strong Branches");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");

            } else if (mouseY > 90 && mouseY < 110) {
                if(!hasSquirrels){
                    DrawQuadTex(getTexture("BLUE"), 1000, 90, 280, 20);
                    if(hasNuts && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 1500 && fertilizer >= 50){
                        hasSquirrels = true;
                        fertilizer = fertilizer - 50;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Squirrels");
                tempfont.drawString(800, 60, "-Squirrels come in and provide extra");
                tempfont.drawString(800, 80, " fertilizer as well as plant additional");
                tempfont.drawString(800, 100, " trees.");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 50 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 15m");
                tempfont.drawString(800, 220, "-Nuts");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");

            } else if (mouseY > 110 && mouseY < 130) {
                if(!hasBirds){
                    DrawQuadTex(getTexture("BLUE"), 1000, 110, 280, 20);
                    if(hasStrongBranches && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 2000 && fertilizer >= 100){
                        hasBirds = true;
                        fertilizer = fertilizer - 100;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Birds");
                tempfont.drawString(800, 60, "-Birds will fly in to make a home,");
                tempfont.drawString(800, 80, " they will provide more fertilizer");
                tempfont.drawString(800, 100, " and bring wildflower seeds from");
                tempfont.drawString(800, 120, " elsewhere.");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 100 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 20m");
                tempfont.drawString(800, 220, "-Strong Branches");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");



            } else if (mouseY > 130 && mouseY < 150) {
                if(!hasFlowers){
                    DrawQuadTex(getTexture("BLUE"), 1000, 130, 280, 20);
                    if(hasBirds && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 2000 && fertilizer >= 100){
                        hasFlowers = true;
                        fertilizer = fertilizer - 100;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Flowers");
                tempfont.drawString(800, 60, "-Nearby flowers will sprout, which");
                tempfont.drawString(800, 80, " will attract bees.");
                tempfont.drawString(800, 100, "");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 100 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 20m");
                tempfont.drawString(800, 220, "-Birds");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");



            } else if (mouseY > 150 && mouseY < 170) {
                if(!hasBees){
                    DrawQuadTex(getTexture("BLUE"), 1000, 150, 280, 20);
                    if(hasFlowers && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 2000 && fertilizer >= 200){
                        hasBees = true;
                        fertilizer = fertilizer - 200;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Bees");
                tempfont.drawString(800, 60, "-Bees will come in and produce");
                tempfont.drawString(800, 80, " honey that will attract bears.");
                tempfont.drawString(800, 100, "");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 200 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 20m");
                tempfont.drawString(800, 220, "-Flowers");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");

            } else if (mouseY > 170 && mouseY < 190) {
                if(!hasBears){
                    DrawQuadTex(getTexture("BLUE"), 1000, 170, 280, 20);
                    if(hasBees && Mouse.isButtonDown(0) && !mousePressed && treeHeight >= 3000 && fertilizer >= 200){
                        hasBears = true;
                        fertilizer = fertilizer - 200;
                    }
                }
                tempfont.drawString(800, 20, "");
                tempfont.drawString(800, 40, "Bears");
                tempfont.drawString(800, 60, "-Bears will come in and will provide");
                tempfont.drawString(800, 80, " a large amount of fertilizer.");
                tempfont.drawString(800, 100, "");
                tempfont.drawString(800, 120, "");

                tempfont.drawString(800, 160, "");
                tempfont.drawString(800, 180, "-Cost: 200 Fertilizer");
                tempfont.drawString(800, 200, "-Height > 30m");
                tempfont.drawString(800, 220, "-Bees");
                tempfont.drawString(800, 240, "");
                tempfont.drawString(800, 260, "");

            }
            if(mousePressed && !Mouse.isButtonDown(0)) mousePressed = false;
        }



        ///////Display Text
        font.drawString(1000, 0, "Upgrades: ");
        font.drawString(1000, 20, "Mature");
        font.drawString(1000, 40, "Strong Branches");
        font.drawString(1000, 60, "Nuts");
        font.drawString(1000, 80, "Squirrels");
        font.drawString(1000, 100, "Birds");
        font.drawString(1000, 120, "Flowers");
        font.drawString(1000, 140, "Bees");
        font.drawString(1000, 160, "Bears");

    }

    public float getTreeHeight(){
        return treeHeight;
    }

    private void cheats(){
        if(Keyboard.isKeyDown(Keyboard.KEY_1)){
            fertilizer = fertilizer + 1;
            System.out.println("Fertilizer cheat");
        }if(Keyboard.isKeyDown(Keyboard.KEY_2)){
            seedWater = seedWater + 1000;
            System.out.println("Water cheat");
        }if(Keyboard.isKeyDown(Keyboard.KEY_3)){
            treeHeight = treeHeight + 100;
            System.out.println("Height cheat");
        }if(Keyboard.isKeyDown(Keyboard.KEY_4)){
            treeHeight = treeHeight - 100;
            System.out.println("Height cheat");
        }
    }

}
