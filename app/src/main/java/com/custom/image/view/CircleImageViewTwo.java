package com.custom.image.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageViewTwo extends AppCompatImageView {

    private static final String TAG = CircleImageViewTwo.class.getSimpleName();

    private float radius;
    private float border;
    private int borderColor;

    public CircleImageViewTwo(Context context) {
        this(context, null, 0);
    }

    public CircleImageViewTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageViewTwo, defStyleAttr, 0);
        border = a.getDimension(R.styleable.CircleImageViewTwo_border, 0f);
        borderColor = a.getColor(R.styleable.CircleImageViewTwo_border_color, Color.WHITE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width != height) throw new RuntimeException("The width and height must be equal.");
        radius = width / 2 - border;
        Log.i(TAG, "onMeasure: radius = " + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        if (border > 0) {
            Paint borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setColor(borderColor);
            canvas.drawCircle(width / 2, height / 2, width / 2, borderPaint);
        }
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) return;
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = radius * 2 / Math.min(bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        canvas.restore();
    }
}
