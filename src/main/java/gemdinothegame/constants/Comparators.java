package gemdinothegame.constants;

import gemdinothegame.entity.Entity;

import java.util.Comparator;

public class Comparators {
    public static Comparator<Entity> compareY = (o1, o2) -> {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return Double.compare(o1.y + o1.actualSize.y + o1.actualSize.height, o2.y + o2.actualSize.y + o2.actualSize.height);
    };
}
