import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 5;
    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    final int MAX_SCREEN_COLS = 16;
    final int MAX_SCREEN_ROWS = 9;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;
    final int FPS = 24;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Player settings
    int player1X = SCREEN_WIDTH / 4;
    int player1Y = SCREEN_HEIGHT * 3 / 4 - TILE_SIZE;
    int player2X = SCREEN_WIDTH  * 3 / 4 - TILE_SIZE * 3 / 4;
    int player2Y = SCREEN_HEIGHT * 3 / 4 - TILE_SIZE;
    int playerSpeed = 5;
    Player leftPlayer;
    Player rightPlayer;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
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
            System.out.println("\nLeft Player:");
            System.out.println(leftPlayer);
            if (leftPlayer.action == MoveType.DUCK) {
                leftPlayer.resetDuck();
            } leftPlayer.action = MoveType.NONE;

            switch (leftPlayerAction) {
                case FRONT_KICK -> leftPlayer.frontKick();
                case BACK_KICK -> leftPlayer.backKick();
                case PUNCH -> leftPlayer.punch();
                case DUCK -> leftPlayer.duck();
                case JUMP -> leftPlayer.jump();
                case BLOCK -> leftPlayer.block();
            }
        } else {
            leftPlayer.freezeFrames--;
        }

        if (rightPlayer.freezeFrames == 0) {
            System.out.println("\nRight Player");
            System.out.println(rightPlayer);

            if (rightPlayer.action == MoveType.DUCK) {
                rightPlayer.resetDuck();
            } rightPlayer.action = MoveType.NONE;

            switch (rightPlayerAction) {
                case FRONT_KICK -> rightPlayer.frontKick();
                case BACK_KICK -> rightPlayer.backKick();
                case PUNCH -> rightPlayer.punch();
                case DUCK -> rightPlayer.duck();
                case JUMP -> rightPlayer.jump();
                case BLOCK -> rightPlayer.block();
            }
        } else {
            rightPlayer.freezeFrames--;
        }

        if (Sprite.checkCollision(leftPlayer, rightPlayer)) System.out.println("COLLISION DETECTED");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        leftPlayer.draw(g2, Color.RED);
        rightPlayer.draw(g2, Color.BLUE);

        g2.dispose();
    }

    private MoveType getLeftPlayerAction() {
        if (!leftPlayer.isGrounded) return MoveType.NONE;
        MoveType[][] moves = MoveTable.moveTable;

        int rowIdx = 0;
        if (keyHandler.fPressed) rowIdx = 1;
        else if (keyHandler.gPressed) rowIdx = 2;

        int colIdx = 0;
        if (keyHandler.wPressed) colIdx = 1;
        else if (keyHandler.sPressed) colIdx = 2;

        return moves[rowIdx][colIdx];
    }

    private MoveType getRightPlayerAction() {
        if (!rightPlayer.isGrounded) return MoveType.NONE;
        MoveType[][] moves = MoveTable.moveTable;

        int rowIdx = 0;
        if (keyHandler.periodPressed) rowIdx = 1;
        else if (keyHandler.slashPressed) rowIdx = 2;

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

        if (keyHandler.leftPressed) {
            rightPlayer.setX(rightPlayer.getX() - playerSpeed);
        }

        if (keyHandler.rightPressed) {
            rightPlayer.setX(rightPlayer.getX() + playerSpeed);
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
