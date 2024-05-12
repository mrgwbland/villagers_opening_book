// Piece.java
public class Piece {
    private String type;
    private String colour;
    private int rank;
    private int file;

    public Piece(String type, String colour, int rank, int file) {
        this.type = type;
        this.colour = colour;
        this.rank = rank;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public String getColour() {
        return colour;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
