import java.awt.*;

public class Player extends Sprite {
    public Player(int x, int y, int tileSize) {
        super(x, y, tileSize);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(getX(), getY(), getTileSize(), getTileSize());
    }

    @Override
    public String toString() {
        return "";
    }
}
