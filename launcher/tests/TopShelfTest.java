package tv.dumbtv.home;

/** The shelf title helper never returns null and never exceeds its limit. */
public final class TopShelfTest {
    private static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
    }

    public static void main(String[] args) {
        check("".equals(TopShelf.clip(null, 20)), "null title is empty");
        check("".equals(TopShelf.clip("", 20)), "empty title is empty");
        check("Netflix".equals(TopShelf.clip("Netflix", 20)), "short title is kept");
        String longName = "A very long application name that would run off the screen";
        String clipped = TopShelf.clip(longName, 20);
        check(clipped.length() == 20, "long title is cut to the limit");
        check(clipped.endsWith("…"), "long title ends with an ellipsis");
        System.out.println("TopShelfTest passed");
    }
}
