package com.lexing360.app.lexingupdate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.lexing360.app.lexingupdate.utils.ScreenUtil;

/**
 * @author unclewei
 *         把glide的图片转化为圆角的方法
 * @date 2017/11/13
 */

public class GlideCircleTransform extends BitmapTransformation {

    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;

    private static float radius = 0f;
    private static int angle = 8;//圆角角度
    private Boolean isStroke = true;
    private Context context;
    private int corners;

    public GlideCircleTransform(Context context, Boolean isStroke) {
        super(context);
        this.context = context;
        this.isStroke = isStroke;
        this.corners = ~CORNER_ALL;
    }

    public GlideCircleTransform(Context context, Boolean isStroke, int corners) {
        super(context);
        this.context = context;
        this.isStroke = isStroke;
        this.corners = corners;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        radius = ScreenUtil.dp2px(context, angle);
        if (source == null) {
            return null;
        }
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        int notRoundedCorners = corners ^ CORNER_ALL;
        if ((notRoundedCorners & CORNER_TOP_LEFT) == 0) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        if ((notRoundedCorners & CORNER_TOP_RIGHT) == 0) {
            canvas.drawRect(rectF.right - radius, 0, rectF.right, radius, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) == 0) {
            canvas.drawRect(0, rectF.bottom - radius, radius, rectF.bottom, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) == 0) {
            canvas.drawRect(rectF.right - radius, rectF.bottom - radius, rectF.right, rectF.bottom, paint);
        }
        if (isStroke) {
            paint.setShader(null);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            paint.setColor(Color.rgb(238, 238, 238));
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}