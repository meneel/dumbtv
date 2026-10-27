package tv.dumbtv.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

/** The strip above the dock: the focused app's name in large type. */
final class TopShelf extends View {
    private static final int NAME_LIMIT = 36;

    private final int height, edge;
    private final TextPaint name = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String title = "";

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

    void show(CharSequence label) {
        String next = clip(label == null ? null : label.toString(), NAME_LIMIT);
        if (next.equals(title)) return;
        title = next;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(title, edge, height * 0.62f, name);
    }
}
