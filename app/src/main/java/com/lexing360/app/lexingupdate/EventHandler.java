package com.lexing360.app.lexingupdate;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class EventHandler {

    private static final String FONT_TYPE_SMALL = "SMALL";
    private static final String FONT_TYPE_MIDDLE = "MIDDLE";
    private static final String FONT_TYPE_LARGE = "LARGE";

    @BindingAdapter("android:src")
    public static void loadImage(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .load(resId)
                .centerCrop()
                .transform(new GlideCircleTransform(imageView.getContext(), false))
                .dontAnimate()
                .override(imageView.getMaxWidth(), imageView.getMaxHeight())
                .into(imageView);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView view, String url, int placeHolder) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeHolder);
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeHolder)
                .transform(new GlideCircleTransform(view.getContext(), true))
                .dontAnimate()
                .into(view);
    }

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView view, String url, Drawable defaultDrawable) {
        if (TextUtils.isEmpty(url)) {
            view.setImageDrawable(defaultDrawable);
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .error(defaultDrawable)
                .centerCrop()
                .transform(new GlideCircleTransform(view.getContext(), true))
                .placeholder(defaultDrawable)
                .dontAnimate()
                .override(view.getMaxWidth(), view.getMaxHeight())
                .into(view);
    }

    @BindingAdapter({"imageUrl", "placeholder", "imageSize"})
    public static void loadImage(ImageView view, String url, Drawable defaultDrawable, int imageSize) {
        if (TextUtils.isEmpty(url)) {
            view.setImageDrawable(defaultDrawable);
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .override(imageSize, imageSize)
                .error(defaultDrawable)
                .placeholder(defaultDrawable)
                .centerCrop()
                .dontAnimate()
                .into(view);
    }

    public static void loadImageCircle(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .load(resId)
                .centerCrop()
                .transform(new GlideCircleTransform(imageView.getContext(), true))
                .dontAnimate()
                .override(imageView.getMaxWidth(), imageView.getMaxHeight())
                .into(imageView);

    }

    public static void loadImageCircle(ImageView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(view.getContext(), true))
                .dontAnimate()
                .override(view.getMaxWidth(), view.getMaxHeight())
                .into(view);
    }

    public static void loadImageCircle(ImageView view, String url, int placeHolder) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeHolder);
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeHolder)
                .transform(new GlideCircleTransform(view.getContext(), true))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(view.getMaxWidth(), view.getMaxHeight())
                .into(view);
    }

    public static void loadImageCircle(ImageView view, String url, Drawable defaultDrawable) {
        if (TextUtils.isEmpty(url)) {
            view.setImageDrawable(defaultDrawable);
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .error(defaultDrawable)
                .placeholder(defaultDrawable)
                .transform(new GlideCircleTransform(view.getContext(), true))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(view.getMaxWidth(), view.getMaxHeight())
                .into(view);
    }

    public static void loadImageNoCircle(ImageView view, String url, int placeholder) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeholder)
                .error(placeholder)
                .dontAnimate()
                .centerCrop()
                .override(view.getMaxWidth(), view.getMaxHeight())
                .into(view);
    }

    @BindingAdapter("android:inputType")
    public static void setInputType(EditText editText, String type) {
        if ("textPassword".equals(type)) {
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if ("numberPassword".equals(type)) {
            editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @BindingAdapter("fontType")
    public static void setTextSizeByType(TextView textView, String fontType) {
        int textSize;
        if (TextUtils.isEmpty(fontType)) {
            textView.setTextSize(12);
            return;
        }
        switch (fontType) {
            case FONT_TYPE_SMALL:
                textSize = 12;
                break;
            case FONT_TYPE_MIDDLE:
                textSize = 14;
                break;
            case FONT_TYPE_LARGE:
                textSize = 16;
                break;
            default:
                textSize = 12;
                break;
        }
        textView.setTextSize(textSize);
    }

    @BindingAdapter("fontColor")
    public static void setTextColor(TextView textView, String fontColor) {
        try {
            if (!TextUtils.isEmpty(fontColor)) {
                textView.setTextColor(Color.parseColor(fontColor));
            }
            //Toast.makeText(MyApplication.getInstance(),"颜色不能为空",Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException | NullPointerException ignored) {

        }
    }

}
