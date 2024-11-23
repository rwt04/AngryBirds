package com.badlogic.angrybirds;

import com.badlogic.angrybirds.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

// ground is at y=125

public class Level {
    private Catapult catapult;
    private List<Bird> birds = new ArrayList<Bird>();
    private List<Block> blocks = new ArrayList<Block>();
    private List<Pig> pigs = new ArrayList<Pig>();

    public Level(int level){
        catapult = new Catapult(260, PlayScreen.GROUND_Y_PIXELS);
        createLevel(level);
    }

    private void createLevel(int level){
        switch(level){
            // level 1
            case 1:
                // birds
                birds.add(new RedBird(210, 125));
                birds.add(new YellowBird(148, 125));
                birds.add(new BlackBird(85, 125));

// blocks
                blocks.add(new SteelBlock(688, PlayScreen.GROUND_Y_PIXELS, "medium", 90f)); // decrease x by width-height/2
                blocks.add(new SteelBlock(808, PlayScreen.GROUND_Y_PIXELS, "medium", 90f)); // increase y by width-height/2
                blocks.add(new SteelBlock(928, PlayScreen.GROUND_Y_PIXELS, "medium", 90f));
                blocks.add(new SteelBlock(1080, PlayScreen.GROUND_Y_PIXELS, "long", 90f));
                blocks.add(new WoodBlock(688, PlayScreen.GROUND_Y_PIXELS+178, "short", 0f));
                blocks.add(new WoodBlock(808, PlayScreen.GROUND_Y_PIXELS+178, "short", 0f));
                blocks.add(new WoodBlock(928, PlayScreen.GROUND_Y_PIXELS+178, "short", 0f));
                blocks.add(new WoodBlock(1080, PlayScreen.GROUND_Y_PIXELS+218, "short", 0f));

// pigs
                pigs.add(new SmallPig(688, PlayScreen.GROUND_Y_PIXELS+208));
                pigs.add(new SmallPig(808, PlayScreen.GROUND_Y_PIXELS+208));
                pigs.add(new SmallPig(928, PlayScreen.GROUND_Y_PIXELS+208));
                pigs.add(new SmallPig(1080, PlayScreen.GROUND_Y_PIXELS+249));
                break;

            case 2:
                // birds
                birds.add(new RedBird(210, 125));
                birds.add(new YellowBird(148, 125));
                birds.add(new BlackBird(85, 125));

                // blocks
                blocks.add(new WoodBlock(810, PlayScreen.GROUND_Y_PIXELS, "short", 90f));
                blocks.add(new WoodBlock(989, PlayScreen.GROUND_Y_PIXELS, "short", 90f));
                blocks.add(new GlassBlock(900, PlayScreen.GROUND_Y_PIXELS + 97.5f , "long", 0f));
                blocks.add(new SteelBlock(849, PlayScreen.GROUND_Y_PIXELS + 109, "short", 90f));
                blocks.add(new SteelBlock(951, PlayScreen.GROUND_Y_PIXELS + 109, "short", 90f));
                blocks.add(new SteelBlock(900, PlayScreen.GROUND_Y_PIXELS + 207, "medium", 0f));
                blocks.add(new WoodBlock(900, PlayScreen.GROUND_Y_PIXELS + 219, "square", 90f));
                blocks.add(new WoodBlock(900, PlayScreen.GROUND_Y_PIXELS + 274.5f, "long", 0f));

                // pigs
                pigs.add(new MediumPig(900, PlayScreen.GROUND_Y_PIXELS + 138));
                pigs.add(new BigPig(900, PlayScreen.GROUND_Y_PIXELS + 336.5f));


        }}

    public Catapult getCatapult(){
        return catapult;
    }

    public List<Bird> getBirds(){
        return birds;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Pig> getPigs() {
        return pigs;
    }
}

