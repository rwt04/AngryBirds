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

            // level 2
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
                // todo: set pig coordinates correctly
                pigs.add(new MediumPig(900, PlayScreen.GROUND_Y_PIXELS + 138));
                pigs.add(new SmallPig(900, PlayScreen.GROUND_Y_PIXELS + 303));
                break;

            // todo: level 3
            case 3:
                birds.add(new RedBird(210, 125));
                birds.add(new YellowBird(148, 125));
                birds.add(new BlackBird(85, 125));

                blocks.add(new GlassBlock(1000, PlayScreen.GROUND_Y_PIXELS + 12.5f, "medium", 0f));
                blocks.add(new GlassBlock(914, PlayScreen.GROUND_Y_PIXELS + 12.5f, "medium", 0f));
                blocks.add(new SteelBlock(957, PlayScreen.GROUND_Y_PIXELS + 25, "short", 90f));
                blocks.add(new SteelBlock(883.5f, PlayScreen.GROUND_Y_PIXELS + 25, "short", 90f));
                blocks.add(new SteelBlock(1030.5f, PlayScreen.GROUND_Y_PIXELS + 25, "short", 90f));
                blocks.add(new GlassBlock(957, PlayScreen.GROUND_Y_PIXELS + 123, "long", 0f));
                blocks.add(new WoodBlock(883.5f, PlayScreen.GROUND_Y_PIXELS + 135, "medium", 90f));
                blocks.add(new WoodBlock(1030.5f, PlayScreen.GROUND_Y_PIXELS + 135, "medium", 90f));
                blocks.add(new SteelBlock(957, PlayScreen.GROUND_Y_PIXELS + 299 + 12.5f, "long", 0f));
                blocks.add(new GlassBlock(957, PlayScreen.GROUND_Y_PIXELS + 324, "short", 90f));

                pigs.add(new SmallPig(920, PlayScreen.GROUND_Y_PIXELS + 41));
                pigs.add(new SmallPig(994, PlayScreen.GROUND_Y_PIXELS + 41));
                pigs.add(new BigPig(957, PlayScreen.GROUND_Y_PIXELS + 135 + 51.8f));
                pigs.add(new MediumPig(890, PlayScreen.GROUND_Y_PIXELS + 324 + 27.5f));
                pigs.add(new MediumPig(1024, PlayScreen.GROUND_Y_PIXELS + 324 + 27.5f));
                break;
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

