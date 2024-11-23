import javax.swing.*;

public class KarateChampRunner {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Karate Champ Remake (Alaqmar and Joseph)");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
