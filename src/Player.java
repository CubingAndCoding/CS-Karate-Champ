import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends Sprite {
    public boolean isGrounded;
    public int frame;
    public int freezeFrames;
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

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        isGrounded = true;
        freezeFrames = 0;
        action = MoveType.NONE;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void frontKick() {
        freezeFrames = 10;
        action = MoveType.FRONT_KICK;
    }

    public void backKick() {
        freezeFrames = 10;
        action = MoveType.BACK_KICK;
    }

    public void punch() {
        freezeFrames = 10;
        action = MoveType.PUNCH;
    }

    public void duck() {
        setHeight(getHeight() >> 1);
        setY(getY() + getHeight());
        freezeFrames = 0;
        action = MoveType.DUCK;
    }

    public void jump() {
        isGrounded = false;
        yvel = -20;
        action = MoveType.JUMP;
    }

    public void block() {
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

        switch (action) {
            case MoveType.NONE -> {
                frame %= idleFrames.length;
                g2.drawImage(idleFrames[frame], getX(), getY(), getWidth(), getHeight(), null);
            }

            case MoveType.WALK -> {
                frame %= walkFrames.length;
                g2.drawImage(idleFrames[frame], getX(), getY(), getWidth(), getHeight(), null);
            }

            case MoveType.JUMP -> {
                frame %= jumpFrames.length;
                g2.drawImage(jumpFrames[frame], getX(), getY(), getWidth(), getHeight(), null);
            }
        }
    }

    @Override
    public String toString() {
        return "Player Action: " + action + "\n" +
                "Player Grounded: " + isGrounded;
    }
}
