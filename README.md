# Snake

A fully playable Snake game built with **Java Swing** — my first independent software project.

https://github.com/user-attachments/assets/86964c33-f588-46e8-9a75-cb2526216e93

---

## Overview

Developed as an introduction to object-oriented programming and GUI development in Java. The game features real-time input handling, persistent score tracking, and a complete game loop built from scratch using the Swing framework.

---

## Technical Highlights

- **Language & Framework:** Java 11+, Java Swing (`JPanel`, `JFrame`, `javax.swing.Timer`)
- **Game Loop:** Event-driven architecture using a `javax.swing.Timer` for consistent frame-rate control (~12 FPS)
- **Input Handling:** Custom `KeyListener` implementation supporting WASD and arrow key controls with direction-reversal prevention
- **Persistent Storage:** High score saved locally to the user's home directory across sessions
- **Rendering:** Custom `paintComponent` override for all drawing — snake, food, HUD, and overlay messages
- **Collision Detection:** Self-collision and boundary wrap-around logic
- **Build:** Compiled as a modular Java JAR (`module-info.java`)

---

## Features

- Smooth snake movement with wrap-around borders
- Food spawning that avoids the snake's current body
- Real-time score and persistent high score display
- Game over screen with final score
- Restart without relaunching (`R`)

---

## How to Run

**Requirements:** Java 11 or later

```bash
java --module-path Snake.jar -m Snake/Snake.Snake
```

---

## Controls

| Key | Action |
|-----|--------|
| `W` / `↑` | Move up |
| `A` / `←` | Move left |
| `S` / `↓` | Move down |
| `D` / `→` | Move right |
| `R` | Restart |
| `Esc` | Quit |

---

## Project Background

This was my first programming project — written to learn the fundamentals of Java, event-driven GUI design, and game loop architecture. The codebase has since been refactored to fix several original bugs (timer misuse on the EDT, game logic inside the paint method, incorrect high score tracking) while preserving the original structure and scope.
