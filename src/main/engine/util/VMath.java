package engine.util;

import org.joml.Vector2f;

public class VMath {

    public static float angle(Vector2f v) {
        return 180f - (float) Math.toDegrees(Math.atan2(v.x, v.y));
    }
}
