import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends Sprite {
    public boolean isGrounded;
    public int frame;
    public int frameTickRate;
    public int freezeFrames;
    public boolean isMoving;
    public MoveType action;

    int yvel = 0;

    Image[] jumpFrames;
    Image[] duckFrames;
    Image[] punchFrames;
    Image[] blockFrames;
    Image[] frontKickFrames;
    Image[] backKickFrames;

    Image[] walkFrames;
    Image[] idleFrames;

    int imagex;
    int imagey;
    int imageWidth;
    int imageHeight;

    public Player(int x, int y, int width, int height, int imagex, int imagey, int imageWidth, int imageHeight) {
        super(x, y, width, height);
        this.imagex = imagex;
        this.imagey = imagey;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        isGrounded = true;
        freezeFrames = 0;
        action = MoveType.NONE;
        isMoving = false;

        setupImages();
    }

    public Image returnImage(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    public void setupImages() {
        try {
            idleFrames = new Image[1];
            for (int i = 0; i < idleFrames.length; i++) {
                idleFrames[i] = returnImage("/left player/idle/idle_" + i + ".png");
            }

            walkFrames = new Image[2];
            for (int i = 0; i < walkFrames.length; i++) {
                walkFrames[i] = returnImage("/left player/walk/walk_" + i + ".png");
            }

            jumpFrames = new Image[1];
            for (int i = 0; i < jumpFrames.length; i++) {
                jumpFrames[i] = returnImage("left player/jump/jump_" + i + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void frontKick() {
        frameTickRate = 4;
        freezeFrames = 4 * punchFrames.length;
        freezeFrames = 10;
        action = MoveType.FRONT_KICK;
    }

    public void backKick() {
        frameTickRate = 4;
        freezeFrames = 4 * punchFrames.length;
        freezeFrames = 10;
        action = MoveType.BACK_KICK;
    }

    public void punch() {
        frameTickRate = 4;
        freezeFrames = 4 * punchFrames.length;
        freezeFrames = 10;
        action = MoveType.PUNCH;
    }

    public void duck() {
        setHeight(getHeight() >> 1);
        setY(getY() + getHeight());
        frameTickRate = 1;
        freezeFrames = 0;
        action = MoveType.DUCK;
    }

    public void jump() {
        frameTickRate = 1;
        isGrounded = false;
        yvel = -20;
        action = MoveType.JUMP;
    }

    public void block() {
        frameTickRate = 1;
        freezeFrames = 0;
        action = MoveType.BLOCK;
    }

    public void resetDuck() {
        setY(getY() - getHeight());
        setHeight(getHeight() << 1);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.drawRect(getX(), getY(), getWidth(), getHeight());

        if (frameTickRate == 0) frameTickRate = 1;

        if (isMoving && action == MoveType.NONE) {
            int curFrame = frame / frameTickRate % walkFrames.length;
            g2.drawImage(walkFrames[curFrame], imagex, imagey, imageWidth, imageHeight, null);
        }

        switch (action) {
            case MoveType.NONE -> {
                if (isMoving) break;
                int curFrame = frame / frameTickRate % idleFrames.length;
                g2.drawImage(idleFrames[curFrame], imagex, imagey, imageWidth, imageHeight, null);
            }

            case MoveType.JUMP -> {
                frame %= jumpFrames.length;
                g2.drawImage(jumpFrames[frame], imagex, imagey, imageWidth, imageHeight, null);
            }
        }

        frame++;
    }

    @Override
    public String toString() {
        return "Player Action: " + action + "\n" +
                "Player Grounded: " + isGrounded;
    }
}
