package tv.dumbtv.home;

/** A small box blur for the glass backdrop. Android 9 has no live blur, so this runs once per wallpaper. */
final class Blur {
    private Blur() {}

    /** Blurs {@code px} (ARGB, w x h) in place: window of 2*radius+1, edges clamped, rows then columns. */
    static void box(int[] px, int w, int h, int radius) {
        if (radius <= 0) return;
        int[] line = new int[Math.max(w, h)];
        for (int y = 0; y < h; y++) pass(px, y * w, 1, w, radius, line);
        for (int x = 0; x < w; x++) pass(px, x, w, h, radius, line);
    }

    private static void pass(int[] px, int start, int step, int n, int r, int[] out) {
        int window = 2 * r + 1;
        for (int shift = 0; shift < 32; shift += 8) {
            long sum = 0;
            for (int k = -r; k <= r; k++) sum += channel(px[start + clamp(k, n) * step], shift);
            for (int i = 0; i < n; i++) {
                out[i] = (int) (sum / window);
                sum += channel(px[start + clamp(i + r + 1, n) * step], shift)
                        - channel(px[start + clamp(i - r, n) * step], shift);
            }
            for (int i = 0; i < n; i++) {
                int p = start + i * step;
                px[p] = px[p] & ~(0xFF << shift) | (out[i] & 0xFF) << shift;
            }
        }
    }

    private static int channel(int pixel, int shift) {
        return pixel >>> shift & 0xFF;
    }

    private static int clamp(int i, int n) {
        return i < 0 ? 0 : i >= n ? n - 1 : i;
    }
}
