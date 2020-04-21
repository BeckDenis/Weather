package com.denisbeck.weather.views

import android.content.Context
import android.util.AttributeSet
import com.denisbeck.weather.utils.changeMeasureSize

class SquareTextView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}

class QuarterHeightTextView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, changeMeasureSize(widthMeasureSpec, 0.25))
    }
}

class DoubleHeightTextView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, changeMeasureSize(widthMeasureSpec, 2.0))
    }
}