import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoard extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 100;
    private Piece[][] board;
    private String currentPlayer = "white"; // Initial move is for white player
    private Piece selectedPiece = null;
    private boolean flipped = false;
    private StringBuilder moveHistory = new StringBuilder();
    private OpeningRecogniser openingRecogniser;

    public ChessBoard() {
        setPreferredSize(new Dimension(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE));
        setBackground(Color.WHITE);

        openingRecogniser = new OpeningRecogniser();
        // Initialise the board with pieces
        initialiseBoard();


        // Add mouse listener for piece movement
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int clickedRank = flipped ? (mouseY / SQUARE_SIZE) : (BOARD_SIZE - 1 - (mouseY / SQUARE_SIZE)); // Calculate rank from bottom to top
                int clickedFile = flipped ? (BOARD_SIZE - 1 - (mouseX / SQUARE_SIZE)) : (mouseX / SQUARE_SIZE);
                if (board[clickedRank][clickedFile] != null && board[clickedRank][clickedFile].getColour().equals(currentPlayer)) {
                    // Highlight legal moves
                    // For now, let's assume all empty squares are legal moves
                    // You can implement the logic for legal moves later
                    // Then, implement logic to move the piece if a legal move is clicked
                    selectedPiece = board[clickedRank][clickedFile];
                } else {
                    Piece selectedDestination = board[clickedRank][clickedFile];
                    // Move the piece if a legal move is clicked
                    // For now, let's assume the clicked square is a legal move
                    if (selectedPiece != null && (selectedDestination == null||selectedDestination.getColour().equals(currentPlayer)==false)) {
                        board[clickedRank][clickedFile] = selectedPiece;
                        recordMove(selectedPiece.getRank(),selectedPiece.getFile(),clickedRank,clickedFile);
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

    private void initialiseSquare(String type, String color, int rank, int file) {
        board[rank][file] = new Piece(type, color, rank, file);
    }

    private void initialiseBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

        initialiseSquare("B", "white", 0, 0);
        initialiseSquare("S", "white", 0, 1);
        initialiseSquare("N", "white", 0, 2);
        initialiseSquare("R", "white", 0, 3);
        initialiseSquare("K", "white", 0, 4);
        initialiseSquare("N", "white", 0, 5);
        initialiseSquare("S", "white", 0, 6);
        initialiseSquare("B", "white", 0, 7);
        initialiseSquare("P", "white", 1, 0);
        initialiseSquare("P", "white", 1, 1);
        initialiseSquare("P", "white", 1, 2);
        initialiseSquare("P", "white", 1, 3);
        initialiseSquare("P", "white", 1, 4);
        initialiseSquare("P", "white", 1, 5);
        initialiseSquare("P", "white", 1, 6);
        initialiseSquare("P", "white", 1, 7);

        initialiseSquare("B", "black", 7, 0);
        initialiseSquare("S", "black", 7, 1);
        initialiseSquare("N", "black", 7, 2);
        initialiseSquare("R", "black", 7, 4);
        initialiseSquare("K", "black", 7, 3);
        initialiseSquare("N", "black", 7, 5);
        initialiseSquare("S", "black", 7, 6);
        initialiseSquare("B", "black", 7, 7);
        initialiseSquare("P", "black", 6, 0);
        initialiseSquare("P", "black", 6, 1);
        initialiseSquare("P", "black", 6, 2);
        initialiseSquare("P", "black", 6, 3);
        initialiseSquare("P", "black", 6, 4);
        initialiseSquare("P", "black", 6, 5);
        initialiseSquare("P", "black", 6, 6);
        initialiseSquare("P", "black", 6, 7);
    }

    public void resetBoard() {
        initialiseBoard();
        repaint();
        moveHistory.setLength(0);
        currentPlayer = "white";
        String moves = moveHistory.toString();
        String opening = openingRecogniser.identifyOpening(moves);
        GUI.updateOpeningLabel(opening);
    }

    public void flipBoard() {
        flipped = !flipped;
        repaint();
    }

    public void recordMove(int startRank, int startFile, int endRank, int endFile) {
        char startRankChar = (char) ('0' + startRank);
        char startFileChar = (char) ('0' + startFile);
        char endRankChar = (char) ('0' + endRank);
        char endFileChar = (char) ('0' + endFile);
        moveHistory.append(board[startRank][startFile].getType()).append(startRankChar).append(startFileChar).append(endRankChar).append(endFileChar);

        String moves = moveHistory.toString();
        String opening = openingRecogniser.identifyOpening(moves);
        GUI.updateOpeningLabel(opening);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the board
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                if ((rank + file) % 2 == 0) {
                    g.setColor(Color.GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
                int x, y;
                if (flipped) {
                    x = (BOARD_SIZE - 1 - file) * SQUARE_SIZE;
                    y = rank * SQUARE_SIZE;
                } else {
                    x = file * SQUARE_SIZE;
                    y = (BOARD_SIZE - 1 - rank) * SQUARE_SIZE;
                }
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        // Draw the pieces
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                Piece piece = board[rank][file];
                if (piece != null) {
                    Image image = null;
                    switch (piece.getColour()) {
                        case "white":
                            switch (piece.getType()) {
                                case "K":
                                    image = new ImageIcon("RoyalPawnW.png").getImage();
                                    break;
                                case "B":
                                    image = new ImageIcon("BishopW.png").getImage();
                                    break;
                                case "N":
                                    image = new ImageIcon("KnightW.png").getImage();
                                    break;
                                case "R":
                                    image = new ImageIcon("RookW.png").getImage();
                                    break;
                                case "P":
                                    image = new ImageIcon("SoldierW.png").getImage();
                                    break;
                                case "S":
                                    image = new ImageIcon("SergeantW.png").getImage();
                                    break;
                                default:
                                    // Handle error or default case
                                    break;
                            }
                            break;
                        case "black":
                            switch (piece.getType()) {
                                case "K":
                                    image = new ImageIcon("RoyalPawnB.png").getImage();
                                    break;
                                case "B":
                                    image = new ImageIcon("BishopB.png").getImage();
                                    break;
                                case "N":
                                    image = new ImageIcon("KnightB.png").getImage();
                                    break;
                                case "R":
                                    image = new ImageIcon("RookB.png").getImage();
                                    break;
                                case "P":
                                    image = new ImageIcon("SoldierB.png").getImage();
                                    break;
                                case "S":
                                    image = new ImageIcon("SergeantB.png").getImage();
                                    break;
                                default:
                                    // Handle error or default case
                                    break;
                            }
                            break;
                        default:
                            // Handle error or default case
                            break;
                    }
                    if (image != null) {
                        int x, y;
                        if (flipped) {
                            x = (BOARD_SIZE - 1 - file) * SQUARE_SIZE;
                            y = rank * SQUARE_SIZE;
                        } else {
                            x = file * SQUARE_SIZE;
                            y = (BOARD_SIZE - 1 - rank) * SQUARE_SIZE;
                        }
                        g.drawImage(image, x, y, SQUARE_SIZE, SQUARE_SIZE, null);
                    }
                }
            }
        }
    }
}
