public class Point {

    int x, y;

    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "("+x+","+y+")";
    }


    public boolean equals (Object o) {
        if (o == null || !(o instanceof Point)) {
            return false;
        }

        Point other = (Point)o;
        return x == other.x && y == other.y;
    }


    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + ((Integer)x).hashCode();     // x.hashCode() is the integer representation of x
        hash = 31 * hash + ((Integer)x).hashCode();
        return hash;
    }
    
}
