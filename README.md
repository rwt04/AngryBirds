# Angry Birds Game

A Java-based 2D physics game inspired by the classic Angry Birds gameplay, built with LibGDX and Box2D. The project features multiple levels, pig targets, destructible blocks, different bird types with unique behaviors, a scoring HUD, and restart/win-lose flow.

## Project Overview

This project recreates the core Angry Birds experience in a desktop game using a physics engine and custom game objects. Players launch birds from a catapult to knock down pig structures, earn points, and clear each level by eliminating all pigs or meeting the game conditions.

The game includes:
- Multiple levels with custom layouts
- Different bird types such as Red, Yellow, and Black birds
- Pig enemies with varied sizes and health
- Destructible wood, glass, and steel blocks
- Physics-based collision and projectile motion
- Score and HUD systems
- Win/Lose screens and level progression
- Save/load support for game state

## How It Works

The gameplay loop is built around a simple but effective structure:

1. The player launches a bird from the catapult.
2. The bird travels with physics-based motion and gravity.
3. Collisions with blocks and pigs trigger damage and destruction.
4. The game updates score based on destroyed targets.
5. When the active bird is out of play, the next bird loads automatically.
6. The level ends when all pigs are defeated or the player loses all birds.

The main game flow is managed in the screen system, where the game transitions between splash, menu, play, win, lose, and pause screens. The `PlayScreen` handles input, world stepping, collisions, and level updates, while `Level` defines each stage layout.

## Tech Stack

- Java
- Gradle
- LibGDX
- Box2D physics engine
- LWJGL3 desktop backend
- Gson for game data handling
- Java Swing-free desktop rendering pipeline

## Project Structure

- `core/` - Main game logic, screens, entities, physics, levels, and rendering
- `lwjgl3/` - Desktop launcher and runtime configuration
- `assets/` - Sprites, backgrounds, UI textures, and game media
- `build.gradle` - Root Gradle project configuration
- `settings.gradle` - Multi-project Gradle settings

## How to Run

### Prerequisites
- Java JDK 8 or later
- Gradle wrapper included in the project

### Run on Windows
```bash
gradlew.bat lwjgl3:run
```

### Run on macOS/Linux
```bash
./gradlew lwjgl3:run
```

### Optional build
```bash
./gradlew build
```

## Contributing

This project was developed by:

- Hrithik Raj
- Aditya Rawat
- dhimant Kaul

## References

- LibGDX skin assets: https://github.com/czyzby/gdx-skins
- Gameplay inspiration and learning references:
  - https://youtu.be/EJwXzmUQChg?si=NZp6AKICqe5J3wcH
  - https://youtu.be/a8MPxzkwBwo?si=fjLpfBQRt44lDDWy



