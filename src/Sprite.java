import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {
    private Image image;
    private int width, height;
    private int x;
    private int y;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
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
        Rectangle spriteOne = new Rectangle(one.x, one.y, one.width, one.height);
        Rectangle spriteTwo = new Rectangle(two.x, two.y, two.width, two.height);

        return spriteOne.intersects(spriteTwo);
    }

    public abstract void draw(Graphics2D g2, Color color);
}
