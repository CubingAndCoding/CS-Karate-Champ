import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean wPressed, sPressed, aPressed, dPressed;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean fPressed, gPressed, hPressed;
    public boolean commaPressed, periodPressed, slashPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed but must be declared
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Left Player Keys
        if (code == KeyEvent.VK_W) {
            wPressed = true;
        } else if (code == KeyEvent.VK_S) {
            sPressed = true;
        }

        if (code == KeyEvent.VK_F) {
            fPressed = true;
        } else if (code == KeyEvent.VK_G) {
            gPressed = true;
        } else if (code == KeyEvent.VK_H) {
            hPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            aPressed = true;
        } if (code == KeyEvent.VK_D) {
            dPressed = true;
        }


        // Right Player Keys
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_COMMA) {
            commaPressed = true;
        } if (code == KeyEvent.VK_PERIOD) {
            periodPressed = true;
        } else if (code == KeyEvent.VK_SLASH) {
            slashPressed = true;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            wPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            aPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            sPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            dPressed = false;
        }

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_COMMA) {
            commaPressed = false;
        }

        if (code == KeyEvent.VK_PERIOD) {
            periodPressed = false;
        }

        if (code == KeyEvent.VK_SLASH) {
            slashPressed = false;
        }

        if (code == KeyEvent.VK_F) {
            fPressed = false;
        }

        if (code == KeyEvent.VK_G) {
            gPressed = false;
        }

        if (code == KeyEvent.VK_H) {
            hPressed = false;
        }
    }
}
