package tv.dumbtv.home;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * The dock's glass: a small pre-blurred copy of the wallpaper, stretched over the screen and cut to
 * a rounded band, under a translucent white layer and a hairline edge. With no wallpaper it is the
 * translucent layer alone. Nothing is blurred per frame.
 */
final class GlassBand extends Drawable {
    private static final int SMALL_W = 96, SMALL_H = 54, BLUR_RADIUS = 3;

    private final View owner;
    private final float inset, radius;
    private final Paint flat = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint solid = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint edge = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint photo = new Paint(Paint.FILTER_BITMAP_FLAG);
    private final RectF box = new RectF(), screen = new RectF();
    private final Path clip = new Path();
    private final int[] where = new int[2];
    private Bitmap backdrop;

    GlassBand(View owner) {
        this.owner = owner;
        inset = Ui.dp(owner.getContext(), 40);
        radius = Ui.dp(owner.getContext(), 28);
        flat.setColor(0x59000000);   // dark frosted glass over a blurred wallpaper
        solid.setColor(0xFF15171B);  // no wallpaper: a dark panel
        edge.setStyle(Paint.Style.STROKE);
        edge.setStrokeWidth(Ui.dp(owner.getContext(), 1));
        edge.setColor(0x2EFFFFFF);
    }

    /** Takes the wallpaper (any size), keeps a blurred 96x54 copy of it; null removes the backdrop. */
    void setBackdrop(Bitmap source) {
        if (source == null) {
            backdrop = null;
        } else {
            Bitmap small = Bitmap.createScaledBitmap(source, SMALL_W, SMALL_H, true);
            int[] px = new int[SMALL_W * SMALL_H];
            small.getPixels(px, 0, SMALL_W, 0, 0, SMALL_W, SMALL_H);
            if (small != source) small.recycle();
            Blur.box(px, SMALL_W, SMALL_H, BLUR_RADIUS);
            backdrop = Bitmap.createBitmap(px, SMALL_W, SMALL_H, Bitmap.Config.ARGB_8888);
        }
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        Rect b = getBounds();
        box.set(b.left + inset, b.top, b.right - inset, b.bottom);
        clip.reset();
        clip.addRoundRect(box, radius, radius, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(clip);
        if (backdrop != null) {
            // ponytail: assumes the wallpaper fills the screen without cropping; a CENTER_CROP
            // wallpaper of another aspect ratio shifts the blur slightly. Fix by cropping first.
            owner.getLocationOnScreen(where);
            int sw = owner.getResources().getDisplayMetrics().widthPixels;
            int sh = owner.getResources().getDisplayMetrics().heightPixels;
            screen.set(-where[0], -where[1], -where[0] + sw, -where[1] + sh);
            canvas.drawBitmap(backdrop, null, screen, photo);
        }
        canvas.drawRoundRect(box, radius, radius, backdrop != null ? flat : solid);
        canvas.restore();
        canvas.drawRoundRect(box, radius, radius, edge);
    }

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter filter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
