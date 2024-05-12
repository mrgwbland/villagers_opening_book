// GUI.java
import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame;

    public void createAndShowGUI() {
        frame = new JFrame("Villagers Chess Opening Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); // Split the frame into two halves

        ChessBoard chessBoard = new ChessBoard();
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE); // Blank panel, you can customize this later

        frame.add(chessBoard);
        frame.add(infoPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
