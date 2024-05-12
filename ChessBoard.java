// ChessBoard.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoard extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 50;
    private Piece[][] board;
    private Image whitePawnImage;
    private Image blackPawnImage;
    private String currentPlayer = "white"; // Initial move is for white player
    private Piece selectedPiece = null;

    public ChessBoard() {
        setPreferredSize(new Dimension(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE));
        setBackground(Color.WHITE);

        // Initialize the board with pieces
        initializeBoard();

        // Load images
        try {
            whitePawnImage = new ImageIcon("RoyalPawnW.png").getImage();
            blackPawnImage = new ImageIcon("RoyalPawnB.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add mouse listener for piece movement
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int clickedRank = BOARD_SIZE - 1 - (mouseY / SQUARE_SIZE); // Calculate rank from bottom to top
                int clickedFile = mouseX / SQUARE_SIZE;
                if (board[clickedRank][clickedFile] != null && board[clickedRank][clickedFile].getColour().equals(currentPlayer)) {
                    // Highlight legal moves
                    // For now, let's assume all empty squares are legal moves
                    // You can implement the logic for legal moves later
                    // Then, implement logic to move the piece if a legal move is clicked
                    System.out.println("Legal moves for " + currentPlayer + " at (" + clickedRank + ", " + clickedFile + ")");
                    selectedPiece = board[clickedRank][clickedFile];
                }
                else {
                    Piece selectedDestination = board[clickedRank][clickedFile];
                    // Move the piece if a legal move is clicked
                    // For now, let's assume the clicked square is a legal move
                    if (selectedPiece != null&&selectedDestination==null) {
                        board[clickedRank][clickedFile] = selectedPiece;
                        board[selectedPiece.getRank()][selectedPiece.getFile()] = null;
                        selectedPiece.setRank(clickedRank);
                        selectedPiece.setFile(clickedFile);
                        repaint();

                        // Change player's turn
                        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
                        selectedPiece = null;
                    }
                }
            }
        });
    }

    private void initializeBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

        // Place the white royal pawn on e1
        board[0][4] = new Piece("K", "white", 0, 4); 
        // Place the black royal pawn on d8
        board[7][3] = new Piece("K", "black", 7, 3); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the board
        for (int rank = 0; rank < BOARD_SIZE; rank++) { 
            for (int file = 0; file < BOARD_SIZE; file++) { 
                if ((rank + file) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(file * SQUARE_SIZE, (BOARD_SIZE - 1 - rank) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        // Draw the pieces
        for (int rank = 0; rank < BOARD_SIZE; rank++) { 
            for (int file = 0; file < BOARD_SIZE; file++) { 
                Piece piece = board[rank][file]; 
                if (piece != null) {
                    Image image = null;
                    if (piece.getColour().equals("white")) {
                        image = whitePawnImage;
                    } else if (piece.getColour().equals("black")) {
                        image = blackPawnImage;
                    }
                    if (image != null) {
                        g.drawImage(image, file * SQUARE_SIZE, (BOARD_SIZE - 1 - rank) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null); 
                    }
                }
            }
        }
    }
}
