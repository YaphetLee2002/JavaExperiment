package first;

import java.util.Objects;

public class Value {
    private char x;
    private double y;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Value value = (Value) object;
        return x == value.x && Double.compare(value.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
