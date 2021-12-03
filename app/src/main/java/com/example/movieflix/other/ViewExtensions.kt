package com.example.movieflix.other

import android.view.View
import android.view.ViewGroup
import androidx.transition.*

fun View.showView(){
    this.visibility = View.VISIBLE
}

fun View.hideView(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.enabled(){
    this.isEnabled = true
    this.alpha = 1.0f
}

fun View.disabled(){
    this.isEnabled = false
    this.alpha = 0.5f
}

fun View.animateShow(parent: ViewGroup, isHiding:Boolean){
    val fade = Fade().apply {
        mode = if(!isHiding){
            Visibility.MODE_IN
        }else{
            Visibility.MODE_OUT
        }
        addTarget(this@animateShow)
        duration = 500
    }
    val changeBounds = ChangeBounds().apply {
        duration = 300
    }
    val transitionSet = TransitionSet().apply {
        addTransition(fade)
        addTransition(changeBounds)
    }
    TransitionManager.beginDelayedTransition(parent, transitionSet)
    if(!isHiding){
        this.showView()
    }else{
        this.hideView()
    }
}