package data.core;

public class Utils {
    static public boolean compare(double d1, double d2) {
        double delta = 0.0000000001;
        return d1 <= d2 + delta && d1 >= d2 - delta;
    }
}
