import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private static JFrame frame;
    private static ChessBoard chessBoard;
    private static JLabel openingLabel;

    public void createAndShowGUI() {
        frame = new JFrame("Villagers Chess Opening Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); // Split the frame into two halves

        chessBoard = new ChessBoard();
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE); // Blank panel, you can customize this later

        JButton resetButton = new JButton("Reset Board");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chessBoard.resetBoard();
            }
        });

        JButton flipButton = new JButton("Flip Board");
        flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chessBoard.flipBoard();
            }
        });

        openingLabel = new JLabel();
        openingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        openingLabel.setPreferredSize(new Dimension(200, 30));

        infoPanel.add(resetButton);
        infoPanel.add(flipButton);
        infoPanel.add(openingLabel);

        frame.add(chessBoard);
        frame.add(infoPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        updateOpeningLabel("Starting Position");
    }

    public static void updateOpeningLabel(String opening) {
        openingLabel.setText(opening);
    }
}
