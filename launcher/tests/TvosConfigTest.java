package tv.dumbtv.home;

import java.util.Arrays;

/** The tvos layout is a Config choice and the default layout is unchanged. */
public final class TvosConfigTest {
    private static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
    }

    public static void main(String[] args) {
        check("rows".equals(Config.LAYOUT.fallback), "default layout unchanged");
        check(Arrays.asList(Config.LAYOUT.values).contains("tvos"), "tvos is a layout");
        check("Apple TV style".equals(Config.LAYOUT.name("tvos")), "tvos has a name");
        check(Config.LAYOUT.values.length == Config.LAYOUT.names.length, "every layout has a name");
        System.out.println("TvosConfigTest passed");
    }
}
