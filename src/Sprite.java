import java.awt.*;

public abstract class Sprite {
    private Image image;
    private int tileSize;
    private int x;
    private int y;

    public Sprite() {
        this(0, 0, 16);
    }

    public Sprite(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getTileSize() {
        return tileSize;
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


    public abstract void draw(Graphics2D g2, Color color);

    public abstract String toString();
}
