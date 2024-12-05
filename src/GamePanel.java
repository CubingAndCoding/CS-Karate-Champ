import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 5;
    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    final int MAX_SCREEN_COLS = 9;
    final int MAX_SCREEN_ROWS = 9;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;
    final int FPS = 12;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Player settings
    int player1X = SCREEN_WIDTH / 4;
    int player1Y = SCREEN_HEIGHT * 3 / 4 - TILE_SIZE;
    int player2X = SCREEN_WIDTH  * 3 / 4 - TILE_SIZE * 3 / 4;
    int player2Y = SCREEN_HEIGHT * 3 / 4 - TILE_SIZE;
    int playerSpeed = 4;
    Player leftPlayer;
    Player rightPlayer;
    
    Image backgroundImage;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void startGameThread() {
        setupBackgroundImage();
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void setupBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        leftPlayer = new Player(player1X, player1Y, TILE_SIZE * 3 / 4, TILE_SIZE);
        rightPlayer = new Player(player2X, player2Y, TILE_SIZE * 3 / 4, TILE_SIZE);

        double drawInterval = 1_000_000_000./FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // Step 1: UPDATE - update information (like character positions)
            update();

            // Step 2: DRAW - draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1_000_000;

                if (remainingTime < 0)
                    remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        updateMovement();
        updatePlayerBounds(leftPlayer);
        updatePlayerBounds(rightPlayer);

        MoveType leftPlayerAction = getLeftPlayerAction();
        MoveType rightPlayerAction = getRightPlayerAction();

        if (leftPlayer.freezeFrames == 0) {
            if (leftPlayer.action == MoveType.DUCK) {
                leftPlayer.resetDuck();
            }

            if (!keyHandler.aPressed && !keyHandler.dPressed)
                leftPlayer.action = MoveType.NONE;

            switch (leftPlayerAction) {
                case FRONT_KICK -> leftPlayer.frontKick();
                case BACK_KICK -> leftPlayer.backKick();
                case PUNCH -> leftPlayer.punch();
                case DUCK -> leftPlayer.duck();
                case JUMP -> {
                    if (!leftPlayer.isGrounded) break;
                    leftPlayer.jump();
                }
                case BLOCK -> leftPlayer.block();
            }
        } else {
            leftPlayer.freezeFrames--;
        }

        if (rightPlayer.freezeFrames == 0) {
            if (rightPlayer.action == MoveType.DUCK) {
                rightPlayer.resetDuck();
            }

            if (!keyHandler.leftPressed && !keyHandler.rightPressed)
                rightPlayer.action = MoveType.NONE;

            switch (rightPlayerAction) {
                case FRONT_KICK -> rightPlayer.frontKick();
                case BACK_KICK -> rightPlayer.backKick();
                case PUNCH -> rightPlayer.punch();
                case DUCK -> rightPlayer.duck();
                case JUMP -> {
                    if (!rightPlayer.isGrounded) break;
                    rightPlayer.jump();
                }
                case BLOCK -> rightPlayer.block();
            }
        } else {
            rightPlayer.freezeFrames--;
        }

        cont:
            if (Sprite.checkCollision(leftPlayer, rightPlayer)) {
                char winner = getWinner();
                if (winner == 'T') break cont;
                System.out.println(winner);
            }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        g2.setColor(Color.RED);

        if (leftPlayer == null || rightPlayer == null) return;

        if (!leftPlayer.isGrounded) leftPlayer.action = MoveType.JUMP;
        if (leftPlayer != null)
            leftPlayer.draw(g2, Color.RED);

        if (!rightPlayer.isGrounded) rightPlayer.action = MoveType.JUMP;
        if (rightPlayer != null)
            rightPlayer.draw(g2, Color.BLUE);

        g2.dispose();
    }

    public char getWinner() {
        if (leftPlayer.action == MoveType.BLOCK) return 'T';
        if (rightPlayer.action == MoveType.BLOCK) return 'T';

        if (leftPlayer.action == MoveType.BACK_KICK && rightPlayer.action != MoveType.BACK_KICK) return 'L';
        if (rightPlayer.action == MoveType.BACK_KICK && leftPlayer.action != MoveType.BACK_KICK) return 'R';

        if (leftPlayer.action == MoveType.FRONT_KICK && rightPlayer.action != MoveType.FRONT_KICK) return 'L';
        if (rightPlayer.action == MoveType.FRONT_KICK && leftPlayer.action != MoveType.FRONT_KICK) return 'R';

        if (leftPlayer.action == MoveType.PUNCH && rightPlayer.action != MoveType.PUNCH) return 'L';
        if (rightPlayer.action == MoveType.PUNCH && leftPlayer.action != MoveType.PUNCH) return 'R';

        return 'T';
    }

    private MoveType getLeftPlayerAction() {
        if (!leftPlayer.isGrounded) return MoveType.JUMP;
        MoveType[][] moves = MoveTable.moveTable;

        int rowIdx = 0;
        if (keyHandler.fPressed) rowIdx = 1;
        else if (keyHandler.gPressed) rowIdx = 2;
        else if (keyHandler.hPressed) rowIdx = 3;

        int colIdx = 0;
        if (keyHandler.wPressed) colIdx = 1;
        else if (keyHandler.sPressed) colIdx = 2;

        return moves[rowIdx][colIdx];
    }

    private MoveType getRightPlayerAction() {
        if (!rightPlayer.isGrounded) return MoveType.JUMP;
        MoveType[][] moves = MoveTable.moveTable;

        int rowIdx = 0;
        if (keyHandler.commaPressed) rowIdx = 1;
        else if (keyHandler.periodPressed) rowIdx = 2;
        else if (keyHandler.slashPressed) rowIdx = 3;

        int colIdx = 0;
        if (keyHandler.upPressed) colIdx = 1;
        else if (keyHandler.downPressed) colIdx = 2;

        return moves[rowIdx][colIdx];
    }

    private void updateMovement() {
        if (keyHandler.aPressed) {
            leftPlayer.setX(leftPlayer.getX() - playerSpeed);
        }

        if (keyHandler.dPressed) {
            leftPlayer.setX(leftPlayer.getX() + playerSpeed);
        }

        if (keyHandler.aPressed ^ keyHandler.dPressed) {
            leftPlayer.action = MoveType.WALK;
            leftPlayer.frameTickRate = 4;
        }

        if (keyHandler.leftPressed) {
            rightPlayer.action = MoveType.WALK;
            rightPlayer.setX(rightPlayer.getX() - playerSpeed);
        }

        if (keyHandler.rightPressed) {
            rightPlayer.action = MoveType.WALK;
            rightPlayer.setX(rightPlayer.getX() + playerSpeed);
        }

        if (keyHandler.leftPressed ^ keyHandler.rightPressed) {
            rightPlayer.action = MoveType.WALK;
            rightPlayer.frameTickRate = 4;
        }

        leftPlayer.setY(leftPlayer.getY() + leftPlayer.yvel);
        leftPlayer.yvel += 2;

        rightPlayer.setY(rightPlayer.getY() + rightPlayer.yvel);
        rightPlayer.yvel += 2;

    }

    private void updatePlayerBounds(Player player) {
        if (player.getX() < 0) player.setX(0);
        if (player.getX() > SCREEN_WIDTH - player.getWidth()) player.setX(SCREEN_WIDTH - player.getWidth());
        if (player.getY() < 0) player.setY(0);
        if (player.getY() >= SCREEN_HEIGHT * 3 / 4 - player.getHeight()) {
            player.isGrounded = true;
            player.setY(SCREEN_HEIGHT * 3 / 4 - player.getHeight());
        } else player.isGrounded = false;
    }
}
