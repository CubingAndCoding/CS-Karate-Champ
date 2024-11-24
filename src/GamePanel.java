import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 5;
    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    final int MAX_SCREEN_COLS = 16;
    final int MAX_SCREEN_ROWS = 9;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            // Step 1: UPDATE - update information (like character positions)
            update();

            // Step 2: DRAW - draw the screen with the updated information
            repaint();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        /*
//        Object drawing test
        g2.setColor(Color.RED);
        g2.fillRect(0, 0, TILE_SIZE, TILE_SIZE);
         */

        g2.dispose();
    }
}
