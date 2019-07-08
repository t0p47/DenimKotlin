package com.t0p47.capitals.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.t0p47.capitals.R
import java.lang.Exception

@BindingAdapter("bind:imageUrl")
fun imageUrl(imgView: ImageView, v: String?){
	if (v != null) {
		if(v.isNotEmpty()){
            //Log.d("LOG_TAG","BindingAdapters: Image which loaded: $v")

			//Glide.with(imgView.context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Canberra_montage_2.jpg/270px-Canberra_montage_2.jpg").into(imgView)
			Picasso.get().load(v).into(imgView, object: Callback {
				override fun onError(e: Exception?) {
					//Log.d("LOG_TAG", "onError: ${e?.message}")
				}

				override fun onSuccess(){
					//Log.d("LOG_TAG", "onSuccess image")
				}
			})
			//imgView.setImageResource(R.mipmap.ic_launcher_round)
		}
	}
    
}