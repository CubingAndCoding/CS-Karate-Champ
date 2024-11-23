import java.awt.*;

public abstract class Sprite {
    public Image image;
    public int tileSize;
    public int x;
    public int y;

    public Sprite() {
        this(0, 0, 16);
    }

    public Sprite(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void drawImage(Graphics2D g2);
    public abstract String toString();
}
