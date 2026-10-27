package tv.dumbtv.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.view.View;

/** The strip above the dock: the focused app's name in large type over a glow in its colour. */
final class TopShelf extends View {
    private static final int NAME_LIMIT = 36;

    private final int height, edge;
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint name = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String title = "";
    private int color = FocusColor.FALLBACK;

    TopShelf(Context c) {
        super(c);
        height = Ui.dp(c, 150);
        edge = Ui.dp(c, 56);
        name.setColor(Ui.TEXT);
        name.setTypeface(Ui.LIGHT);
        name.setTextSize(Ui.dp(c, 44));
    }

    /** Keeps the title to {@code max} characters, ending in an ellipsis when it is cut. */
    static String clip(String title, int max) {
        if (title == null) return "";
        return title.length() <= max ? title : title.substring(0, max - 1) + "…";
    }

    void show(CharSequence label, int tint) {
        String next = clip(label == null ? null : label.toString(), NAME_LIMIT);
        if (next.equals(title) && tint == color) return;
        title = next;
        color = tint;
        rebuild();
        invalidate();
    }

    private void rebuild() {
        int w = getWidth();
        if (w == 0) return;
        glow.setShader(new RadialGradient(w * 0.28f, height * 0.9f, w * 0.6f,
                TvosStyle.glow(color), 0x00000000, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthSpec), height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        rebuild();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), height, glow);
        canvas.drawText(title, edge, height * 0.62f, name);
    }
}
