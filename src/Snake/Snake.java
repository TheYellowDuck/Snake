package Snake;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;

public class Snake extends JPanel {

    static final int XLEN = 1200, YLEN = 600;
    static final int TC1 = 60, TC2 = 30;
    static final int GS = 20;
    static final int FPS = 12;

    static int xv = 0, yv = 0;
    static int nextXv = 0, nextYv = 0;
    static int px = TC1 / 2, py = TC2 / 2;
    static int ax, ay;
    static int tail = 5;
    static int hs = 0;
    static boolean gameOver = false;
    static boolean started = false;
    static ArrayList<int[]> trail = new ArrayList<>();
    static File hsFile;

    public Snake() {
        loadHighScore();
        resetGame();
        setPreferredSize(new Dimension(XLEN, YLEN));
        setBackground(Color.black);

        new Timer(1000 / FPS, (ActionEvent e) -> {
            if (started && !gameOver) {
                applyInput();
                game();
            }
            repaint();
        }).start();
    }

    static void resetGame() {
        px = TC1 / 2;
        py = TC2 / 2;
        xv = 0;
        yv = 0;
        nextXv = 0;
        nextYv = 0;
        tail = 5;
        trail.clear();
        trail.add(new int[]{px, py});
        gameOver = false;
        started = false;
        spawnApple();
    }

    static void applyInput() {
        // Prevent reversing into yourself when already moving
        if (nextXv == -xv && nextYv == -yv && (xv != 0 || yv != 0)) return;
        xv = nextXv;
        yv = nextYv;
    }

    static void spawnApple() {
        do {
            ax = (int)(Math.random() * TC1);
            ay = (int)(Math.random() * TC2);
        } while (isAppleOnSnake());
    }

    static boolean isAppleOnSnake() {
        for (int[] seg : trail)
            if (seg[0] == ax && seg[1] == ay) return true;
        return false;
    }

    public static void game() {
        px += xv;
        py += yv;
        if (px < 0) px = TC1 - 1;
        if (px >= TC1) px = 0;
        if (py < 0) py = TC2 - 1;
        if (py >= TC2) py = 0;

        trail.add(new int[]{px, py});
        while (trail.size() > tail) trail.remove(0);

        // Self-collision: check all segments except the head we just added
        for (int i = 0; i < trail.size() - 1; i++) {
            if (trail.get(i)[0] == px && trail.get(i)[1] == py) {
                gameOver = true;
                int score = tail - 5;
                if (score > hs) {
                    hs = score;
                    saveHighScore();
                }
                return;
            }
        }

        if (ax == px && ay == py) {
            tail++;
            spawnApple();
            int score = tail - 5;
            if (score > hs) {
                hs = score;
                saveHighScore();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Snake body
        g.setColor(Color.green);
        for (int i = 0; i < trail.size() - 1; i++) {
            g.fillRect(trail.get(i)[0] * GS + 2, trail.get(i)[1] * GS + 2, GS - 4, GS - 4);
        }
        // Snake head (slightly larger to stand out)
        if (!trail.isEmpty()) {
            int[] head = trail.get(trail.size() - 1);
            g.fillRect(head[0] * GS + 1, head[1] * GS + 1, GS - 2, GS - 2);
        }

        // Apple
        g.setColor(Color.red);
        g.fillRect(ax * GS + 1, ay * GS + 1, GS - 2, GS - 2);

        // HUD
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Score: " + (tail - 5), 20, 25);
        g.drawString("High Score: " + hs, XLEN - 200, 25);

        if (!started) {
            drawOverlay(g, "Press WASD or Arrow Keys to Start");
        } else if (gameOver) {
            drawOverlay(g, "Game Over!  Score: " + (tail - 5) + "   Press R to Restart");
        }
    }

    private void drawOverlay(Graphics g, String msg) {
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(msg);
        int x = XLEN / 2 - w / 2;
        int y = YLEN / 2;
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(x - 15, y - 25, w + 30, 40, 10, 10);
        g.setColor(Color.yellow);
        g.drawString(msg, x, y);
    }

    static void loadHighScore() {
        hsFile = new File(System.getProperty("user.home"), ".snake_highscore");
        try (BufferedReader reader = new BufferedReader(new FileReader(hsFile))) {
            hs = Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            hs = 0;
        }
    }

    static void saveHighScore() {
        try (FileWriter writer = new FileWriter(hsFile)) {
            writer.write(String.valueOf(hs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Snake");
        f.addKeyListener(new keyListener());
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.add(new Snake(), BorderLayout.CENTER);
        f.setResizable(false);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveHighScore();
                System.exit(0);
            }
        });
    }
}
