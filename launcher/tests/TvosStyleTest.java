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
        System.out.println("TvosStyleTest passed");
    }
}
