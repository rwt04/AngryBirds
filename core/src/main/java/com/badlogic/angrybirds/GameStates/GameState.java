package com.badlogic.angrybirds.GameStates;

import java.util.List;

public class GameState {
    private int score;
    private List<BirdState> birds;
    private List<BlockState> blocks;
    private List<PigState> pigs;

    public GameState(int score, List<BirdState> birds, List<BlockState> blocks, List<PigState> pigs) {
        this.score = score;
        this.birds = birds;
        this.blocks = blocks;
        this.pigs = pigs;
    }

    public int getScore() {
        return score;
    }

    public List<BirdState> getBirds() {
        return birds;
    }

    public List<BlockState> getBlocks() {
        return blocks;
    }

    public List<PigState> getPigs() {
        return pigs;
    }
}




