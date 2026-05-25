package Snake;

import java.awt.event.*;

public class keyListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent k) {
        switch (k.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (!Snake.gameOver) {
                    Snake.nextXv = -1;
                    Snake.nextYv = 0;
                    Snake.started = true;
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (!Snake.gameOver) {
                    Snake.nextXv = 0;
                    Snake.nextYv = -1;
                    Snake.started = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (!Snake.gameOver) {
                    Snake.nextXv = 1;
                    Snake.nextYv = 0;
                    Snake.started = true;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (!Snake.gameOver) {
                    Snake.nextXv = 0;
                    Snake.nextYv = 1;
                    Snake.started = true;
                }
                break;
            case KeyEvent.VK_R:
                Snake.resetGame();
                break;
            case KeyEvent.VK_ESCAPE:
                Snake.saveHighScore();
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
