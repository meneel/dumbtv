package tv.dumbtv.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;

/** A full-screen glow in the focused app's colour, drawn behind everything else. */
final class Ambient extends View {
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int color = FocusColor.FALLBACK;

    Ambient(Context c) {
        super(c);
    }

    void show(int tint) {
        if (tint == color) return;
        color = tint;
        rebuild();
        invalidate();
    }

    private void rebuild() {
        int w = getWidth(), h = getHeight();
        if (w == 0 || h == 0) return;
        float[] g = TvosStyle.ambientShape(w, h);
        RadialGradient round = new RadialGradient(g[0], g[1], g[2],
                TvosStyle.glow(color), 0x00000000, Shader.TileMode.CLAMP);
        // squash the circle into the ellipse
        Matrix squash = new Matrix();
        squash.setScale(1f, g[3] / g[2], g[0], g[1]);
        round.setLocalMatrix(squash);
        glow.setShader(round);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        rebuild();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), glow);
    }
}
