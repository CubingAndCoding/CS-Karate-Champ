import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 5;
    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    final int MAX_SCREEN_COLS = 16;
    final int MAX_SCREEN_ROWS = 9;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }
}
