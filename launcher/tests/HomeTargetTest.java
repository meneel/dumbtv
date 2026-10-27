package tv.dumbtv.home;

/** home_target is "package/class" (class may start with a dot); anything else means dumbtv's own home. */
public final class HomeTargetTest {
    private static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
    }

    private static boolean is(String[] got, String pkg, String cls) {
        return got != null && got.length == 2 && pkg.equals(got[0]) && cls.equals(got[1]);
    }

    public static void main(String[] args) {
        check(HomeTarget.parse(null) == null, "null is no target");
        check(HomeTarget.parse("") == null, "empty is no target");
        check(HomeTarget.parse("com.a.b") == null, "no slash is no target");
        check(HomeTarget.parse("/.Main") == null, "empty package is no target");
        check(HomeTarget.parse("com.a.b/") == null, "empty class is no target");
        check(HomeTarget.parse("bad pkg/.Main") == null, "a space in the package is no target");
        check(HomeTarget.parse("com.a.b/.Main/extra") == null, "a second slash is no target");
        check(is(HomeTarget.parse("com.a.b/.Main"), "com.a.b", "com.a.b.Main"), "a leading dot means relative to the package");
        check(is(HomeTarget.parse("com.a.b/com.a.b.ui.Main"), "com.a.b", "com.a.b.ui.Main"), "a full class name is kept");
        check(is(HomeTarget.parse("com.spocky.projengmenu/.ui.home.MainActivity"),
                "com.spocky.projengmenu", "com.spocky.projengmenu.ui.home.MainActivity"), "Projectivy's home activity");
        System.out.println("HomeTargetTest passed");
    }
}
