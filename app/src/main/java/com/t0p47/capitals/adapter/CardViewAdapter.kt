package com.t0p47.capitals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.t0p47.capitals.databinding.CapitalCardBinding
import com.t0p47.capitals.model.Capital


class CardViewAdapter(val capitalList: ArrayList<Capital>): RecyclerView.Adapter<CardViewAdapter.CardViewHolder>() {

	class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
		var binding: CapitalCardBinding? = DataBindingUtil.bind(itemView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder{
		val inflater = LayoutInflater.from(parent.context)
		val binding = CapitalCardBinding.inflate(inflater, parent, false)
		return CardViewHolder(binding.root)
	}

	override fun onBindViewHolder(holder: CardViewHolder, position: Int){
		val capital = capitalList[position]
		holder.binding?.capital = capital
		//val dr = holder.binding.imgView.getDrawable()
		//val bmp: Bitmap = (dr.getCurrent() as GlideBitmapDrawable).getBitmap()

		//val intent = Intent(context, CapitalActivity::class.java)
		//intent.putExtra("capital",capital.capital)
		//intent.putExtra("country",capital.country)
		//intent.putExtra("description",capital.description)
		//startActivity(this, intent)

	}

	override fun getItemCount(): Int{
		return capitalList.size
	}

}