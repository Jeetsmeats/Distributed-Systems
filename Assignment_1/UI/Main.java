package UI;

import javax.swing.*;

public class Main extends JFrame {

    private JPanel mainPanel;

    public Main() {

        setContentPane(mainPanel);
        setTitle("Interactive Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
