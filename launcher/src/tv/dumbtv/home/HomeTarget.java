package tv.dumbtv.home;

/** Parses the home_target setting: which launcher Guard starts after Home. Free of Android so a host test can pin it. */
final class HomeTarget {
    private HomeTarget() {}

    /**
     * "package/class" becomes {package, full class name}; a class starting with a dot is relative to the
     * package. Anything malformed gives null, which means dumbtv's own home.
     */
    static String[] parse(String setting) {
        if (setting == null) return null;
        String[] part = setting.split("/", -1);
        if (part.length != 2 || part[0].isEmpty() || part[1].isEmpty()) return null;
        if (!part[0].matches("[A-Za-z0-9_.]+")) return null;
        String cls = part[1].startsWith(".") ? part[0] + part[1] : part[1];
        if (!cls.matches("[A-Za-z0-9_.$]+")) return null;
        return new String[] {part[0], cls};
    }
}
