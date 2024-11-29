package com.badlogic.angrybirds.GameStates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class GameStateManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveGameState(GameState gameState, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(gameState, writer);
        }

    }

    public static GameState loadGameState(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, GameState.class);
        }
    }
}
