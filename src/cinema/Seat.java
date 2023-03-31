package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean available;

    public Seat() {}

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.available = true;
        if (this.row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
