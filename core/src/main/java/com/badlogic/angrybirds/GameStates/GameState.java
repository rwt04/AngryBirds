package com.badlogic.angrybirds.GameStates;

import java.util.List;

public class GameState {
    private int score;
    private List<BirdState> birds;
    private List<BlockState> blocks;
    private List<PigState> pigs;
    private int level;

    public GameState(int score, List<BirdState> birds, List<BlockState> blocks, List<PigState> pigs, int level) {
        this.score = score;
        this.birds = birds;
        this.blocks = blocks;
        this.pigs = pigs;
        this.level = level;
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

    public int getLevel() {
        return level;
    }
}




