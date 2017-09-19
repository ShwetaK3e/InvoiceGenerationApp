package com.store.pawan.pawanstore.CustomWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by shwetakumar on 7/27/17.
 */

public class PStoreTextViewNormal extends android.support.v7.widget.AppCompatTextView {


    public PStoreTextViewNormal(Context context) {
        super(context);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_regular.ttf");
        this.setTypeface(tf);

    }

    public PStoreTextViewNormal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_regular.ttf");
        this.setTypeface(tf);
    }

    public PStoreTextViewNormal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf= Typeface.createFromAsset(context.getAssets(),"fonts/roboto_regular.ttf");
        this.setTypeface(tf);
    }


}
