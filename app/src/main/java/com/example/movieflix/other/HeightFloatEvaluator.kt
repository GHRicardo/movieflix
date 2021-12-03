package com.example.movieflix.other

import android.animation.FloatEvaluator
import android.view.View

class HeightFloatEvaluator(val view : View) : FloatEvaluator() {

    override fun evaluate(fraction: Float, startValue: Number?, endValue: Number?): Float {
        view.y = super.evaluate(fraction, startValue, endValue)
        return view.y
    }
}