package tv.dumbtv.home;

/** Pins the Apple TV style numbers and the shelf glow colour maths. */
public final class TvosStyleTest {
    private static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
    }

    public static void main(String[] args) {
        check(TvosStyle.active("tvos"), "tvos is active");
        check(!TvosStyle.active("rows"), "rows is not active");
        check(!TvosStyle.active("grid"), "grid is not active");
        check(!TvosStyle.active(null), "null layout is not active");
        check(TvosStyle.glow(0xFF2050E0) == 0x662050E0, "glow is 40% opacity and keeps rgb");
        check(TvosStyle.glow(0x00112233) == 0x66112233, "glow ignores the incoming alpha");
        check(TvosStyle.FOCUS_SCALE > 1.08f, "lift is stronger than the stock focus scale");
        check(TvosStyle.LIFT_DP > 0f, "lift rises off the dock");
        float[] a = TvosStyle.ambientShape(1920, 1080);
        check(a.length == 4, "ambient shape is cx, cy, rx, ry");
        check(a[0] > 0 && a[0] < 1920 && a[1] > 0 && a[1] < 1080, "ambient glow is centred on the screen");
        check(a[2] > a[3], "ambient glow is wider than it is tall");
        check(a[2] > 1920 / 2f, "ambient glow reaches across most of the screen");
        check(TvosStyle.blockStart(true, 100, 120) == 100, "a visible title starts its row's scroll block");
        check(TvosStyle.blockStart(false, 0, 380) == 380, "a hidden title is ignored: the block starts at the row body");
        System.out.println("TvosStyleTest passed");
    }
}
