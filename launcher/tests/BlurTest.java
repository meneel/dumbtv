package tv.dumbtv.home;

import java.util.Arrays;

/** Box blur keeps flat areas flat, softens edges, keeps alpha, and leaves radius 0 alone. */
public final class BlurTest {
    private static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
    }

    public static void main(String[] args) {
        int[] flat = new int[64];
        Arrays.fill(flat, 0xFF336699);
        Blur.box(flat, 8, 8, 2);
        for (int p : flat) check(p == 0xFF336699, "a flat image stays flat");

        // 8x1 strip: four black pixels then four white ones, radius 1 (window of 3).
        int[] edge = new int[8];
        for (int i = 0; i < 8; i++) edge[i] = i < 4 ? 0xFF000000 : 0xFFFFFFFF;
        Blur.box(edge, 8, 1, 1);
        check((edge[2] >> 16 & 255) == 0, "far from the edge stays black");
        check((edge[3] >> 16 & 255) == 85, "one white neighbour in three is 85");
        check((edge[4] >> 16 & 255) == 170, "two white in three is 170");
        check((edge[7] >> 16 & 255) == 255, "far side stays white");
        check((edge[3] >>> 24) == 255, "alpha is kept");

        int[] untouched = {0xFF000000, 0xFFFFFFFF};
        Blur.box(untouched, 2, 1, 0);
        check(untouched[0] == 0xFF000000 && untouched[1] == 0xFFFFFFFF, "radius 0 is a no-op");
        System.out.println("BlurTest passed");
    }
}
