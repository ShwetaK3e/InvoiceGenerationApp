package com.store.pawan.pawanstore.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by shwetakumar on 7/27/17.
 */

public class PStoreTextViewItalic extends android.support.v7.widget.AppCompatTextView {


    public PStoreTextViewItalic(Context context) {
        super(context);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_light_italic.ttf");
        this.setTypeface(tf);

    }

    public PStoreTextViewItalic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_light_italic.ttf");
        this.setTypeface(tf);
    }

    public PStoreTextViewItalic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_light_italic.ttf");
        this.setTypeface(tf);
    }


}
