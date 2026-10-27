package tv.dumbtv.home;

/** Numbers and colour maths for the Apple TV style layout, free of views so a host test can pin them. */
final class TvosStyle {
    static final String LAYOUT = "tvos";
    /** The focused dock tile grows this much; the stock layouts use Tile's own 1.08. */
    static final float FOCUS_SCALE = 1.12f;
    /** The focused dock tile rises this many dp off the dock, which is what casts its shadow. */
    static final float LIFT_DP = 14f;
    private static final int GLOW_ALPHA = 0x66;

    private TvosStyle() {}

    static boolean active(String layout) {
        return LAYOUT.equals(layout);
    }

    /**
     * The ambient glow as an ellipse {cx, cy, rx, ry} over a w x h screen: centred left of middle and
     * above the dock, and wide enough that its fade runs off the screen edges, where no edge shows.
     */
    static float[] ambientShape(int w, int h) {
        return new float[] {w * 0.30f, h * 0.40f, w * 0.65f, h * 0.55f};
    }

    /** The ambient glow: the app's colour at 40% opacity, whatever alpha it came with. */
    static int glow(int color) {
        return GLOW_ALPHA << 24 | color & 0xFFFFFF;
    }
}
