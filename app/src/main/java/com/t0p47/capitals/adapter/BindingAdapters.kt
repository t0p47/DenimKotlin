package com.t0p47.capitals.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.t0p47.capitals.R
import java.lang.Exception

@BindingAdapter("bind:imageUrl")
fun imageUrl(imgView: ImageView, v: String?){
	if (v != null) {
		if(v.isNotEmpty()){
            Log.d("LOG_TAG","Image which loaded: $v")
			/*val imgSize = Picasso.get().load(v).get().byteCount.toString()
			Log.d("LOG_TAG","ImageSize: $imgSize")*/


			/*val myUri = Uri.parse("https://en.wikipedia.org/wiki/Canberra#/media/File:Canberra_montage_2.jpg")
			val imageLoader = ImageLoader.getInstance()
			imageLoader.displayImage(imageUri, imageView)*/

			Glide.with(imgView.context).load(v).into(imgView)
			/*Picasso.get().load("https://data.humdata.org/crisis-tiles/5/25/14.png").into(imgView, object: Callback{
				override fun onError(e: Exception?) {
					Log.d("LOG_TAG", "onError: ${e?.message}")
				}

				override fun onSuccess(){
					Log.d("LOG_TAG", "onSuccess image")
				}
			})*/
			//imgView.setImageResource(R.mipmap.ic_launcher_round)
		}
	}
    
}