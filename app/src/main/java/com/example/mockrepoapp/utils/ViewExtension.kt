package com.example.mockrepoapp.utils

import android.view.View

object ViewExtension {

    fun View.showHideView(isVisible: Boolean){
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }



}