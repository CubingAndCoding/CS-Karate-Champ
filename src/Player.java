import java.awt.*;

public class Player extends Sprite {
    public boolean isGrounded;

    int yvel = 0;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        isGrounded = true;
    }

    public void jump() {
        isGrounded = false;
        yvel = -20;
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return "";
    }
}
