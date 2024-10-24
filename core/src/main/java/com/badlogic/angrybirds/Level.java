package com.badlogic.angrybirds;

import java.util.ArrayList;
import java.util.List;

// ground is at y=125

public class Level {
    private Catapult catapult;
    private List<Bird> birds = new ArrayList<Bird>();
    private List<Block> blocks = new ArrayList<Block>();
    private List<Pig> pigs = new ArrayList<Pig>();

    public Level(int level){
        catapult = new Catapult(260, 85.4f);
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
                birds.add(new BlackBird(22, 125));

                // blocks
                blocks.add(new SteelBlock(860, 195, "medium", 90f)); // decrease x by width-height/2
                blocks.add(new SteelBlock(1010, 195, "medium", 90f)); // increase y by width-height/2
                blocks.add(new SteelBlock(1160, 195, "medium", 90f));
                blocks.add(new SteelBlock(1350, 215, "long", 90f));
                blocks.add(new WoodBlock(900, 286, "short", 0f));
                blocks.add(new WoodBlock(1050, 286, "short", 0f));
                blocks.add(new WoodBlock(1200, 286, "short", 0f));
                blocks.add(new WoodBlock(1406, 327, "short", 0f));

                // pigs
                pigs.add((new SmallPig(918.5f, 312)));
                pigs.add((new SmallPig(1068.5f, 312)));
                pigs.add((new SmallPig(1218.5f, 312)));
                pigs.add((new SmallPig(1424.5f, 352)));
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

