package com.custom.image.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundedCornerImageView extends AppCompatImageView {

    private static final String TAG = RoundedCornerImageView.class.getSimpleName();

    private float radius;
    private float border;
    private int borderColor;

    public RoundedCornerImageView(Context context) {
        this(context, null, 0);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedCornerImageView, defStyleAttr, 0);
        radius = a.getDimension(R.styleable.RoundedCornerImageView_radius, 0f);
        border = a.getDimension(R.styleable.RoundedCornerImageView_border, 0f);
        borderColor = a.getColor(R.styleable.RoundedCornerImageView_border_color, Color.WHITE);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        if (border > 0) {
            Paint borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);
            canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, borderPaint);
        }

        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) return;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        float scaleW = ((float) width) / bitmap.getWidth();
        float scaleH = ((float) height) / bitmap.getHeight();
        matrix.setScale(scaleW, scaleH);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.drawRoundRect(new RectF(border, border, width - border, height - border), radius, radius, paint);
    }
}
