import java.awt.*;

public class Player extends Sprite {
    public boolean isGrounded;
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

    public void setupImages() {
        /*
        image = ImageIO.read(getClass().getResourceAsStream("/leftPlayer/P1_*_*.png"));
         */
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
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return "Player Action: " + action + "\n" +
                "Player Grounded: " + isGrounded;
    }
}
