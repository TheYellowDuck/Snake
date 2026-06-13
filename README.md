# Snake

A fully playable, built-from-scratch Snake game written in **Java Swing** — my first independent software project. It implements a complete fixed-timestep game loop, custom 2D rendering, collision detection, and persistent high-score storage using only the Java standard library, with no game engine or third-party dependencies.

https://github.com/user-attachments/assets/86964c33-f588-46e8-9a75-cb2526216e93

## Features

- Grid-based snake movement on a 60×30 board with wrap-around borders
- Apple spawning that never lands on the snake's current body
- Real-time score plus a persistent high score saved across sessions
- Start and game-over overlays, with instant restart (`R`) without relaunching
- WASD **and** arrow-key controls, with 180° reversal protection
- Custom-drawn HUD, snake, food, and translucent message overlays

## How It Works

The game is driven by an event-driven loop built on a `javax.swing.Timer` that fires about 12 times per second (`FPS = 12`). On each tick the game applies buffered input, advances the snake one cell, runs collision checks, and triggers a repaint.

Input is deliberately decoupled from movement: a keypress only sets a *pending* direction (`nextXv` / `nextYv`), which is committed at the start of the next tick. A guard rejects any direct 180° reversal — this eliminates the classic bug where two quick keypresses between frames fold the snake back onto itself. The playfield wraps at every edge, so leaving one side re-enters from the opposite one.

The snake's body is stored as a trail of grid coordinates; each tick appends the new head and trims the tail to the current length. Self-collision is detected by scanning the trail for the head's position, and food is re-rolled until it lands on an empty cell. The high score is persisted to a dotfile (`~/.snake_highscore`) in the user's home directory — loaded on startup, written on every new record, and flushed again on exit (window close or `Esc`). All visuals are produced in a single custom `paintComponent` override using AWT `Graphics` primitives.

## Background

This was my first programming project, written to learn the fundamentals of Java, event-driven GUI design, and game-loop architecture from the ground up. It has since been refactored to fix several original bugs — timer misuse on the event dispatch thread, game logic embedded in the paint method, and incorrect high-score tracking — while preserving its original structure and scope.

## Skills Demonstrated

- Object-oriented design — game model, rendering, and input separated across classes
- Event-driven programming — built on Java Swing's event dispatch model
- Game loop architecture — fixed-timestep loop via `javax.swing.Timer` (~12 FPS)
- 2D rendering — custom `paintComponent` / `Graphics` drawing of every element
- Collision detection — self-collision and boundary wrap-around
- Input handling — buffered direction changes with 180° reversal prevention
- Separation of concerns — `KeyListener` decoupled from game and render logic
- File I/O — persistent high-score storage across sessions
- Application lifecycle handling — save-on-exit via a `WindowListener`
- Java Platform Module System — modular build with `module-info.java`
- JAR packaging — runnable modular Java application

## Tech Stack

- Java 11+
- Java Swing (`JPanel`, `JFrame`, `javax.swing.Timer`)
- AWT (`Graphics`, `Color`, `Font`, `FontMetrics`, `KeyListener` / `KeyEvent`)
- Java Platform Module System (`module-info.java`)
- `java.io` file I/O
- Packaged as a runnable modular JAR (`Snake.jar`)

## Getting Started

**Requirements:** Java 11 or later.

Run the prebuilt JAR:

```bash
java --module-path Snake.jar -m Snake/Snake.Snake
```

Or compile and run from source:

```bash
javac -d out src/module-info.java src/Snake/*.java
java --module-path out -m Snake/Snake.Snake
```

### Controls

| Key | Action |
|-----|--------|
| `W` / `↑` | Move up |
| `A` / `←` | Move left |
| `S` / `↓` | Move down |
| `D` / `→` | Move right |
| `R` | Restart |
| `Esc` | Quit |
