import java.awt.*;

public abstract class Sprite {
    private Image image;
    private int tileSize;
    private int x;
    private int y;

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

    public static boolean checkCollision(Sprite one, Sprite two) {
        Rectangle spriteOne = new Rectangle(one.x, one.y, one.tileSize, one.tileSize);
        Rectangle spriteTwo = new Rectangle(two.x, two.y, two.tileSize, two.tileSize);

        return spriteOne.intersects(spriteTwo);
    }

    public abstract void draw(Graphics2D g2, Color color);

    public abstract String toString();
}
