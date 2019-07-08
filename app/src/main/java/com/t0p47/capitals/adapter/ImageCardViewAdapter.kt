package com.t0p47.capitals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.t0p47.capitals.databinding.ImageItemBinding

class ImageCardViewAdapter(private val imageList: ArrayList<String>): RecyclerView.Adapter<ImageCardViewAdapter.ImageViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding: ImageItemBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val img = imageList[position]

        Picasso.get().load(img).into(holder.binding?.imgView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}