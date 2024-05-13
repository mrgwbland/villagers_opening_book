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
        frame.setLayout(new BorderLayout()); // Use BorderLayout to position components

        chessBoard = new ChessBoard();
        frame.add(chessBoard, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton resetButton = new JButton("Reset Board");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chessBoard.resetBoard();
                updateOpeningLabel("Starting Position");
            }
        });

        JButton flipButton = new JButton("Flip Board");
        flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chessBoard.flipBoard();
            }
        });

        buttonPanel.add(resetButton);
        buttonPanel.add(flipButton);

        openingLabel = new JLabel("Starting Position");
        openingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(buttonPanel, BorderLayout.NORTH);
        infoPanel.add(openingLabel, BorderLayout.CENTER);

        frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void updateOpeningLabel(String opening) {
        openingLabel.setText(opening);
    }
}
