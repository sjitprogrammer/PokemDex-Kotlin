package com.aodev.pokemdex.listener

import android.view.View

interface OnItemClickListener {
    fun onClickedItem(view: View, id:Int, position:Int)
}