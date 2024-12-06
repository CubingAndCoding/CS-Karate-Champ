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

    String side;

    public Player(int x, int y, int width, int height, int imagex, int imagey, int imageWidth, int imageHeight, String side) {
        super(x, y, width, height);
        this.imagex = imagex;
        this.imagey = imagey;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.side = side;

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
                idleFrames[i] = returnImage("/"+ side+" player/idle/idle_" + i + ".png");
            }

            walkFrames = new Image[2];
            for (int i = 0; i < walkFrames.length; i++) {
                walkFrames[i] = returnImage("/"+ side+" player/walk/walk_" + i + ".png");
            }

            jumpFrames = new Image[1];
            for (int i = 0; i < jumpFrames.length; i++) {
                jumpFrames[i] = returnImage("/"+ side+ " player/jump/jump_" + i + ".png");
            }

            punchFrames = new Image[1];
            for (int i = 0; i < punchFrames.length; i++) {
                punchFrames[i] = returnImage("/" + side+" player/punch/punch_"+i+".png");
            }

            blockFrames = new Image[1];
            for (int i = 0; i < blockFrames.length; i++) {
                blockFrames[i] =returnImage("/" + side+ " player/block/block_"+i+".png");
            }

            backKickFrames = new Image[1];
            for (int i = 0; i < backKickFrames.length; i++) {
                backKickFrames[i] = returnImage("/" + side+" player/kick/lkick_"+i+".png");
            }

            frontKickFrames = new Image[1];
            for (int i = 0; i < frontKickFrames.length; i++) {
                frontKickFrames[i] = returnImage("/"+ side+ " player/kick/fkick_" + i+".png");
            }

            duckFrames = new Image[1];
            for (int i = 0; i < duckFrames.length; i++) {
                duckFrames[i] = returnImage("/" + side+ " player/duck/duck_"+i+".png");
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
        frameTickRate = 1;
        freezeFrames = 0;
        action = MoveType.DUCK;
    }

    public void jump() {
        frameTickRate = 1;
        isGrounded = false;
        yvel = -17;
        action = MoveType.JUMP;
    }

    public void block() {
        frameTickRate = 1;
        freezeFrames = 0;
        action = MoveType.BLOCK;
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
            case NONE -> {
                if (isMoving) break;
                int curFrame = frame / frameTickRate % idleFrames.length;
                g2.drawImage(idleFrames[curFrame], imagex, imagey, imageWidth, imageHeight, null);
            }

            case JUMP -> {
                frame %= jumpFrames.length;
                g2.drawImage(jumpFrames[frame], imagex, imagey, imageWidth, imageHeight, null);
            }

            case PUNCH -> {
                frame%= punchFrames.length;
                g2.drawImage(punchFrames[frame], imagex,imagey,imageWidth,imageHeight,null);
            }

            case BLOCK -> {
                frame%= blockFrames.length;
                g2.drawImage(blockFrames[frame], imagex,imagey,imageWidth,imageHeight,null);
            }

            case FRONT_KICK -> {
                frame%= frontKickFrames.length;
                g2.drawImage(frontKickFrames[frame],imagex,imagey,imageWidth,imageHeight,null);
            }

            case BACK_KICK -> {
                frame%= backKickFrames.length;
                g2.drawImage(backKickFrames[frame], imagex,imagey,imageWidth,imageHeight,null);
            }

            case DUCK -> {
                frame%= duckFrames.length;
                g2.drawImage(duckFrames[frame],imagex,imagey,imageWidth,imageHeight,null);
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
