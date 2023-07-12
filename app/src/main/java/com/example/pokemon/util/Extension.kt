package com.example.pokemon.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        url?.let { Glide.with(view.context).load(it).into(view) }
    }
}

fun AppCompatActivity.showError(message: String) = AlertDialog.Builder(this)
    .setTitle("Error!")
    .setMessage(message)
    .setPositiveButton("Ok") { dialog, _ ->
        dialog.dismiss()
    }.create().show()

fun AppCompatActivity.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.inflateDataBinding(layout: Int): ViewDataBinding? {
    val layoutInflater =
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return DataBindingUtil.bind(
        layoutInflater.inflate(
            layout,
            null
        )
    )
}