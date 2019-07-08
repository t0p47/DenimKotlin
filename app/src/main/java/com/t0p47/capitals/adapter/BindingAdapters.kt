package com.t0p47.capitals.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bind:imageUrl")
fun imageUrl(imgView: ImageView, v: String?){
	if (v != null && v.isNotEmpty()) {
			Picasso.get().load(v).into(imgView)
	}
    
}