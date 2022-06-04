import java.io.Serializable;

public class Entity implements Serializable {
    protected int x;
    protected int y;
    protected String type;
    protected char symbol;

    public Entity(String type, char symbol, int x, int y) {
        this.type = type;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public char getSymbol() {
        return symbol;
    }
}
