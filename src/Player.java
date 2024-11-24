import java.awt.*;

public class Player extends Sprite {
    public boolean isGrounded;

    int yvel = 0;

    public Player(int x, int y, int tileSize) {
        super(x, y, tileSize);
        isGrounded = true;
    }

    public void jump() {
        isGrounded = false;
        yvel = -20;
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
